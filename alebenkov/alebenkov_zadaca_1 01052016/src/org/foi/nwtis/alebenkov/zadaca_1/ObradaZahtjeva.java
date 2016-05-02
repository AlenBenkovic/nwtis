package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
    private Pattern p;
    private Matcher m;
    private boolean status;
    private PotapanjeBrodova igra;
    InputStream in = null;
    OutputStreamWriter out = null;

    public ObradaZahtjeva(ThreadGroup group, String name, Konfiguracija konfig, PotapanjeBrodova igra) {
        super(group, name);
        this.konfig = konfig;
        this.igra = igra;
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

        System.out.println(this.getName() + " | Pokrecem dretvu koja ce posluziti korisnika.| Brojac rada: " + this.brojacRada + ". | Stanje dretve: " + this.getState());

        //GLAVNA LOGIKA
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
                Matcher mA = provjeraRegex(naredba, 1);
                if (mA == null) {
                    out.write("SERVER | ERROR: Neispravni format naredbe.\n");
                } else {
                    System.out.println("SERVER | Primio sam adminov zahtjev. Provjeravam njegove podatke...");
                    if (adminPrijava(mA.group(1), mA.group(2))) {//provjeravam adminove podatke i ako je sve ok nastavljam s obradom
                        out.write("SERVER | Pozdrav, " + mA.group(1) + "\n");

                        if (mA.group(3).contains("PAUSE")) {
                            adminObradaPause();
                        } else if (mA.group(3).contains("START")) {
                            adminObradaStart();
                        } else if (mA.group(3).contains("NEW")) {
                            adminObradaNew();
                        } else if (mA.group(3).contains("STAT")) {
                            adminObradaStat();
                        } else if (mA.group(3).contains("STOP")) {
                            adminObradaStop();
                        }
                    } else {
                        out.write("SERVER | ERROR 00: Neispravno korisnicko ime ili lozinka.\n");
                    }
                }

                //KRAJ LOGIKE ADMINA
                //LOGIKA USERA
            } else if (ServerSustava.provjeraPauziran()) {//ukoliko se ne radi o admin korisniku, provjeravam je li server pauziran
                out.write("SERVER | ERROR: Server je pauziran i ne prima nove naredbe.\n");
            } else if (naredba.indexOf("USER") != -1) {

                Matcher mU = provjeraRegex(naredba, 2);
                if (mU == null) {
                    out.write("SERVER | ERROR: Neispravni format naredbe.\n");
                } else {
                    System.out.println("SERVER | Primio sam korisnicki zahtjev. Provjeravam njegove parametre...");
                    if (mU.group(2).contains("PLAY")) {
                        userObradaPlay(mU.group(1));
                    } else if (naredba.indexOf("[") != -1) {
                        String imeIgraca = mU.group(1);
                        int xIgraca = Integer.parseInt(mU.group(3));
                        int yIgraca = Integer.parseInt(mU.group(5));

                        userObradaIgraj(imeIgraca, xIgraca, yIgraca);
                    } else if (mU.group(2).contains("STAT")) {
                        userObradaStat();
                    }

                }
            }
            //KRAJ LOGIKE USERA
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

    public boolean adminPrijava(String username, String password) {
        if (username.equals(konfig.dajPostavku("adminKorIme")) && password.equals(konfig.dajPostavku("adminKorLozinka"))) {
            return true;
        }
        return false;
    }

    public Matcher provjeraRegex(StringBuilder p, int i) {
        String regex = null;
        if (i == 1) {
            regex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; (START|STOP|PAUSE|STAT|NEW)\\; *$"; //za admina
        } else if (i == 2) {
            regex = "^USER ([^\\\\s]+); (PLAY|\\[((10)|[1-9]),(10|[1-9])\\]|STAT);"; //za usera
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

    private void adminObradaPause() throws IOException {
        if (!ServerSustava.provjeraPauziran()) {
            ServerSustava.pauziraj(true);
            this.out.write("SERVER | OK\n");
        } else {
            this.out.write("SERVER | ERROR 01: Server je vec pauziran.\n");
        }
    }

    private void adminObradaStart() throws IOException {
        if (ServerSustava.provjeraPauziran()) {
            ServerSustava.pauziraj(false);
            out.write("SERVER | OK\n");
        } else {
            out.write("SERVER | ERROR 02: Server je vec pokrenut.\n");
        }
    }

    private void adminObradaNew() throws IOException {
        if (!ServerSustava.provjeraIgraKreirana()) {
            igra.kreirajBrodove();
            ServerSustava.igraKreirana();
            out.write("SERVER | OK\n");
        } else {
            out.write("ERROR | Igra je vec kreirana.\n");
        }

    }

    private void adminObradaStop() throws IOException {
        out.write("SERVER | STOP Not implemented yet!\n");
    }

    private void adminObradaStat() throws IOException {
        out.write("SERVER | STAT Not implemented yet!\n");
    }

    private void userObradaPlay(String ime) throws IOException {
        if (ServerSustava.provjeraIgraKreirana()) {
            if (igra.provjeraSlobodnihMjesta()) {
                if (igra.igracPrijava(ime)) {
                    out.write("Uspjesno ste prijavljeni!");
                } else {
                    out.write("Igrac sa istim imenom vec postoji!");
                }
            } else {
                out.write("SERVER | ERROR10: Nema slobodnih mjesta za igru.\n");
            }
        } else {
            out.write("ERROR | Igra jos nije kreirana!");
        }

    }

    private void userObradaIgraj(String imeIgraca, int xIgraca, int yIgraca) throws IOException {
        if (ServerSustava.provjeraIgraKreirana()) {
            if (igra.dohvatiIdIgreKorisnika(imeIgraca) != -1) {
                String ime = imeIgraca;
                int x = xIgraca;
                int y = yIgraca;

                int idIgraca = igra.dohvatiIdIgraca(ime);
                if (idIgraca == -1) {
                    out.write("SERVER | Niste prijavljeni za igranje!");
                } else if (igra.brojBrodovaIgraca(idIgraca) == 0) {//ako nema vise brodova 
                    out.write("SERVER | OK 3");
                } else if (igra.neprijateljiUnisteni(idIgraca)) {
                    out.write("SERVER | OK 9");
                } else if ((igra.brojPotezaIgraca(idIgraca) - igra.minBrojPoteza()) == 0) {
                    igra.povecajBrojPoteza(idIgraca);
                    if (igra.pogodiBrod(idIgraca, x - 1, y - 1)) {
                        out.write("SERVER | OK 1");
                        igra.povecajBrojPogodaka(idIgraca);
                        int idPogedjenogProtovnika = igra.vrijednostPolja(x - 1, y - 1); //u samom polju se nalazi ID igraca ciji je broj pogodjen
                        igra.potopiBrod(x - 1, y - 1);
                        System.out.println("ID POGODJENOG: " + idPogedjenogProtovnika);
                        igra.smanjiBrojBrodova(idPogedjenogProtovnika); //odma smanjujem broj brodova pogodjenog igraca
                    } else {
                        out.write("SERVER | OK 0");
                    }
                } else if ((igra.brojPotezaIgraca(idIgraca) - igra.minBrojPoteza()) != 0) {
                    out.write("SERVER | OK 2 (" + igra.brojIgracaCekanje(idIgraca) + ")");
                } else {
                    out.write("SERVER | ERROR 10");
                }

                System.out.println(idIgraca + " " + ime + " X=" + x + ", Y=" + y);

            } else {
                out.write("ERROR | Niste prijavljeni za igranje.");
            }
        } else {
            out.write("ERROR | Igra jos nije pocela!");
        }

    }

    private void userObradaStat() throws IOException {
        out.write("SERVER | STAT for user is not implemented yet!\n");
    }

}
