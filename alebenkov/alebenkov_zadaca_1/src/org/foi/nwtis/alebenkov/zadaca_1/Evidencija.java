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
 *
 * @author abenkovic
 */
public class Evidencija implements Serializable {

    private PotapanjeBrodova igra;
    private ArrayList<EvidencijaZapis> evidencija = new ArrayList();

    public Evidencija(PotapanjeBrodova igra) {
        this.igra = igra;

    }

    public void dodajZapis(String ipAdresa, String zahtjev, String odgovor) {
        EvidencijaZapis ez = new EvidencijaZapis(ipAdresa, zahtjev, odgovor);
    }

    public void prikazEvidencije() {
        for (int j = 0; j < this.evidencija.size(); j++) {
            EvidencijaZapis ev = evidencija.get(j);
            System.out.println("Vrijeme: " + ev.vrijeme + "| IP adresa: " + ev.ipAdresa + "| Zahtjev: " + ev.zahtjev + "| Odgovor: " + ev.odgovor);
        }
    }

    public PotapanjeBrodova dohvatiSpremljenuIgru() {
        return igra;
    }

    public class EvidencijaZapis implements Serializable {

        private String vrijeme;
        private String ipAdresa;
        private String zahtjev;
        private String odgovor;

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
