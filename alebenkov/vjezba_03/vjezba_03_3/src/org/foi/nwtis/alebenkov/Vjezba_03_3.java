/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov;

import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NeispravnaKonfiguracija;

/**
 *
 * @author csx
 */
public class Vjezba_03_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length !=1){
            System.out.println("Ne odgovara broj argumenata");
            return;
        }
        
        try {
            Konfiguracija konfig = KonfiguracijaApstraktna.kreirajKonfiguraciju(args[0]);
        } catch (NeispravnaKonfiguracija ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
