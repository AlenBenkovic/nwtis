/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author abenkovic
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;
    private int stanjeDretve = 0; //0-slobodna, 1-zauzeta
    private int brojacRada = 0; //brojim koliko je puta dretva posluzila klijenta
    Konfiguracija konfig = null;
    InputStream in = null;
    OutputStreamWriter out = null;
    private boolean radi = true;

    public ObradaZahtjeva(ThreadGroup group, String name, Konfiguracija konfig) {
        super(group, name);
        this.konfig = konfig;

    }

    @Override
    public void interrupt() {
        stanjeDretve = 1; //kod ponovnog pozivanja dretve, oznacavam ju kao zauzetu
        super.interrupt();
    }

    @Override
    public void run() {
        while (radi) {
            this.pokreni();
        }
        System.out.println(this.getName() + " GASIM.");
    }

    public synchronized void pokreni() {
        stanjeDretve = 1;
        this.brojacRada += 1;
        long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
        System.out.println(this.getName() + " | Brojac rada: " + this.brojacRada + ". | Stanje dretve: " + this.getState());
        //POCETAK GLAVNE LOGIKE
        StringBuilder naredba = null;
        int c;

        try {
            in = server.getInputStream();
            out = new OutputStreamWriter(server.getOutputStream());
            naredba = new StringBuilder();

            //uzimam naredbu sa input streama
            while ((c = in.read()) != -1) {
                naredba.append((char) c);
            }

            if (naredba.toString().isEmpty()) {//ukoliko kojim slucajem nisam primio naredbu..
                out.write("SERVER | ERROR: Nisam zaprimio nikakvu naredbu.\n");
            } else {
                System.out.println("SERVER | Primljena naredba od korisnika: " + naredba);
            }

        } catch (IOException ex) {
            System.out.println("ERROR 01 | IOException: " + ex.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (server != null) {
                    server.close();
                }

            } catch (IOException ex) {
                System.out.println("ERROR 02 | IOException: " + ex.getMessage() + ex.toString());
            }
        }

        //KRAJ GLAVNE LOGIKE
        long trajanjeRadaDretve = System.currentTimeMillis() - pocetakRadaDretve;//kraj rada dretve
        try {
            System.out.println(this.getName() + " | Saljem dretvu na spavanje.");
            sleep(5000 - trajanjeRadaDretve);

            System.out.println(this.getName() + " | Dretva dosla sa spavanja.");
        } catch (InterruptedException ex) {
            System.out.println(this.getName() + " | Prekid dretve za vrijeme spavanja");
        }

        stanjeDretve = 0;//oslobadjam dretvu

        while (this.stanjeDretve == 0) {//dokle god je slobodna, dretva ceka dok netko ne pozove 
            try {
                this.wait();
            } catch (InterruptedException ex) {
                System.out.println(this.getName() + " | Dretva nastavlja s radom");
            }
        }

    }

    public void setSocket(Socket server) {
        this.server = server;
    }

    public int stanjeDretve() {
        return this.stanjeDretve;
    }

    public int brojacRada() {
        return this.brojacRada;
    }

    public void setRadi(boolean radi) {
        this.radi = radi;
    }

}
