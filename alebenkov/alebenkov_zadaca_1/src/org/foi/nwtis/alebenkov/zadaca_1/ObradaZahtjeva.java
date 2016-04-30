package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author csx
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;
    private int stanjeDretve = 0; //0-slobodna, 1-zauzeta
    private int brojacRada = 0; //brojim koliko je puta dretva posluzila klijenta
    Konfiguracija konfig = null;
    private String adminRegex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; (START|STOP|PAUSE|STAT|NEW)\\; *$";
    private String userRegex = "napravi ovo!!";
    private Pattern p;
    private Matcher m;
    private boolean status;

    public ObradaZahtjeva(ThreadGroup group, String name, Konfiguracija konfig) {
        super(group, name);
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        stanjeDretve = 1;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (true) {
            this.pokreni();
        }
    }

    public synchronized void pokreni() {
        stanjeDretve = 1;
        this.brojacRada += 1;
        long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
        int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
        potapanjeBrodova igra = new potapanjeBrodova(brojIgraca);
        System.out.println(this.getName() + " | Pokrecem dretvu koja ce posluziti korisnika.| Brojac rada: " + this.brojacRada + ". | Stanje dretve: " + this.getState());

        //GLAVNA LOGIKA
        InputStream in = null;
        OutputStreamWriter out = null;
        StringBuilder naredba = null;
        int c;

        try {
            in = server.getInputStream();
            out = new OutputStreamWriter(server.getOutputStream());
            naredba = new StringBuilder();

            //uzimam naredbu sa input streama
            while ((c = in.read()) != -1) {
                naredba.append((char) c);
            }

            System.out.println("SERVER | Primljena naredba od korisnika: " + naredba);

            if (naredba.toString().isEmpty()) {//ukoliko kojim slucajem nisam primio naredbu..
                out.write("SERVER | ERROR: Nisam zaprimio nikakvu naredbu.\n");
            } else if (naredba.indexOf("PASSWD") != -1) { //ako jesam provjeravam radi li se o adminu i radim obradu admin naredbi
                //LOGIKA ADMIN
                p = Pattern.compile(adminRegex);
                m = p.matcher(naredba);
                status = m.matches(); //1-korisnik, 2-lozinka, 3-naredba
                if (status) {
                    System.out.println("SERVER | Primio sam adminov zahtjev. Provjeravam njegove podatke...");
                    if (provjeraAdmina(m.group(1), m.group(2))) {//provjeravam adminove podatke i ako je sve ok nastavljam s obradom
                        out.write("SERVER | Pozdrav, " + m.group(1) + "\n");

                        if (naredba.indexOf("PAUSE") != -1) {
                            if (!ServerSustava.provjeraPauziran()) {
                                ServerSustava.pauziraj(true);
                                out.write("SERVER | OK\n");
                            } else {
                                out.write("SERVER | ERROR 01: Server je vec pauziran.\n");
                            }
                        } else if (naredba.indexOf("START") != -1) {
                            if (ServerSustava.provjeraPauziran()) {
                                ServerSustava.pauziraj(false);
                                out.write("SERVER | OK\n");
                            } else {
                                out.write("SERVER | ERROR 02: Server je vec pokrenut.\n");
                            }
                        } else if (naredba.indexOf("NEW") != -1) {
                            igra.kreirajBrodove();
                            out.write("SERVER | OK\n");
                        } else if (naredba.indexOf("STAT") != -1) {
                            //implementiraj ovo!
                            out.write("SERVER | Not implemented yet!\n");
                        }
                    } else {
                        out.write("SERVER | ERROR 00: Neispravno korisnicko ime ili lozinka.\n");
                    }
                } else {
                    out.write("SERVER | ERROR: Neispravni format naredbe.\n");
                }
                //KRAJ LOGIKE ADMINA
            } else if (ServerSustava.provjeraPauziran()) {//ukoliko se ne radi o admin korisniku, provjeravam je li server pauziran
                out.write("SERVER | ERROR: Server je pauziran i ne prima nove naredbe.\n");
            } else if (naredba.indexOf("USER") != -1) {
                //LOGIKA USERA
                if(naredba.indexOf("PLAY") != -1){
                    out.write("PLAY option is not implemented yet! |USER\n");
                } else if (naredba.indexOf("[") != -1){
                    out.write("[] option is not implemented yet! |USER\n");
                } else if (naredba.indexOf("STAT") != -1){
                    out.write("STAT option is not implemented yet! |USER\n");
                } else {
                    out.write("SERVER | ERROR: Neispravni format naredbe.\n");
                }
                //KRAJ LOGIKE USERA
            }

            out.flush();
            server.shutdownOutput();

        } catch (IOException ex) {
            System.out.println("ERROR 01 | IOException: " + ex.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException ex) {
                System.out.println("ERROR 02 | IOException: " + ex.getMessage());
            }
        }

        //KRAJ GLAVNE LOGIKE
        //po zavrsetku svih poslova dretve, saljem ju na spavanje
        long trajanjeRadaDretve = System.currentTimeMillis() - pocetakRadaDretve;
        try {
            System.out.println(this.getName() + " | Saljem dretvu na spavanje");
            sleep(5000 - trajanjeRadaDretve);

            System.out.println(this.getName() + " | Dretva dosla sa spavanja");
        } catch (InterruptedException ex) {
            System.out.println(this.getName() + " | Prekid dretve za vrijeme spavanja");
        }

        stanjeDretve = 0;//oslobadjam dretvu

        while (this.stanjeDretve == 0) {//dokle god je slobodna, dretva ceka dok netko ne pozove 
            try {
                this.wait();
            } catch (InterruptedException ex) {
                System.out.println(this.getName() + " | Dretva nastavlja s radom");
            }
        }

    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSocket(Socket server) {
        this.server = server;
    }

    public int stanjeDretve() {
        return this.stanjeDretve;
    }

    public int brojacRada() {
        return this.brojacRada;
    }

    public boolean provjeraAdmina(String username, String password) {
        if (username.equals(konfig.dajPostavku("adminKorIme")) && password.equals(konfig.dajPostavku("adminKorLozinka"))) {
            return true;
        }
        return false;
    }
}
