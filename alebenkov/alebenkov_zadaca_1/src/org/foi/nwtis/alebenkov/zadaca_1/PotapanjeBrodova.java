package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Klasa same igre. Sadrzi sve relevantne informacije o igri
 *
 * @author alen benkovic
 */
public class PotapanjeBrodova implements Serializable {

    private final int brojX;//velicina ploce
    private final int brojY;//velicina ploce
    private final int brojIgraca;//ukupan broj igraca koji igra
    private int trenutniBrojIgraca;//trenutni broj igraca koji je prijavljen za igru
    private final int brojBrodova;//broj brodova po igracu
    private int[][] poljeBrodova;//polje u koje se spremaju svi brodovi svih igraca
    private final int idIgre;
    private Igrac[] igraci; //polje u koje se spremaju svi igraci
    private boolean igraKreirana = false; //sluzi kako se igraci ne bi prijavljivali prije nego sto igra pocne

    /**
     * Konstruktor klase PotapanjeBrodova.
     *
     * @param brojIgraca maksimalni broj igraca koji igraju
     * @param x velicina ploce
     * @param y velicina ploce
     * @param brojBrodova broj brodova po igracu
     */
    public PotapanjeBrodova(int brojIgraca, int x, int y, int brojBrodova) {
        this.trenutniBrojIgraca = 0;
        this.igraKreirana = false;
        this.brojX = x;
        this.brojY = y;
        this.brojBrodova = brojBrodova;
        //this.brojBrodova = 2 + (int) (Math.random() * ((5 - 2) + 1));//http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range;
        this.brojIgraca = brojIgraca;
        this.poljeBrodova = new int[brojX][brojY];
        this.igraci = new Igrac[brojIgraca];
        this.idIgre = 1 + (int) (Math.random() * ((1000 - 1) + 1)); //generiram nasumicni broj. Ovdje bi puno bolje bilo koristiti recimo UUID
        System.out.println("IGRA | Kreiram novu igru sa ID igre: " + idIgre);
    }

    public int[][] getPoljeBrodova() {
        return poljeBrodova;
    }

    /**
     * Metoda za kreiranje same ploce te postavljanje brodova na nasumicne
     * pozicije. Svaki broj na ploci predstavlja ID igraca
     */
    public void kreirajBrodove() {
        igraKreirana = true;
        System.out.println("IGRA | Kreiram novu igru...");
        System.out.println("IGRA | Ploca velicine " + this.brojX + " X " + this.brojY);
        System.out.println("IGRA | Broj IGRACA:" + this.brojIgraca);
        System.out.println("IGRA | Broj BRODOVA:" + this.brojBrodova);

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
        this.prikazSvihBrodova();
    }

