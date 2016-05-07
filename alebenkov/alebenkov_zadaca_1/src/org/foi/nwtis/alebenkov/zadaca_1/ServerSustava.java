package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 * Server class
 *
 * @author Alen Benkovic
 */
public class ServerSustava {

    private final String datoteka;
    private final Matcher mParametri;
    private boolean load = false; //datoteka sa serijaliziranim podacima
    private static boolean zaustavljen = false; //za zaustavljanje rada servera
    private static boolean pauziran = false; //za pauziranje rada servera
    private Socket klijent;
    private static Evidencija evid;
    private PotapanjeBrodova igra;
    private static SerijalizatorEvidencije se;

    /**
     * Konstruktor servera.
     *
     * @param parametri
     * @throws java.lang.Exception
     */
    public ServerSustava(String parametri) throws Exception {
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri servera ne odgovoraju!");
        } else {
            this.datoteka = mParametri.group(1);
            if (mParametri.group(3) != null) {
                this.load = true;
            }
        }

    }

    /**
     * Metoda za pokretanje samog servera. Ucitava datoteku sa postavkama
     * Ucitava serijaliziranu evidenciju i spremljenu igru ako je potrebno, u
     * suprotnom kreira novu igru sa zadanim postavkama Pokrece dretvu za
     * serijalizaciju evidencije Kreira grupu dretvi za posluzivanje igraca
     * Kreira server socket i ceka da se igrac spoji, te potom konekciju predaje
     * dretvi na obradu
     */
    public void pokreniServer() {
        //System.out.println("Pokrecem server!");
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

        //ako ne postoji zapis o staroj igri kreiram novu
        if (this.load == false) {
            int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
            int x = Integer.parseInt(konfig.dajPostavku("brojX"));
            int y = Integer.parseInt(konfig.dajPostavku("brojY"));
            int brojBrodova = Integer.parseInt(konfig.dajPostavku("brojBrodova"));
            igra = new PotapanjeBrodova(brojIgraca, x, y, brojBrodova); //kreiram novu igru sa postavkama iz datoteke
            evid = new Evidencija(igra); //kreiram evidenciju za novu igru
            evid.dodajServerZapis("SERVER | Kreiram novu igru. Broj igraca: " + brojIgraca + ". Broj brodova: " + brojBrodova + ". Velicina ploce: " + x + " x " + y + ".");
            se = new SerijalizatorEvidencije(konfig, evid); //kreiram dretvu zaduzenu za serijalizaciju evidencije
            se.start(); //pokrecem serijalizaciju evidencije svakih n sekundi(postavka u konfig datoteci)

        } //ako postoji stara igra ucitavam nju
        else {
            se = new SerijalizatorEvidencije(konfig);//kreiram dretvu za serijalizaciju
            this.evid = se.ucitajEvidenciju(); //ucitavam serijaliziranu evidenciju
            //evid.dodajServerZapis("SERVER | Ucitavam spremljenu igru...");
            igra = evid.dohvatiSpremljenuIgru(); //iz spremljene evidencije ucitavam spremljenu igru
            se.start();

        }

        int brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
        int port = Integer.parseInt(konfig.dajPostavku("port"));

        //kreiram grupu za dretve
        ThreadGroup tg = new ThreadGroup("alebenkov");
        ObradaZahtjeva[] dretve = new ObradaZahtjeva[brojDretvi];

        //kreiram dretve i spremam ih u grupu
        for (int i = 0; i < brojDretvi; i++) {
            dretve[i] = new ObradaZahtjeva(tg, "alebenkov_" + i, konfig, igra, evid);
            evid.dodajServerZapis("SERVER | Kreiram dretvu " + dretve[i].getName() + " " + dretve[i].getState());
        }

        try {
            //kreiram server socket
            ServerSocket ss = new ServerSocket(port);
            //ss.setSoTimeout(10000); //da ne blokira kod citanja do kraja vec 1000
            while (!zaustavljen) {
                this.klijent = ss.accept(); //cekam da se igrac spoji
                evid.dodajServerZapis("SERVER | Zahtjev primljen, trazim slobodnu dretvu...");
                int sd = dajSlobodnuDretvu(dretve); //trazim slobodnu dretvu
                if (sd == -1) { //ako nema slobodne dretve saljem igracu poruku
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
                            evid.dodajServerZapis(" | GRESKA kod IO operacija 2");
                        }
                    }
                    evid.dodajServerZapis("SERVER | ERROR 80: Nema slobodne dretve.");
                } else {//ako postoji slobodna dretva radim sljedece:

                    if (dretve[sd].brojacRada() > 0) {//ako je dretva do sada radila ona radim interrupt nad njom kako bi nastavila
                        evid.dodajServerZapis("SERVER | Ponovno pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].interrupt();
                    } else {//ako nije onda ju pokrecem
                        evid.dodajServerZapis("SERVER | Pokrecem dretvu " + dretve[sd].getName());
                        dretve[sd].start(); //pokrecem prvu slobodnu dretvu
                    }
                    dretve[sd].setSocket(this.klijent); //saljem dretvi podatke o socketu klijenta/igraca

                }

            }
        } catch (IOException ex) {
            evid.dodajServerZapis("SERVER | Nastala greska prilikom rada socketa " + ex.getMessage());
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
                evid.dodajServerZapis("SERVER | Dretva " + dretve[i].getName() + " je slobodna.");

            } else if (dretve[i].stanjeDretve() == 1) {
                evid.dodajServerZapis("SERVER | Dretva " + dretve[i].getName() + " je zauzeta.");
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

    /**
     *
     * @return true ako je server pauziran, inace false
     */
    public static boolean provjeraPauziran() {
        return pauziran;
    }

    /**
     * Metoda za pauziranje servera
     *
     * @param pauziran
     */
    public static synchronized void SetPauziraj(boolean pauziran) {
        ServerSustava.pauziran = pauziran;
    }

    /**
     * @param p dobivena naredba koja ide na daljnju provjeru
     * @return Matcher ili null ukoliko su neispravni parametri
     */
    public Matcher provjeraParametara(String p) {
        String regex = "^-server -konf ([^\\s]+\\.(?i)(txt|xml))( +-load)?"; //dozvoljeno: -server -konf -datoteka(.xml | .txt) [-load]
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            return null;
        }
    }

    /**
     * Metoda za zaustavljanje serijalizacije evidencije i njeno spremanje nakon
     * zaustavljanja
     */
    public static void zaustaviSerijalizaciju() {
        se.zaustaviSerijalizacijuEvidencije();
        se.spremiEvidenciju();
    }

    /**
     * Dohvat evidencije
     * @return evidenciju
     */
    public static Evidencija getEvid() {
        return evid;
    }

    public static void zaustaviRadServera() {
        System.exit(0);       
    }

}
