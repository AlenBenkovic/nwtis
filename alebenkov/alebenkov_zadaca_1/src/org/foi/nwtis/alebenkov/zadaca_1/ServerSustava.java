package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.util.Arrays;
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
            System.out.println("Ploca velicine " + brojX + " X " + brojY);
            int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
            System.out.println("Broj IGRACA:" + brojIgraca);
            int brojBrodova = Integer.parseInt(konfig.dajPostavku("brojBrodova"));
            System.out.println("Broj BRODOVA:" + brojBrodova);
            int[][][] poljeBrodova = new int [brojIgraca][brojX][brojY]; //kreiram trodimenzionalno polje
            for (int i = 0; i < brojIgraca; i++) { 
                for(int j=0; j<brojBrodova;j++){//spremam brodove na razlicite lokacije
                    int nasumicniX=0 + (int) (Math.random() * (((brojX-1) - 0) + 1));
                    int nasumicniY=0 + (int) (Math.random() * (((brojY-1) - 0) + 1));
                    if(poljeBrodova[i][nasumicniX][nasumicniY] ==1)
                    {
                        j--; //ukoliko broj postoji pokusavam ponovno na novoj nasumicnoj lokaciji
                    } else
                    {
                        poljeBrodova[i][nasumicniX][nasumicniY] =1; //ako broj ne postoji spremam ga na lokaciju
                    }
                }
                
                System.out.println("IGRAC " + i + ":" + Arrays.deepToString(poljeBrodova[i]));
            }
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
