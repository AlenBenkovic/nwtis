/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.konfiguracije.bp;

import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author abenkovic
 */
public class BP_konfiguracija implements BP_Sucelje {

    Konfiguracija konfig;
    private boolean status = false;

    private String serverDB;
    private String adminUsername;
    private String adminPassword;
    private String adminDatabase;

    private String userUsername;
    private String userPassword;
    private String userDatabase;

    private String driverDatabase;

    public BP_konfiguracija(String datoteka) {
        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            status = true;
            serverDB = konfig.dajPostavku("server.database");
            adminUsername = konfig.dajPostavku("admin.username");
            adminPassword = konfig.dajPostavku("admin.password");
            adminDatabase = konfig.dajPostavku("admin.database");
            userUsername = konfig.dajPostavku("user.username");
            userPassword = konfig.dajPostavku("user.password");
            userDatabase = konfig.dajPostavku("user.database");
            driverDatabase = getDriverDatabase(serverDB);

        } catch (NemaKonfiguracije ex) {
            System.out.println("Nema konfiguracija " + ex.getMessage());
        }
    }

    public boolean getStatus() {
        return this.status;
    }

    @Override
    public String getAdminDatabase() {
        return this.adminDatabase;
    }

    @Override
    public String getAdminPassword() {
        return this.adminPassword;
    }

    @Override
    public String getAdminUsername() {
        return this.adminUsername;
    }

    @Override
    public String getDriverDatabase() {
        return this.driverDatabase;
    }

    @Override
    public String getDriverDatabase(String bp_url) {
        String[] url = bp_url.split(":");
        String tipDrivera = url[1];
        String odabraniDriver = null;
        Properties driveri = getDriversDatabase();
        for (Enumeration e = driveri.keys(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String[] d = key.split("\\.");
            if (d.length == 3) {
                String tipDrivera2 = d[2];
                if (tipDrivera2.equals(tipDrivera)) {
                    odabraniDriver = driveri.getProperty(key);
                }
            }
        }

        return odabraniDriver;
    }

    @Override
    public Properties getDriversDatabase() {
        Properties drivers = new Properties();
        for (Enumeration e = konfig.dajPostavke(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            if (key.startsWith("driver.database")) {
                String driver = konfig.dajPostavku(key);
                drivers.setProperty(key, driver);
            }
        }
        return drivers;
    }

    @Override
    public String getServerDatabase() {
        return this.serverDB;
    }

    @Override
    public String getUserDatabase() {
        return this.userDatabase;
    }

    @Override
    public String getUserPassword() {
        return this.userPassword;
    }

    @Override
    public String getUserUsername() {
        return this.userUsername;
    }

}
