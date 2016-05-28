/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
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

    public PreuzmiMeteoPodatke() {
        this.intervalSpavanja = Integer.parseInt(SlusacAplikacije.getKonfigOstalog().dajPostavku("intervalDretve")) * 1000;
        this.konfigBP = SlusacAplikacije.getKonfigBP();

    }

    @Override
    public void interrupt() {
        this.dretvaRadi = false;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (dretvaRadi) {
            long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
            
            System.out.println("Dretva radi...");
            
            
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
