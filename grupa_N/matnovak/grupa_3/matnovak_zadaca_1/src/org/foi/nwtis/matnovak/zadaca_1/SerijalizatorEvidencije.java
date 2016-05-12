/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.zadaca_1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_3
 */
public class SerijalizatorEvidencije extends Thread {

    protected String datoteka;
    protected int vrijemeCiklusa = 10000;//TODO preuzmi iz konfiguracije
    protected ServerSustava serverSustava = null;

    public SerijalizatorEvidencije(String datoteka, ServerSustava server) {
        this.datoteka = datoteka;
        this.serverSustava = server;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        
        //TODO osigurati međusobnu isključivost s ostalim dretvama (ObradaZahtjeva)
        while (true) {
            try {
                FileOutputStream out = new FileOutputStream(datoteka);
                ObjectOutputStream s = new ObjectOutputStream(out);               
                s.writeObject(serverSustava.evidencija);
                s.close();
                out.close();
                sleep(vrijemeCiklusa);
                //TODO obavi korekciju spavanja za utrošeno vrijeme rada dretve
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    @Override
    public synchronized void start() {
        System.out.println("POKRENUTA DRETVA SERIJALIZACIJE!");
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
