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
        
        if(args[0] == null){//ako ne postoje argumenti prekidam program
            System.out.println("Niste unjeli ni jedan argument! Izlazim.");
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
                System.out.println("Server trazis...");
                String rServer = "^-server -konf ([^\\s]+\\.(?i)(txt|xml))( +-load)?"; //dozvoljeno: -server -konf -datoteka(.xml | .txt) [-load]
                p = Pattern.compile(rServer); //kompajliram regex izraz za usporedjivanje
                m = p.matcher(naredba); //usporedjujem regex sa dobivenom naredbom, grupa 1=datoteka.txt, 2=txt, 3=-load
                status = m.matches(); //spremam status usporedbe, true, false
                if (status) {
                    try {
                        ServerSustava server = new ServerSustava(m.group(1), m.group(3)); //kreiram server i saljem mu paremetre naziv konfig datoteke i da li treba ucitati datoteku sa serijalizirnim podacima
                        server.pokreniServer(); //pokrecem server
                    } catch (Exception ex) {
                        System.out.println("Greska na serveru: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Neispravni argumenti");
                    return;
                }
                break;
            case "-admin":
                System.out.println("Admin trazis...");
                String rAdmin = "^-admin -s (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) -port ([8-9]\\d{3}) -u ([a-zA-Z0-9_]+) -p ([a-zA-Z0-9_]+) (|PAUSE|START|STOP|STAT|NEW) *$"; 
                p = Pattern.compile(rAdmin); 
                m = p.matcher(naredba); 
                status = m.matches();
                if (status) {
                    String server = m.group(1);
                    int port = Integer.parseInt(m.group(2));
                    String korisnik =  m.group(3);
                    String lozinka = m.group(4);
                    String naredbaAdmin = m.group(5);
                    try {
                        AdministratorSustava admin = new AdministratorSustava(port, server, korisnik, lozinka, naredbaAdmin);
                        admin.pokreniAdminSustava();
                        
                    } catch (Exception ex) {
                        System.out.println("Greska na serveru: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Neispravni argumenti");
                    return;
                }
                break;
            case "-user":
                System.out.println("User trazis...");
                String rUser = "^-user -s (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) -port ([8-9]\\d{3})"; 
                p = Pattern.compile(rUser); 
                m = p.matcher(naredba); 
                status = m.matches();
                if (status) {
                    int port = Integer.parseInt(m.group(2));
                    String server = m.group(1);
                    try {
                        KlijentSustava klijent = new KlijentSustava(server, port );
                        klijent.PokreniKlijentSustava(); 
                    } catch (Exception ex) {
                        System.out.println("Greska na serveru: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Neispravni argumenti");
                    return;
                }

                break;
            case "-show":
                System.out.println("Show trazis...");
                break;
            default:
                System.out.println("Neispravan unos argumenata!");
                break;
        }

    }
}
