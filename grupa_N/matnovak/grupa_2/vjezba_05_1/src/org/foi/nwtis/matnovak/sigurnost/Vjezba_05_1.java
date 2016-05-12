/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.sigurnost;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.matnovak.konfiguracije.Konfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_2
 */
public class Vjezba_05_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Krvi broj argumenata!");
            return;
        }
        Path putanja = Paths.get(args[0]);
       
        if(!Files.exists(putanja, LinkOption.NOFOLLOW_LINKS)){
            System.err.println("Nema datoteke: "+putanja.getFileName().toString());
            return;
        }
        
       
        try {
             System.out.println("Datoteka je: "+putanja.getFileName().toString());
        Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(putanja.getFileName().toString());
            for(Enumeration e = konf.dajPostavke();e.hasMoreElements();)
            {
                String kljuc = (String) e.nextElement();
                System.out.println(kljuc + " => " + konf.dajPostavku(kljuc));
            }
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(Vjezba_05_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
