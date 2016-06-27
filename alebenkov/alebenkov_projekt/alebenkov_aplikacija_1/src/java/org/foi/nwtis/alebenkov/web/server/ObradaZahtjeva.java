/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author abenkovic
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;
    private int stanjeDretve = 0; //0-slobodna, 1-zauzeta
    private int brojacRada = 0; //brojim koliko je puta dretva posluzila klijenta
    Konfiguracija konfig = null;
    InputStream in = null;
    OutputStreamWriter out = null;
    private boolean radi = true;

    public ObradaZahtjeva(ThreadGroup group, String name, Konfiguracija konfig) {
        super(group, name);
        this.konfig = konfig;

    }

    @Override
    public void interrupt() {
        stanjeDretve = 1; //kod ponovnog pozivanja dretve, oznacavam ju kao zauzetu
        super.interrupt();
    }

    @Override
    public void run() {
        while (radi) {
            this.pokreni();
        }
        System.out.println(this.getName() + " GASIM.");
    }

    public synchronized void pokreni() {
        stanjeDretve = 1;
        this.brojacRada += 1;
        long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
        System.out.println(this.getName() + " | Brojac rada: " + this.brojacRada + ". | Stanje dretve: " + this.getState());
        //POCETAK GLAVNE LOGIKE
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

            if (naredba.toString().isEmpty()) {//ukoliko kojim slucajem nisam primio naredbu..
                out.write("SERVER | ERROR: Nisam zaprimio nikakvu naredbu.\n");
            } else {
                //OBRADA DOBIVENE NAREDBE
                System.out.println("SERVER | Primljena naredba od korisnika: " + naredba);
                Matcher rx = provjeraRegex(naredba, 0);
                if (rx == null) {
                    out.write("SERVER | ERROR: Neispravni format naredbe.\n");
                } else {
                    out.write("Provjeravam podatke za prijavu...\n");
                    String user = rx.group(1);
                    String pass = rx.group(2);
                    //TODO RADI PROVJERU IZ BAZE, neka vraca 1-admin, 2-user
                    DBOps dbOp = new DBOps();
                    int vrstaKorisnika = dbOp.provjeraKorisnika(user, pass);
                    out.write("Dobrodosao, " + user + ".\n");
                    switch (vrstaKorisnika) {
                        case 0:
                            out.write("ERR 20");
                            break;
                        case 1:
                            //OBRADA ADMIN ZAHTJEVA
                            System.out.println("SERVER | Obrada admin zahtjeva.");

                            if (rx.group(3).trim().isEmpty()) {//ako su uneseni samo podaci za prijavu
                                out.write("OK 10.");
                            } else {
                                Matcher ax = provjeraRegex(naredba, 1);

                                if (ax == null) {
                                    out.write("ERR 21.\n");
                                } else if (ax.group(3).contains("PAUSE")) {
                                    System.out.println("PAUSE");
                                    //TODO pozovi metodu
                                } else if (ax.group(3).contains("START")) {
                                    System.out.println("START");
                                    //TODO pozovi metodu
                                } else if (ax.group(3).contains("STOP")) {
                                    System.out.println("STOP");
                                    //TODO pozovi metodu
                                } else if (ax.group(3).contains("STATUS")) {
                                    System.out.println("STATUS");
                                    //TODO pozovi metodu
                                } else if (ax.group(3).contains("ADD")) {
                                    String newUser = ax.group(4);
                                    String newPass = ax.group(5);
                                    String newRole = ax.group(6);
                                    System.out.println("ADD " + newUser + newPass + newRole);
                                    //TODO pozovi metodu
                                } else if (ax.group(7).contains("UP")) {
                                    //korisnik u  ax.group(8)
                                    System.out.println("UP " + ax.group(8));
                                    //TODO pozovi metodu
                                } else if (ax.group(7).contains("DOWN")) {
                                    System.out.println("DOWN " + ax.group(8));
                                    //TODO pozovi metodu
                                }

                            }

                            //KRAJ OBRADE ADMIN ZAHTJEVA
                            break;
                        case 2:
                            //OBRADA USER ZAHTJEVA
                            System.out.println("SERVER | Obrada korisnickog zahtjeva.");

                            if (rx.group(3).trim().isEmpty()) {//ako su uneseni samo podaci za prijavu
                                out.write("OK 10.");
                            } else {
                                Matcher ux = provjeraRegex(naredba, 2);
                                //grupa 5-test, 6-get, 7-add
                                if (ux == null) {
                                    out.write("ERR 21.\n");
                                } else if (ux.group(4) != null) {
                                    System.out.println("TEST: " + ux.group(4));
                                    //TODO pozovi metodu
                                } else if (ux.group(5) != null) {
                                    System.out.println("GET: " + ux.group(5));
                                    //TODO pozovi metodu
                                } else if (ux.group(6) != null) {
                                    System.out.println("ADD: " + ux.group(6));
                                    //TODO pozovi metodu
                                }
                            }
                            //KRAJ OBRADE USER ZAHTJEVA
                            break;
                        default:
                            break;
                    }
                }
                //KRAJ OBRADE DOBIVENE NAREDBE
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
                System.out.println("ERROR 02 | IOException: " + ex.getMessage() + ex.toString());
            }
        }

        //KRAJ GLAVNE LOGIKE
        long trajanjeRadaDretve = System.currentTimeMillis() - pocetakRadaDretve;//kraj rada dretve
        try {
            System.out.println(this.getName() + " | Saljem dretvu na spavanje.");
            sleep(5000 - trajanjeRadaDretve);

            System.out.println(this.getName() + " | Dretva dosla sa spavanja.");
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

    public void setSocket(Socket server) {
        this.server = server;
    }

    public int stanjeDretve() {
        return this.stanjeDretve;
    }

    public int brojacRada() {
        return this.brojacRada;
    }

    public void setRadi(boolean radi) {
        this.radi = radi;
    }

    public Matcher provjeraRegex(StringBuilder p, int i) {
        String regex = null;
        if (i == 0) {
            regex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\;((?:.*)) *$";
        } else if (i == 1) {
            regex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; (PAUSE|START|STOP|STATUS|ADD ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; ROLE (ADMIN|USER)|(UP|DOWN) ([a-zA-Z0-9_]+))\\; *$";
        } else if (i == 2) {
            regex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; (TEST \"((?:.*))\"|GET \"((?:.*))\"|ADD \"((?:.*))\")\\; *$"; //za usera
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            System.out.println("ERROR | Parametri ne odgovaraju!");
            return null;
        }
    }

}
