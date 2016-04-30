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

    public PotapanjeBrodova(int brojIgraca, int x, int y, int brojBrodova) {
        this.brojX = x;
        this.brojY = y;
        this.brojBrodova = brojBrodova;
        //this.brojBrodova = 2 + (int) (Math.random() * ((5 - 2) + 1));//http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range;
        this.brojIgraca = brojIgraca;
        this.poljeBrodova = new int[brojX][brojY];
        this.igraci = new Igrac[brojIgraca];
        System.out.println("Kreiram novu igru sa ID igre: " + idIgre);
    }

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
        } else if (this.poljeBrodova[x][y] != idIgraca & this.poljeBrodova[x][y] != 0) {
            this.poljeBrodova[x][y] = 0;
            return true;
        } else {
            return false;
        }
    }

    public int vrijednostPolja(int x, int y) {
        return this.poljeBrodova[x][y];
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
            prijava = true;
        } else {
            for (int i = 0; i < trenutniBrojIgraca; i++) {
                if (igraci[i].dajIme().equals(ime)) {
                    prijava = false;
                    break;
                } else {
                    prijava = true;
                    break;
                }
            }
        }
        if (prijava) {
            igraci[trenutniBrojIgraca] = new Igrac(ime, trenutniBrojIgraca + 1, idIgre, brojBrodova);
            this.trenutniBrojIgraca += 1;
        }

        return prijava;
    }

    public void pregledPrijavljenihIgraca() {
        System.out.println("Prijavljeni igraci: " + igraci.toString());
    }

    public int minBrojPoteza() {
        int min = 9999;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiBrojPoteza() < min) {
                min = igraci[i].dohvatiBrojPoteza();
            }
        }
        return min;
    }

    public int dohvatiIdIgraca(String ime) {
        int id = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dajIme().equals(ime)) {
                id = i;
            }
        }
        return id + 1;
    }

    public int brojPotezaIgraca(int id) {
        int brojPoteza = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                brojPoteza = igraci[i].dohvatiBrojPoteza();
            }
        }
        return brojPoteza;
    }

    public void povecajBrojPoteza(int id) {
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                igraci[i].povecajBrojPoteza();
            }
        }
    }

    public void povecajBrojPogodaka(int id) {
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                igraci[i].povecajBrojPogodaka();
            }
        }
    }

    public void smanjiBrojBrodova(int id) {
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                igraci[i].smanjiBrojBrodova();
            }
        }
    }

    public int brojIgracaCekanje(int id) {//broj igraca koji se cekaju da odigraju
        int brojIgraca = 0;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                int igracPoteza = igraci[i].dohvatiBrojPoteza();
                for (int j = 0; i < trenutniBrojIgraca; i++) {
                    if (igraci[i].dohvatiBrojPoteza() < igracPoteza) {
                        brojIgraca++;
                    }
                }
            }
        }
        return brojIgraca;
    }

    public int brojBrodovaIgraca(int id) {
        int brojBrodova = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                brojBrodova = igraci[i].dohvatiBrojBrodova();
            }
        }
        return brojBrodova;
    }

}
