/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.servlet.ServletContext;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author abenkovic
 */
public class ServerSustava extends Thread {

    private ServletContext kontekst;
    private static boolean zaustavljen = false; //za zaustavljanje rada servera
    private static boolean pauziran = false; //za pauziranje rada servera
    private Konfiguracija konfig = null;
    private int port;
    private int brojDretvi;
    private Socket klijent = null;
    private ServerSocket ss = null;
    private ObradaZahtjeva[] dretve;

    public ServerSustava(ServletContext kontekst) {
        this.kontekst = kontekst;
        this.konfig = (Konfiguracija) kontekst.getAttribute("serverConfig");
        this.port = Integer.parseInt(konfig.dajPostavku("port"));
        this.brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
    }

    @Override
    public void interrupt() {
        System.out.println("Gasim dretve servera.");
        for (int i = 0; i < brojDretvi; i++) {
            if (dretve[i].isAlive()) {
                dretve[i].setRadi(false);
                dretve[i].interrupt();
            }

        }
        try {
            ss.close();
        } catch (IOException ex) {
            System.out.println("Nastala greska prilikom gasenja servera. " + ex.getMessage());
        }

        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        pokreniServer();
    }

    public void pokreniServer() {

        //kreiram grupu za dretve
        ThreadGroup tg = new ThreadGroup("alebenkov");
        dretve = new ObradaZahtjeva[brojDretvi];

        //kreiram dretve i spremam ih u grupu
        for (int i = 0; i < brojDretvi; i++) {
            dretve[i] = new ObradaZahtjeva(tg, "alebenkov_" + i, konfig);
            System.out.println("SERVER | Kreiram dretvu " + dretve[i].getName() + " " + dretve[i].getState());
        }

        try {
            //kreiram server socket
            ss = new ServerSocket(port);
            System.out.println("SERVER | Osluskujem na " + this.port);
            //ss.setSoTimeout(10000); //da ne blokira kod citanja do kraja vec 1000
            while (!zaustavljen) {
                this.klijent = ss.accept(); //cekam da se korisnik spoji
                System.out.println("SERVER | Zahtjev primljen, trazim slobodnu dretvu...");
                int sd = dajSlobodnuDretvu(dretve); //trazim slobodnu dretvu
                if (sd == -1) { //ako nema slobodne dretve saljem korisniku poruku
                    OutputStream os = null;
                    try {
                        os = this.klijent.getOutputStream();
                        String slanjeNaredbe = "SERVER | ERROR 80: Nema slobodne dretve.";

                        os.write(slanjeNaredbe.getBytes());
                        os.flush();
                        this.klijent.shutdownOutput();

                    } finally {
                        try {
                            if (os != null) {
                                os.close();
                            }
                        } catch (IOException ex) {
                            System.out.println(" | GRESKA kod IO operacija 2");
                        }
                    }
                    System.out.println("SERVER | ERROR 80: Nema slobodne dretve.");
                } else {
                    //ako postoji slobodna dretva radim sljedece:
                    if (dretve[sd].brojacRada() > 0) {//ako je dretva do sada radila ona radim interrupt nad njom kako bi nastavila
                        System.out.println("SERVER | Ponovno pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].interrupt();
                    } else {//ako nije onda ju pokrecem
                        System.out.println("SERVER | Pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].start(); //pokrecem prvu slobodnu dretvu
                    }
                    dretve[sd].setSocket(this.klijent); //saljem dretvi podatke o socketu klijenta/igraca

                }

            }
        } catch (IOException ex) {
            System.out.println("SERVER | Nastala greska prilikom rada socketa " + ex.getMessage());
        }
    }

    /**
     * Metoda za dobivanje slobodne dretve koja je najmanje radila, kruzno
     * posluzivanje
     *
     * @param dretve grupa dretvi od kojih se odabire najmanje koristena dretva
     * @return ID slobodne dretve
     */
    private int dajSlobodnuDretvu(ObradaZahtjeva[] dretve) {
        int slobodnaDretvaID = -1;
        int najmanjiBrojac = 9999;
        //radim posebnu petlju za ispis stanja dretvi kako bi izbjegao komplikacije kod samog izbora
        for (int i = 0; i < dretve.length; i++) {
            if (dretve[i].stanjeDretve() == 0) {

                System.out.println("SERVER | Dretva " + dretve[i].getName() + " je slobodna.");

            } else if (dretve[i].stanjeDretve() == 1) {
                System.out.println("SERVER | Dretva " + dretve[i].getName() + " je zauzeta.");
            }
        }
        for (int i = 0; i < dretve.length; i++) {
            if (dretve[i].stanjeDretve() == 0 & dretve[i].brojacRada() == 0) {
                slobodnaDretvaID = i;
                break;
            } else if (dretve[i].stanjeDretve() == 0) { //ovo radim kako bi jednako zaposlio sve dretve
                if (najmanjiBrojac > dretve[i].brojacRada()) {
                    najmanjiBrojac = dretve[i].brojacRada();
                    slobodnaDretvaID = i;
                }
            }
        }
        return slobodnaDretvaID;
    }

}
