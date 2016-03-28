package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            System.out.println("Kreiram novu igru...");
            int brojX = 3 + (int) (Math.random() * ((10 - 3) + 1)); //http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
            int brojY = 3 + (int) (Math.random() * ((10 - 3) + 1));
            System.out.println("BrojX= " + brojX);
            System.out.println("BrojY= " + brojY);
            int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
            System.out.println("Ucitavam broj igraca iz datoteke:" + brojIgraca);
        } else { //ako postoji stara igra ucitavam nju
            System.out.println("Ucitavam staru igru...");
        }

        int brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
        System.out.println("Ucitavam broj dretvi iz datoteke:" + brojDretvi);
    }

    private void ucitajSerijaliziranuEvidenciju(String datEvid) {
        //TODO napravite sami
    }

}
