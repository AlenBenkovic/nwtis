/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.sigurnost;

import java.io.File;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_1
 */
public class Vjezba_05_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Broj argumenata nije 1.");
            return;
        }
        File datoteka = new File(args[0]);
        if(! datoteka.exists()) {
            System.out.println("Ne postoji datoteka: " + args[0]);
            return;
        }
        try {
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
            for(Enumeration e = konfig.dajPostavke();e.hasMoreElements();) {
                String k = (String) e.nextElement();
                String v = konfig.dajPostavku(k);
                System.out.println("K: " + k + " V: " + v);
                
            }
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(Vjezba_05_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
