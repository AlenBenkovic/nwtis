package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author csx
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;
    private int stanjeDretve = 0; //0-slobodna, 1-zauzeta
    private int brojacRada = 0; //brojim koliko je puta dretva posluzila klijenta

    public ObradaZahtjeva(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void interrupt() {
        stanjeDretve = 1;
        brojacRada++;
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
        brojacRada++;
        long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
        InputStream is = null;
        OutputStream os = null;
        System.out.println(this.getName() + " | Pokrecem dretvu koja ce posluziti korisnika. Stanje " + this.getState());

        try {
            is = server.getInputStream();
            os = server.getOutputStream();

            String slanjeNaredbe = "SERVER KAZE OK!";

            os.write(slanjeNaredbe.getBytes());
            os.flush();
            server.shutdownOutput();

            StringBuilder naredba = new StringBuilder();
            while (is.available() > 0) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                naredba.append((char) znak);
            }

            System.out.println(this.getName() + " | Dobivena naredba: " + naredba.toString());

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
        //po zavrsetku svih poslova dretve, saljem ju na spavanje
        long trajanjeRadaDretve = System.currentTimeMillis() - pocetakRadaDretve;
        try {
            System.out.println(this.getName() + " | Saljem dretvu na spavanje" );
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

}
