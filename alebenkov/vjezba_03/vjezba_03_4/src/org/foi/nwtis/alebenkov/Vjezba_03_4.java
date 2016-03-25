/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author csx
 */
public class Vjezba_03_4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.out.println("Broj argumenata ne odgovara!");
            return;
        }

        try {
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
            if (args.length == 1) {
                for (Enumeration e = konfig.dajPostavke(); e.hasMoreElements();) {
                    String k = (String) e.nextElement();
                    String v = konfig.dajPostavku(k);
                    System.out.println(k + " => " + v);
                }

            } else if (args.length == 2) {
                String k = args[1];
                String v = konfig.dajPostavku(k);
                System.out.println(k + " => " + v);

            } else if (args.length == 3) {
                String k = args[1];
                String v = args[2];
                if(konfig.postojiPostavka(k)){
                    konfig.azurirajPostavku(k, v);
                } else {
                    konfig.spremiPostavku(k, v);
                }
                
                konfig.spremiKonfiguraciju();

            }
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            System.out.println(ex.getMessage());
        }

    }

}
