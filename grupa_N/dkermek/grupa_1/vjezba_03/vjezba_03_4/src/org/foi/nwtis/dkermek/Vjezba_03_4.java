/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek;

import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkermek.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_1
 */
public class Vjezba_03_4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
                Properties post = konf.dajSvePostavke();
                for (Enumeration e = post.keys(); e.hasMoreElements();) {
                    String k = (String) e.nextElement();
                    String v = post.getProperty(k);
                    System.out.println("K: " + k + " v: " + v);
                }
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (args.length == 2) {
            try {
                Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
                String k = args[1];
                String v = konf.dajPostavku(k);
                System.out.println("K: " + k + " v: " + v);
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (args.length == 3) {
            try {
                Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
                String k = args[1];
                String v = args[2];
                if(konf.postojiPostavka(k)) {                    
                    String v1 = konf.dajPostavku(k);
                    konf.azurirajPostavku(k, v);
                    System.out.println("Postavka -> K: " + k + " v: " + v + " stara: " + v1);
                } else {
                    konf.spremiPostavku(k, v);
                    System.out.println("Postavka -> K: " + k + " v: " + v); 
                    konf.spremiKonfiguraciju();
                }
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeispravnaKonfiguracija ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Neispravan broj argumenata.");
        }
    }

}
