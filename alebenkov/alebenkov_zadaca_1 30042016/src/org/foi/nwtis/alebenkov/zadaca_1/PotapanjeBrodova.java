package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author alen benkovic
 */
public class PotapanjeBrodova {

    private final int brojX;//velicina ploce
    private final int brojY;//velicina ploce
    private final int brojIgraca;
    private int trenutniBrojIgraca = 0;
    private final int brojBrodova;
    private final int[][] poljeBrodova;
    private final UUID idIgre = UUID.randomUUID();
    private Igrac[] igraci;
    private boolean igraKreirana = false; //sluzi kako se igraci ne bi prijavljivali prije nego sto igra pocne

    public PotapanjeBrodova(int brojIgraca) {
        this.brojX = 3 + (int) (Math.random() * ((10 - 3) + 1)); //http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range;
        this.brojY = 3 + (int) (Math.random() * ((10 - 3) + 1));
        this.brojBrodova = 2 + (int) (Math.random() * ((5 - 2) + 1));
        this.brojIgraca = brojIgraca;
        this.poljeBrodova = new int[brojX][brojY];
        this.igraci = new Igrac[brojIgraca];
        System.out.println("Kreiram novu igru sa ID igre: " + idIgre);
    }

    /*public PotapanjeBrodova(int x, int y, int brojIgraca, int brojBrodova) {
        this.brojX = x;
        this.brojY = y;
        this.brojIgraca = brojIgraca;
        this.brojBrodova = brojBrodova;
        this.poljeBrodova = new int[brojX][brojY];
    }*/
    public void kreirajBrodove() {
        igraKreirana = true;
        System.out.println("Kreiram novu igru...");
        System.out.println("Ploca velicine " + this.brojX + " X " + this.brojY);
        System.out.println("Broj IGRACA:" + this.brojIgraca);
        System.out.println("Broj BRODOVA:" + this.brojBrodova);

        for (int i = 1; i <= this.brojIgraca; i++) {
            for (int j = 0; j < this.brojBrodova; j++) {//spremam brodove na razlicite lokacije
                int nasumicniX = 0 + (int) (Math.random() * (((this.brojX - 1) - 0) + 1));
                int nasumicniY = 0 + (int) (Math.random() * (((this.brojY - 1) - 0) + 1));
                if (this.poljeBrodova[nasumicniX][nasumicniY] == 0) {
                    this.poljeBrodova[nasumicniX][nasumicniY] = i; //ako broj ne postoji spremam ga na lokaciju
                } else {
                    j--; //ukoliko broj postoji pokusavam ponovno na novoj nasumicnoj lokaciji
                }
            }
        }
        System.out.println("Polje brodova: " + Arrays.deepToString(this.poljeBrodova));
    }

    public boolean pogodiBrod(int idIgraca, int x, int y) {
        if (this.brojX <= x | this.brojY <= y) {
            System.out.println("Neispravne koordinate!");
            return false;
        } else if (this.poljeBrodova[x][y] == idIgraca) {
            System.out.println("POGODAK!");
            return true;
        } else {
            System.out.println("PROMASAJ!");
            return false;
        }
    }

    public boolean provjeraSlobodnihMjesta() {
        if (trenutniBrojIgraca < brojIgraca) {
            return igraKreirana;//ukoliko igra nije kreirana vraca false
        } else {
            return false;
        }
    }

    public UUID dohvatiIdIgre() {
        return this.idIgre;
    }

    public boolean igracPrijava(String ime) {
        boolean prijava = false;
        if (trenutniBrojIgraca == 0) {
            igraci[trenutniBrojIgraca] = new Igrac(ime, trenutniBrojIgraca + 1, idIgre);
            this.trenutniBrojIgraca+=1;
            prijava = true;
        } else {
            for (int i = 0; i < trenutniBrojIgraca; i++) {
                if (igraci[i].dajIme() != ime) {
                    igraci[trenutniBrojIgraca] = new Igrac(ime, trenutniBrojIgraca + 1, idIgre);
                    this.trenutniBrojIgraca += 1;
                    prijava = true;
                }
            }

        }

        return prijava;
    }

    public void pregledPrijavljenihIgraca() {
        System.out.println("Polje brodova: " + Arrays.deepToString(this.igraci));
    }

}
