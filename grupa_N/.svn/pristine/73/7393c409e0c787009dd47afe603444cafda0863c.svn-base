/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.konfiguracije.bp;

import java.util.Enumeration;
import java.util.Properties;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_1
 */
public class BP_Konfiguracija implements BP_Sucelje {

    private Konfiguracija konfiguracija;

    /**
     * Kreira objekt klase BP_Konfiguracija
     *
     * @param datoteka naziv datoteke postavki
     * @throws NemaKonfiguracije
     */
    public BP_Konfiguracija(String datoteka) throws NemaKonfiguracije {
        konfiguracija = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
    }

    /**
     * Vraća naziv baze podataka za administratora
     *
     * @return String
     */
    @Override
    public String getAdminDatabase() {
        return konfiguracija.dajPostavku("admin.database");
    }

    /**
     * Vraća lozinku administratora
     *
     * @return String
     */
    @Override
    public String getAdminPassword() {
        return konfiguracija.dajPostavku("admin.password");
    }

    /**
     * Vraća korisničko ime administratora
     *
     * @return String
     */
    @Override
    public String getAdminUsername() {
        return konfiguracija.dajPostavku("admin.username");
    }

    /**
     * Vraća naziv drivera za bazu podataka
     * @return String
     */
    @Override
    public String getDriverDatabase() {
        return getDriverDatabase(this.getServerDatabase());
    }

    /**
     * Vraća naziv drivera za bazu podataka iz argumenta
     * @param bp_url
     * @return String
     */
    @Override
    public String getDriverDatabase(String bp_url) {
        String url[] = bp_url.split(":");
        String driver = konfiguracija.dajPostavku("driver.database." + url[1]);
        return driver;
    }

    /**
     * Vraća sve drivere za baze podataka
     * @return String
     */
    @Override
    public Properties getDriversDatabase() {
        Properties p = new Properties();
        for(Enumeration e=konfiguracija.dajPostavke();e.hasMoreElements();) {
            String k = (String) e.nextElement();
            if(k.startsWith("driver.database.")) {
                p.setProperty(k, konfiguracija.dajPostavku(k));
            }
        }
        return p;
    }

    /**
     * Vraća naziv poslužitelja baze podataka
     * @return String
     */
    @Override
    public String getServerDatabase() {
        return konfiguracija.dajPostavku("server.database");
    }

    /**
     * Vraća naziv baze podataka za korisnika
     * @return String
     */
    @Override
    public String getUserDatabase() {
        return konfiguracija.dajPostavku("user.database");
    }

    /**
     * Vraća lozinku korisnika
     * @return String
     */
    @Override
    public String getUserPassword() {
        return konfiguracija.dajPostavku("user.password");
    }

    /**
     * Vraća korisničko ime korisnika
     * @return 
     */
    @Override
    public String getUserUsername() {
        return konfiguracija.dajPostavku("user.username");
    }

}