    /**
     * Metoda koja provjerava je li igrac pogodio brod
     *
     * @param idIgraca
     * @param x koordinata
     * @param y koordinata
     * @return true ukoliko je brod pogodjen, inace false
     */
    public boolean pogodiBrod(int idIgraca, int x, int y) {
        if (this.brojX <= x | this.brojY <= y) {
            System.out.println("IGRA | Neispravne koordinate!");
            return false;
        } else if (this.poljeBrodova[x][y] != idIgraca & this.poljeBrodova[x][y] != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int[][] koordinateBrodovaIgraca(int idIgraca) {
        int brojac=0;
        int[][] koordinate = new int[brojBrodova][2];
        for (int i = 0; i < poljeBrodova.length; i++) {
            for (int j = 0; j < poljeBrodova[0].length; j++) {
                if (poljeBrodova[i][j] == idIgraca) {
                    koordinate[brojac][0] = i + 1;
                    koordinate[brojac][1] = j + 1;
                    brojac++;
                }
            }
        }
        return koordinate;
    }

    /**
     * Metoda koja brise pogodjeni brod sa ploce
     *
     * @param x
     * @param y
     */
    //ovo mi sluzi samo kako bi mogao pokupiti id igraca ciji je brod prethodno pogodjen (Ukoliko ga odma obrisem ne mogu uzeti vrijednost polja odnosno id)
    public void potopiBrod(int x, int y) {
        this.poljeBrodova[x][y] = 0;
    }

    /**
     * Metoda koja uzima vrijednost ploce na odredjenoj koordinati
     *
     * @param x
     * @param y
     * @return vrijenost polja na xy koordinati (int)
     */
    public int vrijednostPolja(int x, int y) {
        return this.poljeBrodova[x][y];
    }

    /**
     * Metoda koja provjerava ima li slobodnih mjesta za igru
     *
     * @return true ukoliko ima slobodnih mjesta, inace false
     */
    public boolean provjeraSlobodnihMjesta() {
        if (trenutniBrojIgraca < brojIgraca) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metoda za dohvacanje ID igre
     *
     * @return id igre (int)
     */
    public int dohvatiIdIgre() {
        return this.idIgre;
    }

    /**
     * Metoda koja dohvaca id igre zadanog igraca
     *
     * @param ime igrac za kojeg se trazi id igre
     * @return id igre (int) za trazenog igraca
     */
    public int dohvatiIdIgreKorisnika(String ime) {
        int idIgre = -1;
        if (trenutniBrojIgraca != 0) {
            for (int i = 0; i < trenutniBrojIgraca; i++) {
                if (igraci[i].dajIme().equals(ime)) {
                    idIgre = igraci[i].dohvatiIdIgre();
                }
            }
        }
        return idIgre;
    }

    /**
     * Metoda za prijavu igraca za igru. Kreira objekt igraca i sprema ga u
     * polje igraci.
     *
     * @param ime
     * @return true ukoliko se igrac uspjesno prijavio, inace false
     */
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

    /**
     * Vraca najmanji broj poteza svih igraca
     *
     * @return najmanji broj poteza (int)
     */
    public int minBrojPoteza() {
        int min = 9999;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiBrojPoteza() < min) {
                min = igraci[i].dohvatiBrojPoteza();
            }
        }
        return min;
    }

    /**
     * Dohvat id igraca
     *
     * @param ime trazenog igraca
     * @return id igraca (int)
     */
    public int dohvatiIdIgraca(String ime) {
        int id = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dajIme().equals(ime)) {
                id = i;
            }
        }
        return id + 1;
    }

    /**
     * Dohvat imena igraca
     *
     * @param id trazenog igraca
     * @return ime igraca (String)
     */
    public String dohvatiImeIgraca(int id) {
        String ime = null;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                ime = igraci[i].dajIme();
            }
        }
        return ime;
    }

    /**
     * Vraca ukupan broj poteza za trazenog igraca
     *
     * @param id igraca
     * @return ukupan broj poteza za trazenog igraca (int)
     */
    public int brojPotezaIgraca(int id) {
        int brojPoteza = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                brojPoteza = igraci[i].dohvatiBrojPoteza();
            }
        }
        return brojPoteza;
    }

    /**
     * Povecava ukupan broj poteza nakon pokusaja za odredjenog igraca
     *
     * @param id
     */
    public void povecajBrojPoteza(int id) {
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                igraci[i].povecajBrojPoteza();
            }
        }
    }

    /**
     * Metoda koja pove
     *
     * @param id
     */
    public void povecajBrojPogodaka(int id) {
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                igraci[i].povecajBrojPogodaka();
            }
        }
    }

    /**
     * Provjera tko je pobjednik
     *
     * @return id pobjednika (int)
     */
    public int pobjednik() {
        int pobjednikID = -1;
        int maxBrojPogodaka = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiBrojPogodaka() > maxBrojPogodaka) {
                maxBrojPogodaka = igraci[i].dohvatiBrojPogodaka();
                pobjednikID = i + 1;
            }
        }
        return pobjednikID;
    }

    /**
     * Smanjuje broj brodova odredjenog igraca
     *
     * @param id igraca ciji je brod pogodjen
     */
    public void smanjiBrojBrodova(int id) {
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                igraci[i].smanjiBrojBrodova();
            }
        }
    }

    /**
     * Provjerava koliko ima igraca koji moraju odigrati prije igraca za kojeg
     * se trazi provjera
     *
     * @param id igraca za kojeg se trazi provjera
     * @return broj igraca koji jos moraju odigrati (int)
     */
    public int brojIgracaCekanje(int id) {//broj igraca koji se cekaju da odigraju
        int brojIgraca = 0;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                int igracPoteza = igraci[i].dohvatiBrojPoteza();
                for (int j = 0; j < trenutniBrojIgraca; j++) {
                    if (igraci[j].dohvatiBrojPoteza() < igracPoteza) {
                        brojIgraca++;
                    }
                }
            }
        }
        return brojIgraca;
    }

    /**
     * Provjerava koliko odredjeni igrac jos ima brodova
     *
     * @param id igraca za kojeg se trazi provjera
     * @return ukupan broj brodova odredjenog igraca (int)
     */
    public int brojBrodovaIgraca(int id) {
        int brojBrodova = -1;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() == id) {
                brojBrodova = igraci[i].dohvatiBrojBrodova();
            }
        }
        return brojBrodova;
    }

    /**
     * Provjerava jesu li svi neprijatelji unisteni
     *
     * @param id igraca za kojeg se trazi provjera
     * @return true ukoliko su svi neprijatelji unisteni, inace false
     */
    public boolean neprijateljiUnisteni(int id) {
        boolean neprijateljiUnisteni = false;
        for (int i = 0; i < trenutniBrojIgraca; i++) {
            if (igraci[i].dohvatiId() != id) {
                if (igraci[i].dohvatiBrojBrodova() > 0) {
                    neprijateljiUnisteni = false;
                } else {
                    neprijateljiUnisteni = true;
                }
            }
        }
        return neprijateljiUnisteni;
    }

    /**
     * Sluzi za prikaz svih brodova na ploci
     */
    public void prikazSvihBrodova() {
        System.out.println("IGRA | Polje brodova: " + Arrays.deepToString(this.poljeBrodova));
        for (int i = 0; i < poljeBrodova.length; i++) {

            for (int j = 0; j < poljeBrodova[0].length; j++) {
                System.out.print(poljeBrodova[i][j] + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Sluzi za prikaz pogodjenog broda na ploci
     */
    public void prikazPogodjenogBroda(int x, int y) {
        for (int i = 0; i < poljeBrodova.length; i++) {
            for (int j = 0; j < poljeBrodova[0].length; j++) {
                if (i == x && j == y) {
                    System.out.print("X\t");
                } else {
                    System.out.print(poljeBrodova[i][j] + "\t");
                }

            }
            System.out.print("\n");
        }
    }

    /**
     * Provjera je li igra kreirana
     *
     * @return true ukoliko je igra kreirana
     */
    public boolean igraKreirana() {//test obrisi
        return igraKreirana;
    }

    public String velicinaPloce() {
        String velicina = this.brojX + "x" + this.brojY;
        return velicina;
    }

    public int getBrojIgraca() {
        return brojIgraca;
    }

    public int getBrojBrodova() {
        return brojBrodova;
    }

}
