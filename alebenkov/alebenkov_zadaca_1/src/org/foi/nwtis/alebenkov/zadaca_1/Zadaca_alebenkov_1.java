/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alen Benkovic
 */
public class Zadaca_alebenkov_1 {

    public static void main(String[] args) {

        Pattern p; //spremam u njega odredjeni regex izraz ovisno o caseu
        Matcher m; //za provjeru izraza
        boolean status; //spremam status provjere izraza

        //kreiram stringbuilder i u njega spremam argumente radi lakse manipulacije
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        String naredba = sb.toString().trim();
        System.out.println(naredba);

        switch (args[0]) {//uzimam prvi argument i provjeravam o kojem se slucaju radi
            case "-server":
                System.out.println("Server trazis...");
                String rServer = "^-server -konf ([^\\s]+\\.(?i)(txt|xml))( +-load)?"; //dozvoljeno: -server -konf -datoteka(.xml | .txt) [-load]
                p = Pattern.compile(rServer); //kompajliram regex izraz za usporedjivanje
                m = p.matcher(naredba); //usporedjujem regex sa dobivenom naredbom
                status = m.matches(); //spremam status usporedbe, true, false
                System.out.println(status);
                break;
            case "-admin":
                System.out.println("Admin trazis...");
                break;
            case "-user":
                System.out.println("User trazis...");
                break;
            case "-show":
                System.out.println("Show trazis...");
                break;
            default:
                System.out.println("Neispravan unos");
                break;
        }

    }
}
