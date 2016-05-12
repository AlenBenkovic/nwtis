/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_3
 */
public class DretvaPromjena extends Thread{

    private SlusacPromjena slusacPromjena = null;
    private int interval = 100;
    private String nazivKlase = null;
    private Brojaci objekt = null;

    public DretvaPromjena(SlusacPromjena sp, int interval, String nazivKlase) {
        this.slusacPromjena = sp;
        this.interval = interval;
        this.nazivKlase = nazivKlase;
    }
    
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (true) {            
            objekt.run();
            try {
                sleep(interval*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DretvaPromjena.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        try {
            Class klasa = Class.forName(this.nazivKlase);
            objekt = (Brojaci) klasa.newInstance();
            objekt.dodajSlusaca(slusacPromjena);
            super.start(); //To change body of generated methods, choose Tools | Templates.
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DretvaPromjena.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DretvaPromjena.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DretvaPromjena.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}