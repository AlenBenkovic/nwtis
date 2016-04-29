package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        System.out.println("SOCKET:" + server.toString());
        if(server.isClosed()){
            System.out.println("SOCKET JE ZATVOREN!!!");
            return;
        }
        if(server.isInputShutdown()){
            System.out.println("INPUT JE ZATVOREN!!!");
            return;
        }
        if(server.isOutputShutdown()){
            System.out.println("OUT JE ZATVOREN!!!");
            return;
        }
        int brojIgraca = Integer.parseInt(konfig.dajPostavku("brojIgraca"));
        potapanjeBrodova igra = new potapanjeBrodova(brojIgraca);
        stanjeDretve = 1;
        this.brojacRada += 1;
        long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve

        StringBuilder primljenoOdKorisnika;
        InputStream is = null;
        OutputStreamWriter os = null;
        int c;
        try {
            is = server.getInputStream();
            os = new OutputStreamWriter(server.getOutputStream());
            primljenoOdKorisnika = new StringBuilder();
            /*while ((c = is.read()) != -1) {
                primljenoOdKorisnika.append((char) c);
                System.out.println("VRTIIIIM" + primljenoOdKorisnika.toString());
            }*/
            while (is.available() > 0) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                primljenoOdKorisnika.append((char) znak);
                System.out.println("VRTIIIIM" + primljenoOdKorisnika.toString());

            }
            System.out.println("SERVER | Primljena naredba od korisnika: " + primljenoOdKorisnika);
            if (primljenoOdKorisnika.toString().isEmpty()) {
                os.write("SERVER | ERROR: Nisam zaprimio nikakvu naredbu.\n");
            } else if (primljenoOdKorisnika.indexOf("PASSWD") != -1) { //OBRADA ADMIN ZAHTJEVA
                p = Pattern.compile(adminRegex);
                m = p.matcher(primljenoOdKorisnika);
                status = m.matches(); //1-korisnik, 2-lozinka, 3-naredba
                if (status) {
                    System.out.println("SERVER | Primio sam adminov zahtjev. Provjeravam njegove podatke...");
                    if (provjeraAdmina(m.group(1), m.group(2))) {
                        os.write("SERVER | Pozdrav, " + m.group(1) + "\n");
                        os.flush();
                        if (primljenoOdKorisnika.indexOf("PAUSE") != -1) {
                            if (!ServerSustava.provjeraPauziran()) {
                                ServerSustava.pauziraj(true);
                                os.write("SERVER | OK\n");
                            } else {
                                os.write("SERVER | ERROR 01: Server je vec pauziran.\n");
                            }

                        } else if (primljenoOdKorisnika.indexOf("START") != -1) {
                            if (ServerSustava.provjeraPauziran()) {
                                ServerSustava.pauziraj(false);
                                os.write("SERVER | OK\n");
                            } else {
                                os.write("SERVER | ERROR 02: Server je vec pokrenut.\n");
                            }

                        } else if (primljenoOdKorisnika.indexOf("NEW") != -1) {

                            igra.kreirajBrodove();
                            os.write("SERVER | OK\n");
                        }
                    } else {
                        os.write("SERVER | ERROR 00: Neispravno korisniko ime ili lozinka.\n");

                    }
                } else {
                    os.write("SERVER | ERROR: Neispravni format naredbe.\n");
                }

            } else if (primljenoOdKorisnika.indexOf("USER") != -1) {
                p = Pattern.compile(userRegex);
                m = p.matcher(primljenoOdKorisnika);
                status = m.matches(); //1-korisnik, 2-lozinka, 3-naredba
                if (status) {
                    if (primljenoOdKorisnika.indexOf("PLAY") != -1) {
                        if (igra.provjeraSlobodnihMjesta()) {
                            potapanjeBrodova.igrac igrac = igra.new igrac("Ivana");//SREDI OVO
                        } else {
                            os.write("SERVER | ERROR10: Nema slobodnih mjesta za igru.\n");
                        }
                    } else if (primljenoOdKorisnika.indexOf("[") != -1) {
                        igra.pogodiBrod(2, 0, 0); //SREDI OVO

                    } else if (primljenoOdKorisnika.indexOf("STAT") != -1) {

                    }
                }
            } else if (ServerSustava.provjeraPauziran()) {
                os.write("SERVER | ERROR: Server je pauziran i ne prima nove naredbe.\n");
            }
            os.flush();
            os.close();
            server.shutdownOutput();
        } catch (IOException ex) {
            System.out.println(this.getName() + " | GRESKA kod IO operacija!" + ex.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (server != null) {
                try {
                    server.close();
                } catch (IOException ex) {
                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println(this.getName() + " | Pokrecem dretvu koja ce posluziti korisnika.| Brojac rada: " + this.brojacRada + ". | Stanje dretve: " + this.getState());

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