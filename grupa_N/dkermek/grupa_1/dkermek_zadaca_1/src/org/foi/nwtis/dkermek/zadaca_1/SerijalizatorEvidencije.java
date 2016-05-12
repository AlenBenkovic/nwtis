/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.zadaca_1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_1
 */
public class SerijalizatorEvidencije extends Thread {

    protected String datoteka;
    protected ServerSustava serverSustava;
    protected int vrijemeCiklusa = 10000; // todo preuzmi iz konfiguracije

    public SerijalizatorEvidencije(String datoteka, ServerSustava serverSustava) {
        this.datoteka = datoteka;
        this.serverSustava = serverSustava;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        // todo osigirati međusobnu isključivost s ostalim dretvama (ObradaZahtjeva)
        while (true) {
            Evidencija evidencija = serverSustava.evidencija;
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datoteka));
                oos.writeObject(evidencija);
                oos.close();
                sleep(vrijemeCiklusa); // todo obavi korenciju spavanja za utrošeno vrijem rada dretve
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
