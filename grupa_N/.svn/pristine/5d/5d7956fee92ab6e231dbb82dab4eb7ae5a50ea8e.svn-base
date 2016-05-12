/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.konfiguracije.bp;

import java.util.Properties;
import org.foi.nwtis.matnovak.konfiguracije.Konfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;

/**
 * Klasa za rad sa podacima konfiguracije za bazu podataka
 * @author Matija Novak
 * @see BP_Sucelje
 */
public class BP_Konfiguracija implements BP_Sucelje{
    private Konfiguracija konfig = null;

    /**
     * Kreira objekt klase BP_konfiguracija
     * 
     * @param datoteka naziv datoteke postavki
     * @throws NemaKonfiguracije 
     */
    public BP_Konfiguracija(String datoteka) throws NemaKonfiguracije {
        this.konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
    }

    /**
     * Vraća naziv admin baze
     * @return String
     */
    @Override
    public String getAdminDatabase() {
        return this.konfig.dajPostavku("admin.database");
    }

    /**
     * Vraća admin lozinku
     * @return String
     */
    @Override
    public String getAdminPassword() {
        return this.konfig.dajPostavku("admin.password");
    }

    /**
     * Vraća admin korisničko ime
     * @return String
     */
    @Override
    public String getAdminUsername() {
        return this.konfig.dajPostavku("admin.username");
    }

    /**
     * Vraća driver na temelju učitanog servera
     * @return String
     */
    @Override
    public String getDriverDatabase() {
        return this.getDriverDatabase(this.getServerDatabase());
    }

    /**
     * Vraća driver na temelju predanog servera
     * @param bp_url - server url
     * @return String
     */
    @Override
    public String getDriverDatabase(String bp_url) {
        return this.konfig.dajPostavku("driver.database."+bp_url.split(":")[1]);
    }

    /**
     * Vraća popis svih drivera
     * @return Properties
     */
    @Override
    public Properties getDriversDatabase() {
        Properties p = new Properties();
        
        for (Object kljuc : this.konfig.dajSvePostavke().keySet()) {
            String k = (String) kljuc;
            if(k.startsWith("driver.database.")){
                p.setProperty(k, this.konfig.dajPostavku(k));
            }
        }
        
        return p;
    }

    /**
     * Vraća putanju do servera
     * @return String
     */
    @Override
    public String getServerDatabase() {
        return this.konfig.dajPostavku("server.database");
    }

    /**
     * Vraća naziv baze korisnika
     * @return String
     */
    @Override
    public String getUserDatabase() {
        return this.konfig.dajPostavku("user.database");
    }

    /**
     * Vraća lozinku korisnika
     * @return String
     */
    @Override
    public String getUserPassword() {
        return this.konfig.dajPostavku("user.password");
    }

    /**
     * Vraća korisničko ime korisnika za spajanje na bazu podataka
     * @return String
     */
    @Override
    public String getUserUsername() {
        return this.konfig.dajPostavku("user.username");
    }
}
