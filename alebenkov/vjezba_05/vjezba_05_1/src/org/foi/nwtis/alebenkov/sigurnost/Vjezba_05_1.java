/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.sigurnost;

import java.io.File;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author abenkovic
 */
public class Vjezba_05_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Broj argumenata nije 1.");
            return;
        }
        try {
            File datoteka = new File(args[0]);
            if (!datoteka.exists()) {
                System.out.println("Ne postoji datoteka konfiguracije.");
                return;
            }
            
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
            System.out.println("Postavka: port=" + konfig.dajPostavku("port"));
        } catch (NemaKonfiguracije ex) {
            System.out.println("Problem s konfiguracijom. " + ex.getMessage());
            return;
        }

    }

}
