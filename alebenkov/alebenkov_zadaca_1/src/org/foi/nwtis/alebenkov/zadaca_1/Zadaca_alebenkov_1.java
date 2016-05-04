package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Glavna klasa projekta
 *
 * @author Alen Benkovic
 *
 */
public class Zadaca_alebenkov_1 {

    /**
     * Konstruktor glavne klase. Prima naredbu i ovisno o parametrima kreira
     * objekte i poziva metode
     *
     * @param args prima parametre iz konzole
     */
    public static void main(String[] args) {

        Pattern p; //spremam u njega odredjeni regex izraz ovisno o caseu
        Matcher m; //za provjeru izraza
        boolean status; //spremam status provjere izraza

        if (args.length == 0) {//ako ne postoje argumenti prekidam program
            System.out.println("ERROR | Niste unjeli ni jedan argument! Izlazim.\n");
            return;
        }

        //kreiram stringbuilder i u njega spremam argumente radi lakse manipulacije
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        String naredba = sb.toString().trim();
        System.out.println(naredba);

        switch (args[0]) {//uzimam prvi argument i provjeravam o kojem se slucaju radi
            case "-server":
                try {
                    ServerSustava server = new ServerSustava(naredba); //kreiram server i saljem mu paremetre naziv konfig datoteke i da li treba ucitati datoteku sa serijalizirnim podacima
                    server.pokreniServer(); //pokrecem server
                } catch (Exception ex) {
                    System.out.println("ERROR | Greska na serveru: " + ex.getMessage()+"\n");
                }

                break;
            case "-admin":
                try {
                    AdministratorSustava admin = new AdministratorSustava(naredba);
                    admin.pokreniAdminSustava();

                } catch (Exception ex) {
                    System.out.println("ERROR | Greska na serveru: " + ex.getMessage()+"\n");
                }

                break;
            case "-user":
                try {
                    KlijentSustava klijent = new KlijentSustava(naredba);
                    klijent.PokreniKlijentSustava();

                } catch (Exception ex) {
                    System.out.println("ERROR | Greska na serveru: " + ex.getMessage()+"\n");
                }

                break;
            case "-show":
                System.out.println("Show is not implemented yet!");
                break;
            default:
                System.out.println("ERROR | Neispravan unos argumenata!");
                break;
        }

    }
}
