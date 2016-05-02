package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
    private static boolean pauziran = false;
    private Socket klijent;
    private static boolean igraKreirana = false;

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
        /*if (this.load == false) { //ako ne postoji zapis o staroj igri kreiram novu
            System.out.println("Potapanje brodova v.0.0.1");
        } else { //ako postoji stara igra ucitavam nju
            System.out.println("SERVER | Ucitavam staru igru...");
        }*/

        int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
        int x = Integer.parseInt(konfig.dajPostavku("brojX"));
        int y = Integer.parseInt(konfig.dajPostavku("brojY"));
        int brojBrodova = Integer.parseInt(konfig.dajPostavku("brojBrodova"));
        PotapanjeBrodova igra = new PotapanjeBrodova(brojIgraca, x, y, brojBrodova);
        
        int brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
        int port = Integer.parseInt(konfig.dajPostavku("port"));
        System.out.println("SERVER | Broj dretvi: " + brojDretvi + " | Port: " + port);

        //kreiram grupu dretvi
        ThreadGroup tg = new ThreadGroup("alebenkov");
        ObradaZahtjeva[] dretve = new ObradaZahtjeva[brojDretvi];

        for (int i = 0; i < brojDretvi; i++) {
            dretve[i] = new ObradaZahtjeva(tg, "alebenkov_" + i, konfig, igra);
            System.out.println("SERVER | Kreiram dretvu " + dretve[i].getName() + " " + dretve[i].getState());
        }

        try {
            ServerSocket ss = new ServerSocket(port);
            //ss.setSoTimeout(10000); //da ne blokira kod citanja do kraja vec 1000
            while (!zaustavljen) {
                this.klijent = ss.accept();
                System.out.println("SERVER | Zahtjev primljen, trazim slobodnu dretvu...");
                int sd = dajSlobodnuDretvu(dretve);
                if (sd == -1) {
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

                    if (dretve[sd].brojacRada() > 0) {//ako je dretva do sada radila ona radim interrupt nad njom kako bi nastavila
                        System.out.println("SERVER | Ponovno pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].interrupt();
                    } else {//ako nije onda ju pokrecem
                        System.out.println("SERVER | Pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].start(); //pokrecem prvu slobodnu dretvu
                    }
                    dretve[sd].setSocket(this.klijent);

                }

            }
        } catch (IOException ex) {
            System.out.println("SERVER | Nastala greska prilikom rada socketa " + ex.getMessage());
        }
    }

    //gledam koja mi je dretva slobodna i ako ih ima više slobodnih gledam koliko je koja radila
    //tako da sve budu jednako zastupljene
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

    private void ucitajSerijaliziranuEvidenciju(String datEvid) {
        //TODO napravite sami
    }

    public static boolean provjeraPauziran() {
        return pauziran;
    }

    public static synchronized void pauziraj(boolean pauziran) {
        ServerSustava.pauziran = pauziran;
    }
    
    public static boolean provjeraIgraKreirana(){
        return igraKreirana;
    }
    
    public static void igraKreirana(){
        igraKreirana = true;
    }

}
