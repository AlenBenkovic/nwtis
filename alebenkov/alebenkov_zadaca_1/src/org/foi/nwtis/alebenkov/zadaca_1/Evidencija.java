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
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

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
     * @param biljeska dodatna biljeska
     */
    public synchronized void dodajZapis(String imeIgraca, int x, int y, String biljeska) { //metoda je synchronized kako bi se provodilo medjusobno iskljucivanje dretvi
        EvidencijaZapis ez = new EvidencijaZapis(imeIgraca, x, y, biljeska);
    }

    /**
     * Ispisuje korisne informacije same evidencije na ekran
     */
    public void prikazEvidencije() {
        for (int j = 0; j < this.evidencija.size(); j++) {
            EvidencijaZapis ev = evidencija.get(j);
            System.out.println("Vrijeme: " + ev.vrijeme + "| Ime igraca: " + ev.imeIgraca + "| Gadjana lokacija: " + ev.x + "," + ev.y + "| Biljeska: " + ev.biljeska );
            System.out.println("Igre kreirana:" + igra.igraKreirana());
        }
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

        private String vrijeme;
        private String imeIgraca;
        private int x;
        private int y;
        private String biljeska;

        /**
         * Konstruktor klase za spremanje zapisa.
         *
         * @param imeIgraca
         * @param x koordinata pozicije koja se gadja
         * @param y koordinata pozicije koja se gadja
         * @param biljeska dodatna biljeska
         */
        public EvidencijaZapis(String imeIgraca, int x, int y, String biljeska) {
            Date trenutnoVrijeme = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
            this.vrijeme = sdf.format(trenutnoVrijeme);
            this.imeIgraca = imeIgraca;
            this.x = x;
            this.y = y;
            this.biljeska = biljeska;
            evidencija.add(this);
        }

    }

}
