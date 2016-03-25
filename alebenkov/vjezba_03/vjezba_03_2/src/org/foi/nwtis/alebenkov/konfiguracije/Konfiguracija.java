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
public interface Konfiguracija {

    public void ucitajKonfiguraciju() throws NemaKonfiguracije;

    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije;

    public void spremiKonfiguraciju() throws NeispravnaKonfiguracija;

    public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija;

    public void dodajKonfiguraciju(Properties postavke);

    public void dodajKonfiguraciju(Konfiguracija konfig);

    public void kopirajKonfiguraciju(Properties postavke);

    public void kopirajKonfiguraciju(Konfiguracija konfig);

    public Properties dajSvePostavke();

    public Enumeration dajPostavke();

    public boolean obrisiSvePostavke();

    public String dajPostavku(String postavka);

    public boolean spremiPostavku(String postavka, String vrijednost);

    public boolean azurirajPostavku(String postavka, String vrijednost);

    public boolean postojiPostavka(String postavka);

    public boolean obrisiPostavku(String postavka);

}
