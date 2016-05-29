/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.rest.klijenti.OWMKlijent;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;
import org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS;

/**
 *
 * @author grupa_1
 */
public class PreuzmiMeteoPodatke extends Thread {

    private boolean dretvaRadi = true;
    private int intervalSpavanja;
    private BP_konfiguracija konfigBP = null;
    private Konfiguracija konfig = null;
    private String APPID;
    private List<Adresa> adrese;


    public PreuzmiMeteoPodatke() {
        this.intervalSpavanja = Integer.parseInt(SlusacAplikacije.getKonfigOstalog().dajPostavku("intervalDretve")) * 1000;
        this.konfigBP = SlusacAplikacije.getKonfigBP();
        this.adrese = new ArrayList<>();

    }

    @Override
    public void interrupt() {
        this.dretvaRadi = false;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        this.konfig = SlusacAplikacije.getKonfigOstalog();
        this.APPID = konfig.dajPostavku("APPID");
        while (dretvaRadi) {
            long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
            System.out.println("Dretva radi...");

            OperacijeBP obp = new OperacijeBP();
            this.adrese = obp.ucitajAdrese();

            for (int i = 0; i < adrese.size(); i++) {
                OWMKlijent owmk = new OWMKlijent(APPID);
                MeteoPodaci mp = owmk.getRealTimeWeather(adrese.get(i).getGeoloc().getLatitude(), adrese.get(i).getGeoloc().getLongitude());
                System.out.println(mp.getWeatherMain());
                if(obp.spremiMeteo(adrese.get(i), mp)){
                    System.out.println("Meteo podatak za " + adrese.get(i).getAdresa() + " uspjesno spremljen u METEO tablicu.");
                } else {
                    System.out.println("Huston, we have a problem.");
                }
            }
            
 

            long krajRadaDretve = System.currentTimeMillis(); //biljezim kraj rada dretve
            long trajanjeRadaDretve = krajRadaDretve - pocetakRadaDretve;
            try {
                sleep(intervalSpavanja - trajanjeRadaDretve);//odlazim na spavanje
            } catch (InterruptedException ex) {
                System.out.println("Dretva prekinuta...");
            }
        }

    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
