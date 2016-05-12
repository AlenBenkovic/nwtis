/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dkermek
 */
public class Zadaca_dkermek_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String sintaksa = "(^-server.+)|(^-admin.+)|(^-user.+)|(^-show.+)";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String p = sb.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            //todo izbaci nakon testiranja
            int poc = 0;
            int kraj = m.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + m.group(i));
            }
            // kraj
            if (m.group(1) != null) {
                ServerSustava serverSustava = new ServerSustava(p);
                serverSustava.kreni();
            } else if (m.group(3) != null) {
                KlijentSustava klijentSustava = new KlijentSustava(p);
                klijentSustava.kreni();
            }
        } else {
            System.out.println("Ne odgovara!");
        }
    }

}
