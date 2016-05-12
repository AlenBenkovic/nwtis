/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_1
 */
public class ObradaZahtjeva extends Thread {
    protected Socket vrata = null;
    protected Konfiguracija konfig = null;
    
    public ObradaZahtjeva(Socket vrata, Konfiguracija konfig) {
        this.vrata = vrata;
        this.konfig = konfig;
    }
    
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            InputStream is = vrata.getInputStream();
            OutputStream os = vrata.getOutputStream();
            StringBuffer zahtjev = new StringBuffer();
            int bajt;
            while((bajt = is.read()) != -1) {
                zahtjev.append((char) bajt);                
            }
            System.out.println(zahtjev);
            String odgovor = "OK;";
            os.write(odgovor.getBytes());
            os.flush();
            is.close();
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
