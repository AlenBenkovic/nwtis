package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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

        InputStream is = null;
        OutputStream os = null;
        StringBuilder primljenoOdKorisnika = new StringBuilder();
        String slanjeKorisniku = null;

        try {
            is = server.getInputStream();
            os = server.getOutputStream();

            while (is.available() > 0) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                primljenoOdKorisnika.append((char) znak);

            }
            System.out.println("SERVER | Primljena naredba od korisnika: " + primljenoOdKorisnika);
            if (primljenoOdKorisnika.indexOf("PASSWD") != -1) { //ako sadrži password znaic da se radi o adminu
                p = Pattern.compile(adminRegex);
                m = p.matcher(primljenoOdKorisnika);
                status = m.matches(); //1-korisnik, 2-lozinka, 3-naredba
                if (status) {
                    System.out.println("SERVER | Primio sam adminov zahtjev. Provjeravam njegove podatke...");
                     slanjeKorisniku = "Pozdrav, " + m.group(1);
                    if (provjeraAdmina(m.group(1), m.group(2))) {
                        slanjeKorisniku = "SERVER | Vasi podaci su OK!";
                    } else {
                        slanjeKorisniku = "SERVER | Neispravni korisnicki podaci za prijavu!";
                    }
                } else {
                    slanjeKorisniku = "ERROR: Neispravna naredba.";
                }
                os.write(slanjeKorisniku.getBytes());
                os.flush();
                server.shutdownOutput();
            } else if (primljenoOdKorisnika.indexOf("-user") != -1) {
                System.out.println("SERVER | Primam podatke od korisnika.");
            }

        } catch (IOException ex) {
            System.out.println(this.getName() + " | GRESKA kod IO operacija!");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }

            } catch (IOException ex) {
                System.out.println(this.getName() + " | GRESKA kod IO operacija 2");
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
