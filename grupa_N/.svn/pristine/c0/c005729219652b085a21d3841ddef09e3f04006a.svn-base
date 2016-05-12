/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkermek.konfiguracije.NeispravnaKonfiguracija;

/**
 *
 * @author grupa_1
 */
public class Vjezba_03_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Nema samo 1 argument");
            return;
        }
        try {
            Konfiguracija konf = KonfiguracijaApstraktna.kreirajKonfiguraciju(args[0]);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(Vjezba_03_3.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
}
