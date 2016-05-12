/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.matnovak.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_3
 */
class ObradaZahtjeva extends Thread {
    protected Socket socket = null;
    protected Konfiguracija konfig = null;
    
    ObradaZahtjeva(Socket socket, Konfiguracija konfig) {
        this.socket = socket;
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            StringBuffer zahtjev = new StringBuffer();
            int bajt;
            while((bajt = is.read())!=-1){
                zahtjev.append((char) bajt);
            }
            System.out.println("Zahtjev: " + zahtjev);
            
            String odgovor = "OK;";
            os.write(odgovor.getBytes());
            os.flush();
            os.close();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
