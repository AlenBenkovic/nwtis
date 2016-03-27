/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1_15;

import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author alen
 */
public class SerijalizatorEvidencije15 extends Thread {
    
    Konfiguracija konfig;

    public SerijalizatorEvidencije15(Konfiguracija konfig) {
        super();
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
    super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        //TODO dovrsiti za serijalizaciju evidencije
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}