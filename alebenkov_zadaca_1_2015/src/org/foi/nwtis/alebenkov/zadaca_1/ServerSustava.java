/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.io.IOException;
import static java.lang.Thread.State.NEW;
import static java.lang.Thread.State.RUNNABLE;
import static java.lang.Thread.State.WAITING;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;
import static org.foi.nwtis.alebenkov.zadaca_1.ObradaZahtjeva.StanjeDretve.Slobodna;
import static org.foi.nwtis.alebenkov.zadaca_1.ObradaZahtjeva.StanjeDretve.Zauzeta;

/**
 *
 * @author alen
 */
public class ServerSustava {

    protected String parametri;
    protected Matcher mParametri;
    
    private static boolean pauziran = false;
    private static boolean zaustavljen = false;

    public ServerSustava(String parametri) throws Exception {
        this.parametri = parametri;
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri servera ne odgovaraju!");
        }
    }

    public Matcher provjeraParametara(String p) {
        String sintaksa = "^-server -konf +([^\\s]+.(xml|txt))( +-load)?$";

        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            System.out.println("Ne odgovara!");
            return null;
        }
    }

    public void pokreniServer() {
        Konfiguracija konfig = null;
        String datoteka = mParametri.group(1);
        File dat = new File(datoteka);
        if (!dat.exists()) {
            System.out.println("Datoteka konfiguracije ne postoji.");
            return;
        }
        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);

            if (this.mParametri.group(2) != null) {
                String datEvid = konfig.dajPostavku("evidDatoteka");
                ucitajSerijaliziranuEvidenciju(datEvid);
            }
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }

        SerijalizatorEvidencije se = new SerijalizatorEvidencije(konfig);
        se.start();

        int brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
        System.out.println("Ucitavam broj dretvi iz datoteke:" + brojDretvi);
        
        ThreadGroup tg = new ThreadGroup("alebenkov");
        ObradaZahtjeva[] dretve = new ObradaZahtjeva[brojDretvi];
        for (int i = 0; i < brojDretvi; i++) {
            dretve[i] = new ObradaZahtjeva(tg, "alebenkov_" + i);
            dretve[i].setKonfig(konfig);
            System.out.println("Kreiram dretvu " + dretve[i].getName() + " stanje " + dretve[i].getState());
        }

        int port = Integer.parseInt(konfig.dajPostavku("port"));
        System.out.println("Ucitavam port iz datoteke:" + port);
        try {
            ServerSocket ss = new ServerSocket(port);
            ss.setSoTimeout(1000); //da ne blokira kod citanja do kraja vec 1000
            while (!zaustavljen) {
                Socket socket = ss.accept();
                System.out.println("Zahtjev primljen, odgovaram...");
                ObradaZahtjeva oz = dajSlobodnuDretvu(dretve);
                if (oz == null) {
                    System.out.println("ERROR 80: Nema slobodne dretve.");//ja dodao
                    return;
                }
                if (oz.getStanje() != Slobodna) {
                    break;
                } else {
                    if(oz.getState() == WAITING){
                       oz.notify(); 
                    }
                    oz.start();
                    oz.setStanje(ObradaZahtjeva.StanjeDretve.Zauzeta);
                    oz.setSocket(socket);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ucitajSerijaliziranuEvidenciju(String datEvid) {
        //TODO napravite sami
    }

    private ObradaZahtjeva dajSlobodnuDretvu(ObradaZahtjeva[] dretve) {
       int slobodnaDretvaID = -1;
        for (int i = dretve.length-1; i >= 0; i--) { //na ovaj nacin uzimam prvu dretvu
            if (dretve[i].getStanje()== Slobodna) {
                slobodnaDretvaID = i;
                System.out.println("Slobodna dretva " + dretve[i].getName());
            } else if (dretve[i].getStanje() == Zauzeta) {
                System.out.println("Zauzeta dretva " + dretve[i].getName() + dretve[i].getState());
            }
        }

        if (slobodnaDretvaID == -1) {
            return null;
        } else {
            return dretve[slobodnaDretvaID];
        }
    } 

}
