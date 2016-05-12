/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mantovak.zadaca_1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_2
 */
public class SerijalizatorEvidencije extends Thread {

    protected String datoteka;
    protected int vrijemeCiklusa = 10000; //TODO preuzmi iz konfiguracije
    protected ServerSustava serverSustava;
    
    SerijalizatorEvidencije(String datotekaEvidencije, ServerSustava serverSustava) {
        this.datoteka = datotekaEvidencije;
        this.serverSustava = serverSustava;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        //TODO osigurati međusobnu isključivost s ostalim dretvama (ObradaZahtjeva)
        while (true) { 
             Evidencija evidencija = serverSustava.evidencija;
             try {
                OutputStream os = new FileOutputStream(this.datoteka);
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(evidencija);
                sleep(vrijemeCiklusa);//TODO obavi korekciju spavanja za utrošeno vrijeme rada dretve
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
