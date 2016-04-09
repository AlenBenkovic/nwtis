package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.ServerSocket;
import java.net.Socket;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 * Server class
 *
 * @author Alen Benkovic
 */
public class ServerSustava {

    private static String datoteka; //konfig datoteka
    private static boolean load = false; //datoteka sa serijaliziranim podacima
    private static boolean zaustavljen = false; //za provjeru stanja servera

    /**
     *
     * @param datoteka - naziv konfiguracijske datoteke
     * @param load - parametar za ucitavanje datoteke sa serijaliziranim
     * podacima
     *
     */
    public ServerSustava(String datoteka, String load) {
        this.datoteka = datoteka;
        if (load != null) {
            this.load = true;
        }
        System.out.println(datoteka);
        System.out.println(this.load);
    }

    public void pokreniServer() {
        System.out.println("Pokrecem server!");
        Konfiguracija konfig = null;
        File dat = new File(this.datoteka);
        if (!dat.exists()) { //provjeravam da li postoji datoteka konfiguracije
            System.out.println("Datoteka konfiguracije ne postoji.");
            return;
        } else {
            try {//ako postoji pokusavam preuzeti konfiguraciju i spremiti ju u memoriju
                konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(this.datoteka);
            } catch (NemaKonfiguracije ex) {
                System.out.println("Greska prilikom preuzimanja konfiguracije " + ex.getMessage());
            }
        }
        if (this.load == false) { //ako ne postoji zapis o staroj igri kreiram novu
            int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
            potapanjeBrodova igra = new potapanjeBrodova(brojIgraca);
            igra.kreirajBrodove();
            igra.pogodiBrod(2, 0, 0);
        } else { //ako postoji stara igra ucitavam nju
            System.out.println("Ucitavam staru igru...");
        }

        int brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
        System.out.println("BROJ DRETVI: " + brojDretvi);

        int port = Integer.parseInt(konfig.dajPostavku("port"));
        System.out.println("PORT: " + port);

        ThreadGroup tg = new ThreadGroup("alebenkov");//kreiram grupu dretvi
        ObradaZahtjeva[] dretve = new ObradaZahtjeva[brojDretvi];

        for (int i = 0; i < brojDretvi; i++) {
            dretve[i] = new ObradaZahtjeva(tg, "alebenkov_" + i);
            System.out.println("Kreiram dretvu " + dretve[i].getName() + " " + dretve[i].getState());
        }

        try {
            ServerSocket ss = new ServerSocket(port);
            //ss.setSoTimeout(1000); //da ne blokira kod citanja do kraja vec 1000
            while (!zaustavljen) {
                Socket socket = ss.accept();
                System.out.println("Zahtjev primljen, odgovaram...");
                int sd = dajSlobodnuDretvu(dretve);
                if (sd == -1) {
                    System.out.println("ERROR 80: Nema slobodne dretve.");
                } else {

                    if (dretve[sd].brojacRada() > 0) {
                        System.out.println("Radim interrup dretve " + dretve[sd].getName());
                        dretve[sd].interrupt();
                    } else {
                        System.out.println("Pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].start(); //pokrecem prvu slobodnu dretvu
                    }
                    dretve[sd].setSocket(socket);

                }

            }
        } catch (IOException ex) {
            System.out.println("Nastala greska prilikom rada socketa " + ex.getMessage());
        }
    }

    private int dajSlobodnuDretvu(ObradaZahtjeva[] dretve) {
        int slobodnaDretvaID = -1;
        int najmanjiBrojac = 9999;
        for (int i = 0; i < dretve.length; i++) {
            if (dretve[i].stanjeDretve() == 0 & dretve[i].brojacRada() == 0) {
                slobodnaDretvaID = i;
                break;
            } else if (dretve[i].stanjeDretve() == 0) { //ovo radim kako bi jednako zaposlio sve dretve
                if(najmanjiBrojac>dretve[i].brojacRada()){
                    najmanjiBrojac = dretve[i].brojacRada();
                    slobodnaDretvaID = i;
                }
            }
        }
        return slobodnaDretvaID;
    }

    private void ucitajSerijaliziranuEvidenciju(String datEvid) {
        //TODO napravite sami
    }

}
