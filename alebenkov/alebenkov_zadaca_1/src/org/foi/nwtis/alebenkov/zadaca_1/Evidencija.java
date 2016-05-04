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
 * @author Alen Benkovic
 */
public class Evidencija implements Serializable {

    private PotapanjeBrodova igra; 
    private ArrayList<EvidencijaZapis> evidencija = new ArrayList(); //ovdje spremam zapise o radu servera

    /**
     * Konstruktor klase
     * @param igra igra koja se sprema u datoteku i vodi evidencija
     */
    public Evidencija(PotapanjeBrodova igra) {
        this.igra = igra;

    }

    /**
     * Metoda koja kreira novi objekt klase EvidencijaZapis za svaki zapis koji primim
     * @param ipAdresa adresa sa koje je dosao zahtjev
     * @param zahtjev naredba igraca
     * @param odgovor odgovor servera
     */
    public void dodajZapis(String ipAdresa, String zahtjev, String odgovor) {
        EvidencijaZapis ez = new EvidencijaZapis(ipAdresa, zahtjev, odgovor);
    }
    
    /**
     * Ispisuje korisne informacije same evidencije na ekran
     */
    public void prikazEvidencije() {
        for (int j = 0; j < this.evidencija.size(); j++) {
            EvidencijaZapis ev = evidencija.get(j);
            System.out.println("Vrijeme: " + ev.vrijeme + "| IP adresa: " + ev.ipAdresa + "| Zahtjev: " + ev.zahtjev + "| Odgovor: " + ev.odgovor);
            System.out.println("Igre kreirana:"+ igra.igraKreirana());
        }
    }
    
    /**
     * Vraca spremljenu igru iz datoteke
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
        private String ipAdresa;
        private String zahtjev;
        private String odgovor;

        /**
         * Konstruktor klase za spremanje zapisa.
         * @param ipAdresa adresa igraca
         * @param zahtjev zahtjev igraca
         * @param odgovor odgovor servera
         */
        public EvidencijaZapis(String ipAdresa, String zahtjev, String odgovor) {
            Date trenutnoVrijeme = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
            this.vrijeme = sdf.format(trenutnoVrijeme);
            this.ipAdresa = ipAdresa;
            this.zahtjev = zahtjev;
            this.odgovor = odgovor;
            evidencija.add(this);
        }

    }

}
