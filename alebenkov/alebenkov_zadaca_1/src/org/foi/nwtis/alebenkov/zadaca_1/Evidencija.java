/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Klasa zaduzena sa evidenciju igre koja se moze serijalizirati
 *
 * @author Alen Benkovic
 */
public class Evidencija implements Serializable {

    private PotapanjeBrodova igra;
    private ArrayList<EvidencijaZapis> evidencija = new ArrayList(); //ovdje spremam zapise o radu servera

    /**
     * Konstruktor klase
     *
     * @param igra igra koja se sprema u datoteku i vodi evidencija
     */
    public Evidencija(PotapanjeBrodova igra) {
        this.igra = igra;

    }

    /**
     * Metoda koja kreira novi objekt klase EvidencijaZapis za svaki zapis koji
     * primim
     *
     * @param imeIgraca
     * @param x koordinata pozicije koja se gadja
     * @param y koordinata pozicije koja se gadja
     * @param poljeBrodova trenutno stanje ploce
     * @param biljeska dodatna biljeska
     */
    public synchronized void dodajZapis(String imeIgraca, int x, int y, int[][] poljeBrodova, String biljeska) { //metoda je synchronized kako bi se provodilo medjusobno iskljucivanje dretvi
        EvidencijaZapis ez = new EvidencijaZapis(imeIgraca, x, y, poljeBrodova, biljeska);
    }

    /**
     * Ispisuje korisne informacije same evidencije na ekran
     */
    public void prikazEvidencije() {
        for (int j = 0; j < this.evidencija.size(); j++) {
            EvidencijaZapis ev = evidencija.get(j);
            System.out.println("Vrijeme: " + ev.vrijeme + "| Ime igraca: " + ev.imeIgraca + "| Gadjana lokacija: " + ev.x + "," + ev.y + "| Biljeska: " + ev.biljeska);
            System.out.println("Igre kreirana:" + igra.igraKreirana());
        }
    }

    public ArrayList<EvidencijaZapis> dohvatiZapise() {
        return this.evidencija;
    }

    /**
     * Vraca spremljenu igru iz datoteke
     *
     * @return igra (PotapanjeBrodova)
     */
    public PotapanjeBrodova dohvatiSpremljenuIgru() {
        return igra;
    }

    /**
     * Klasa za pohranu zapisa o radu servera.
     */
    public class EvidencijaZapis implements Serializable {

        private final String vrijeme;
        private final String imeIgraca;
        private final int x;
        private final int y;
        private final String biljeska;
        private  int[][] poljeBrodova;

        /**
         * Konstruktor klase za spremanje zapisa.
         *
         * @param imeIgraca
         * @param x koordinata pozicije koja se gadja
         * @param y koordinata pozicije koja se gadja
         * @param poljeBrodova trenutno stanje ploce
         * @param biljeska dodatna biljeska
         */
        public EvidencijaZapis(String imeIgraca, int x, int y, int[][] poljeBrodova, String biljeska) {
            Date trenutnoVrijeme = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
            this.vrijeme = sdf.format(trenutnoVrijeme);
            this.imeIgraca = imeIgraca;
            this.x = x;
            this.y = y;
            this.biljeska = biljeska;
            this.poljeBrodova = new int[poljeBrodova.length][poljeBrodova[0].length];
            System.out.println("DULJINA: " + poljeBrodova.length + "DULJ2: " + poljeBrodova[0].length);
            for (int i=0; i<poljeBrodova.length; i++){ //za ovo vjerojatno postoji elegantnije rjesenje, probao sa arraycopy, copyof, clone ali ne radi :(
                for(int j=0; j<poljeBrodova[i].length; j++){
                    this.poljeBrodova[i][j] = poljeBrodova[i][j];
                }
            }
            evidencija.add(this);
        }

        public String getVrijeme() {
            return vrijeme;
        }

        public String getImeIgraca() {
            return imeIgraca;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getBiljeska() {
            return biljeska;
        }

        public int[][] getPoljeBrodova() {
            return poljeBrodova;
        }

    }

}
