/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.konfiguracije;

import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author grupa_1
 */
public abstract class KonfiguracijaApstraktna implements Konfiguracija {

    String datoteka;
    Properties postavke = new Properties();

    public KonfiguracijaApstraktna(String datoteka) {
        this.datoteka = datoteka;
    }

    @Override
    public void dodajKonfiguraciju(Properties postavke) {
        this.postavke.putAll(postavke); // postojeće elemente zamjenjuje
    }

    @Override
    public void dodajKonfiguraciju(Konfiguracija konfig) {
        this.postavke.putAll(konfig.dajSvePostavke());
    }

    @Override
    public void kopirajKonfiguraciju(Properties postavke) {
        this.postavke.clear();
        this.postavke.putAll(postavke);
    }

    @Override
    public void kopirajKonfiguraciju(Konfiguracija konfig) {
        this.postavke.clear();
        this.postavke.putAll(konfig.dajSvePostavke());
    }

    @Override
    public Properties dajSvePostavke() {
        Properties p = new Properties();
        p.putAll(this.postavke);
        return p;
    }

    @Override
    public Enumeration dajPostavke() {
        return this.postavke.keys();
    }

    @Override
    public boolean obrisiSvePostavke() {
        if (this.postavke.isEmpty()) {
            return false;
        } else {
            this.postavke.clear();
            return true;
        }
    }

    @Override
    public String dajPostavku(String postavka) {
        return this.postavke.getProperty(postavka);
    }

    @Override
    public boolean spremiPostavku(String postavka, String vrijednost) {
        if (this.postavke.containsKey(postavka)) {
            return false;
        } else {
            this.postavke.setProperty(postavka, vrijednost);
            return true;
        }
    }

    @Override
    public boolean azurirajPostavku(String postavka, String vrijednost) {
        if (this.postavke.containsKey(postavka)) {
            this.postavke.setProperty(postavka, vrijednost);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean postojiPostavka(String postavka) {
        return this.postavke.containsKey(postavka);
    }

    @Override
    public boolean obrisiPostavku(String postavka) {
        if (this.postavke.containsKey(postavka)) {
            this.postavke.remove(postavka);
            return true;
        } else {
            return false;
        }
    }

    public static Konfiguracija kreirajKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
        Konfiguracija konfig = null;
        if (datoteka == null || datoteka.length() == 0) {
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan.");
        }
        if (datoteka.toLowerCase().endsWith(".txt")) {
            konfig = new KonfiguracijaTxt(datoteka);
        } else if (datoteka.toLowerCase().endsWith(".xml")) {
            konfig = new KonfiguracijaXML(datoteka);
        } else {
            throw new NeispravnaKonfiguracija("Nije poznata vrsta konfiguracije.");
        }
        konfig.spremiKonfiguraciju();
        return konfig;
    }

    public static Konfiguracija preuzmiKonfiguraciju(String datoteka) throws NemaKonfiguracije {
        Konfiguracija konfig = null;
        if (datoteka == null || datoteka.length() == 0) {
            throw new NemaKonfiguracije("Naziv datoteke nije ispravan.");
        }
        if (datoteka.toLowerCase().endsWith(".txt")) {
            konfig = new KonfiguracijaTxt(datoteka);
        } else if (datoteka.toLowerCase().endsWith(".xml")) {
            konfig = new KonfiguracijaXML(datoteka);
        } else {
            throw new NemaKonfiguracije("Nije poznata vrsta konfiguracije.");
        }
        konfig.ucitajKonfiguraciju();
        return konfig;
    }
}
