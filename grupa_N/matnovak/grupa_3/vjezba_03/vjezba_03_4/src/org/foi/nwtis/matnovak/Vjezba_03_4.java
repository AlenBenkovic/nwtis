/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak;

import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.matnovak.konfiguracije.Konfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.matnovak.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_3
 */
public class Vjezba_03_4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
                Properties p = konfig.dajSvePostavke();
                for (Enumeration e = p.keys(); e.hasMoreElements();) {
                    String k = (String) e.nextElement();
                    String v = p.getProperty(k);
                    System.out.println("K = " + k + ", v = " + v);
                }
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (args.length == 2) {
            try {
                Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
               
                String k = args[1];
                String v = konfig.dajPostavku(k);
                System.out.println("K = " + k + ", v = " + v);
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (args.length == 3) {
            try {
                Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
                String k = args[1];
                String v = args[2];
                
                if(konfig.postojiPostavka(k)){
                    String stara_v = konfig.dajPostavku(k);
                    konfig.azurirajPostavku(k, v);
                    System.out.println("K = " + k + ", v = " + v + ", stara_v = " + stara_v);
                } else {
                    konfig.spremiPostavku(k, v);
                    System.out.println("K = " + k + ", v = " + v);
                }
                
                konfig.spremiKonfiguraciju();
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeispravnaKonfiguracija ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Broj argumenata nije dobar.");
        }
    }

}