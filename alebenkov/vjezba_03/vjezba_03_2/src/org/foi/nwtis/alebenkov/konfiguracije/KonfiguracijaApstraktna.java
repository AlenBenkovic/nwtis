/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.konfiguracije;

import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author csx
 */
public abstract class KonfiguracijaApstraktna implements Konfiguracija {

    protected String datoteka;
    protected Properties postavke = new Properties();

    public KonfiguracijaApstraktna() {
    }

    public KonfiguracijaApstraktna(String datoteka) {
        this.datoteka = datoteka;
    }

    @Override
    public void dodajKonfiguraciju(Properties postavke) {
        for (Enumeration e = postavke.keys(); e.hasMoreElements();) {
            String k = (String) e.nextElement();
            String v = postavke.getProperty(k);
            this.postavke.setProperty(k, v);
        }
    }

    @Override
    public void dodajKonfiguraciju(Konfiguracija konfig) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kopirajKonfiguraciju(Properties postavke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kopirajKonfiguraciju(Konfiguracija konfig) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Properties dajSvePostavke() {
        return this.postavke;
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
        if (!this.postavke.containsKey(postavka)) {
            return false;
        } else {
            this.postavke.setProperty(postavka, vrijednost);
            return true;
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
        if(datoteka.endsWith(".txt")) {
            konfig = new KonfiguracijaTxt(datoteka);
        } else if(datoteka.endsWith(".xml")) {
            konfig = new KonfiguracijaXML(datoteka);
        } else {
            throw new NeispravnaKonfiguracija("Nepoznata vrsta konfiguracije!");
        }
        konfig.spremiKonfiguraciju();
        
        return konfig;
    }

    public static Konfiguracija preuzmiKonfiguraciju(String datoteka) throws NemaKonfiguracije {
        Konfiguracija konfig = null;
        if(datoteka.endsWith(".txt")) {
            konfig = new KonfiguracijaTxt(datoteka);
        } else if(datoteka.endsWith(".xml")) {
            konfig = new KonfiguracijaXML(datoteka);
        } else {
            throw new NemaKonfiguracije("Nepoznata vrsta konfiguracije!");
        }
        konfig.ucitajKonfiguraciju();
        
        return konfig;
    }

    public void spremiKonfiguraciju() throws NeispravnaKonfiguracija {
        this.spremiKonfiguraciju(this.datoteka);
    }

    public void ucitajKonfiguraciju() throws NemaKonfiguracije {
        this.ucitajKonfiguraciju(this.datoteka);
    }
}
