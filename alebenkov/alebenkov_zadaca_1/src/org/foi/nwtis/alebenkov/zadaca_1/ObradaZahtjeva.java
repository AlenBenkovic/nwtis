package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author csx
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;
    private int stanjeDretve = 0; //0-slobodna, 1-zauzeta
    private int brojacRada = 0; //brojim koliko je puta dretva posluzila klijenta
    Konfiguracija konfig = null;

    public ObradaZahtjeva(ThreadGroup group, String name, Konfiguracija konfig) {
        super(group, name);
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        stanjeDretve = 1;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (true) {
            this.pokreni();
        }
    }

    public synchronized void pokreni() {

        stanjeDretve = 1;
        this.brojacRada += 1;
        long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve

        InputStream is = null;
        OutputStream os = null;
        StringBuilder primljenoOdKorisnika = new StringBuilder();

        try {
            is = server.getInputStream();
            os = server.getOutputStream();

            String slanjeKorisniku = "SERVER | Primio sam vas zahtjev. Obrada zahtjeva...";

            os.write(slanjeKorisniku.getBytes());
            os.flush();
            server.shutdownOutput();

            while (is.available() > 0) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                primljenoOdKorisnika.append((char) znak);

            }
            System.out.println("SERVER | Primljena naredba od korisnika: " + primljenoOdKorisnika);
            if (primljenoOdKorisnika.indexOf("START") != -1) {
                System.out.println("Pokrecem server...");
            }

        } catch (IOException ex) {
            System.out.println(this.getName() + " | GRESKA kod IO operacija!");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }

            } catch (IOException ex) {
                System.out.println(this.getName() + " | GRESKA kod IO operacija 2");
            }
        }

        System.out.println(this.getName() + " | Pokrecem dretvu koja ce posluziti korisnika.| Brojac rada: " + this.brojacRada + ". | Stanje dretve: " + this.getState());

        //po zavrsetku svih poslova dretve, saljem ju na spavanje
        long trajanjeRadaDretve = System.currentTimeMillis() - pocetakRadaDretve;
        try {
            System.out.println(this.getName() + " | Saljem dretvu na spavanje");
            sleep(5000 - trajanjeRadaDretve);

            System.out.println(this.getName() + " | Dretva dosla sa spavanja");
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

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
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

    public boolean provjeraAdmina(String username, String password) {
        if (username.equals(konfig.dajPostavku("adminKorLozinka")) && password.equals(konfig.dajPostavku("adminKorLozinka"))) {
            return true;
        }
        return false;
    }
}
