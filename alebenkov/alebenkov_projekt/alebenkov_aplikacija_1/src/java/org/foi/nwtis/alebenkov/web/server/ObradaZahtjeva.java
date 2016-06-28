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
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;
    private int stanjeDretve = 0; //0-slobodna, 1-zauzeta
    private int brojacRada = 0; //brojim koliko je puta dretva posluzila klijenta
    private Konfiguracija konfig = null;
    private InputStream in = null;
    private OutputStreamWriter out = null;
    private boolean radi = true;
    private PozadinskoPreuzimanje pozadinskaDretva = null;

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
        pozadinskaDretva = SlusacAplikacije.getPozadinskaDretva();
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
                    int[] vrstaKorisnika = dbOp.provjeraKorisnika(user, pass);
                    System.out.println("SERVER | [Vrsta korisnika: " + vrstaKorisnika[0] + "]  [Rang korisnika: " + vrstaKorisnika[1] + "]");
                    switch (vrstaKorisnika[0]) {
                        case 0:
                            out.write("ERR 20");
                            break;
                        case 1:
                            //OBRADA ADMIN ZAHTJEVA
                            System.out.println("SERVER | Obrada admin zahtjeva.");

                            if (rx.group(3).trim().isEmpty()) {//ako su uneseni samo podaci za prijavu
                                dbOp.dnevnik(user, naredba.toString(), "OK 10");
                                out.write("OK 10.");
                            } else {
                                Matcher ax = provjeraRegex(naredba, 1);

                                if (ax == null) {
                                    dbOp.dnevnik(user, naredba.toString(), "ERR 21");
                                    out.write("ERR 21.\n");
                                } else if (ax.group(3).contains("PAUSE")) {
                                    if (!pozadinskaDretva.isDretvaPreuzima()) {
                                        dbOp.dnevnik(user, naredba.toString(), "ERR 30");
                                        out.write("ERR 30.");
                                    } else {
                                        pozadinskaDretva.setDretvaPreuzima(false);
                                        dbOp.dnevnik(user, naredba.toString(), "OK 10");
                                        out.write("OK 10.");
                                    }

                                } else if (ax.group(3).contains("START")) {
                                    if (pozadinskaDretva.isDretvaPreuzima()) {
                                        dbOp.dnevnik(user, naredba.toString(), "ERR 31");
                                        out.write("ERR 31.");
                                    } else {
                                        pozadinskaDretva.setDretvaPreuzima(true);
                                        dbOp.dnevnik(user, naredba.toString(), "OK 10");
                                        out.write("OK 10.");
                                    }

                                } else if (ax.group(3).contains("STOP")) {
                                    if (!pozadinskaDretva.isDretvaRadi()) {
                                        dbOp.dnevnik(user, naredba.toString(), "ERR 32");
                                        out.write("ERR 32.");
                                    } else {
                                        pozadinskaDretva.setDretvaRadi(false);
                                        dbOp.dnevnik(user, naredba.toString(), "OK 10");
                                        out.write("OK 10.");
                                    }

                                } else if (ax.group(3).contains("STATUS")) {
                                    if (pozadinskaDretva.isDretvaRadi() && pozadinskaDretva.isDretvaPreuzima()) {
                                        dbOp.dnevnik(user, naredba.toString(), "OK 01");
                                        out.write("OK 01.");
                                    } else if (pozadinskaDretva.isDretvaRadi() && !pozadinskaDretva.isDretvaPreuzima()) {
                                        dbOp.dnevnik(user, naredba.toString(), "OK 00");
                                        out.write("OK 00.");
                                    } else {
                                        dbOp.dnevnik(user, naredba.toString(), "OK 02");
                                        out.write("OK 02.");
                                    }
                                } else if (ax.group(3).contains("ADD")) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
                                    long vrijemeZahtjeva = System.currentTimeMillis();
                                    String newUser = ax.group(4);
                                    String newPass = ax.group(5);
                                    String newRole = ax.group(6);
                                    if (dbOp.dodajKorisnika(newUser, newPass, newRole)) {
                                        dbOp.dnevnik(user, naredba.toString(), "OK 10");
                                        out.write("OK 10.");
                                        int[] statUser = dbOp.statistikaKorisnika();
                                        SlanjePoruke poruka = new SlanjePoruke();
                                        String tekstPoruke = "Dobivena naredba: " + naredba
                                                + "\nVrijeme primanja zahtjeva: " + sdf.format(vrijemeZahtjeva)
                                                + "\nUkupan broj korisnika: " + statUser[0]
                                                + "\nBroj administratora: " + statUser[1]
                                                + "\nBroj obicnih korisnika: " + statUser[2];
                                        poruka.setPredmetPoruke(konfig.dajPostavku("mailNaslov"));
                                        poruka.setTekstPoruke(tekstPoruke);
                                        poruka.setTipPoruke("text/plain");
                                        poruka.setPosluzitelj(konfig.dajPostavku("mailPosluzitelj"));
                                        poruka.setTkoPrima(konfig.dajPostavku("mailPrima"));
                                        poruka.setTkoSalje(konfig.dajPostavku("mailSalje"));
                                        poruka.saljiPoruku();
                                    } else {
                                        dbOp.dnevnik(user, naredba.toString(), "ERR 33");
                                        out.write("ERR 33.");
                                    }
                                    System.out.println("ADD " + newUser + newPass + newRole);
                                    //TODO pozovi metodu
                                } else if (ax.group(7).contains("UP")) {
                                    //korisnik u  ax.group(8)
                                    String odg = dbOp.povecajRang(ax.group(8));
                                    dbOp.dnevnik(user, naredba.toString(), odg);
                                    out.write(odg);
                                } else if (ax.group(7).contains("DOWN")) {
                                    String odg = dbOp.smanjiRang(ax.group(8));
                                    dbOp.dnevnik(user, naredba.toString(), odg);
                                    out.write(odg);
                                }

                            }

                            //KRAJ OBRADE ADMIN ZAHTJEVA
                            break;
                        case 2:
                            //OBRADA USER ZAHTJEVA
                            if (!pozadinskaDretva.isDretvaRadi()) { //ako pozadinska ne radi, znaci da se nove naredbe ne primaju
                                out.write("Server ne prima nove naredbe.");
                            } else if (!dbOp.provjeraKvote(user)) {
                                out.write("ERR 40");
                            } else {
                                System.out.println("SERVER | Obrada korisnickog zahtjeva.");
                                if (rx.group(3).trim().isEmpty()) {//ako su uneseni samo podaci za prijavu
                                    dbOp.dnevnik(user, naredba.toString(), "OK 10");
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
