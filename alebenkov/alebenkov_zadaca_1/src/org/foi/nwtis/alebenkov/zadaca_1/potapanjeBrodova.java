package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.Arrays;

/**
 * @author alen benkovic
 */
public class potapanjeBrodova {

    private final int brojX;//velicina ploce
    private final int brojY;//velicina ploce
    private final int brojIgraca;
    private final int brojBrodova;
    private final int[][] poljeBrodova;

    public potapanjeBrodova(int brojIgraca) {
        this.brojX = 3 + (int) (Math.random() * ((10 - 3) + 1)); //http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range;
        this.brojY = 3 + (int) (Math.random() * ((10 - 3) + 1));
        this.brojBrodova = 2 + (int) (Math.random() * ((5 - 2) + 1));
        this.brojIgraca = brojIgraca;
        this.poljeBrodova = new int[brojX][brojY];
    }
    
    public potapanjeBrodova(int x, int y, int brojIgraca, int brojBrodova) {
        this.brojX = x;
        this.brojY = y;
        this.brojIgraca = brojIgraca;
        this.brojBrodova = brojBrodova;
        this.poljeBrodova = new int[brojX][brojY];
    }

    public void kreirajBrodove() {
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
        if (this.brojX<=x | this.brojY<=y){
            System.out.println("Neispravne koordinate!");
            return false;
        }
        else if (this.poljeBrodova[x][y] == idIgraca) {
            System.out.println("POGODAK!");
            return true;
        } else {
            System.out.println("PROMASAJ!");
            return false;
        }
    }
    
    
    

}
