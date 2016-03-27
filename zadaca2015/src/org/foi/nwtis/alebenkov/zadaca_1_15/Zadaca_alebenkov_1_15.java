/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1_15;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Glavna klasa
 * @author Alen Benkovic
 */

public class Zadaca_alebenkov_1_15 {

    /**
    * @param String[], dobiveni parametri
    * ovisno o dobivenim parametrima, pozivaju se odgovarajuce metode
    */
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String p = sb.toString().trim();
        Zadaca_alebenkov_1_15 zadaca = new Zadaca_alebenkov_1_15();
        Matcher m = zadaca.provjeraParametara(p);
        if (m == null) {
            return;
        }

        if (m.group(1) != null) {
            try {
                System.out.println("Trazis server!");
                ServerSustava15 server = new ServerSustava15(p);
                server.pokreniServer();
            } catch (Exception ex) {
                Logger.getLogger(Zadaca_alebenkov_1_15.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (m.group(2) != null) {
            //TODO admin
            System.out.println("Trazis admin!");
        } else if (m.group(3) != null) {
            //TODO user
            KlijentSustava15 klijent = null;
            System.out.println("Trazis user!");
            try {
                klijent = new KlijentSustava15(p);
            } catch (Exception ex) {
                Logger.getLogger(Zadaca_alebenkov_1_15.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                klijent.pokreniKlijenta();
            } catch (Exception ex) {
                Logger.getLogger(Zadaca_alebenkov_1_15.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (m.group(4) != null) {
            //TODO show
            PregledSustava15 ps = null;
            System.out.println("Trazis show!");
            try {
                ps = new PregledSustava15(p);
            } catch (Exception ex) {
                Logger.getLogger(Zadaca_alebenkov_1_15.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps.pokreniPreglednik();
        } else {
            System.out.println("Parametri ne odgovaraju!");
        }
    }
    
    /**
    * Provjerava dobivena parametre
    */
    public Matcher provjeraParametara(String p) {
        
        String sintaksa = "(^-server.+)|(^-admin.+)|(^-user.+)|(^-show.+)";

        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            int poc = 0;
            int kraj = m.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + m.group(i));
            }
            return m;
        } else {
            System.out.println("Ne odgovara!");
            return null;
        }
    }

}
