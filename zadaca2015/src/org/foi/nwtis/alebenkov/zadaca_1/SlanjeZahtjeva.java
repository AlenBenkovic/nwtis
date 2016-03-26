/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author alen
 */
public class SlanjeZahtjeva extends Thread {

    private Konfiguracija konfig;
    private String server;
    private int port;
    private int ukupniBrojCiklusa;
    private int cekaj;
    private int razmakDretvi;

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        long pocetak = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS dd.MM.yyyy");

        try {
            Thread.sleep(this.razmakDretvi);
            System.out.println("Pokrecem korisnicku dretvu: " + this.getName() + " stanje " + this.getState());

            System.out.println("Sada je " + sdf.format(new Date()));
        } catch (InterruptedException ex) {
            Logger.getLogger(SlanjeZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }

        InputStream is = null;
        OutputStream os = null;
        Socket socket = null;

        //TODO preuzmi iz parametara
        String korisnik = "alebenkov";
        //TODO preuztmi iz parametara
        int brojPokusaja = 5;
        //TODO preuztmi iz parametara
        int vrijemeSpavanja = cekaj;
        int brojNuspjelihPokusaja;

        int brojCiklusa = 0; //sluzi za brojanje ciklusa korisnicke dretve
        boolean spavanje = false;

        while (true) {
            brojNuspjelihPokusaja = 0;
            for (int i = 0; i < ukupniBrojCiklusa; i++) {
                spavanje = false;
                brojCiklusa = brojCiklusa + 1;
                try {
                    socket = new Socket(server, port);
                    os = socket.getOutputStream();
                    is = socket.getInputStream();

                    String komanda = "USER " + korisnik + "; TIME;" + "broj ciklusa: " + brojCiklusa;
                    if (cekaj > 0) {
                        try {
                            Thread.sleep(this.cekaj * 1000);
                            System.out.println("Dretva na cekanju prije slanja naredbe " + sdf.format(new Date()));
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SlanjeZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    os.write(komanda.getBytes());
                    System.out.println("Poslao naredbu  " + sdf.format(new Date()));
                    os.flush();
                    socket.shutdownOutput();

                    StringBuilder sb = new StringBuilder();
                    int j = 100;
                    while (true) {
                        int znak = is.read();
                        if (znak == -1) {
                            break;
                        }
                        sb.append((char) znak);
                        //TODO ovo je privremeno
                        if (sb.length() == j) {
                            break;
                        }
                    }
                    System.out.println(sb.toString());

                } catch (IOException ex) {
                    Logger.getLogger(SlanjeZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                    brojNuspjelihPokusaja++;
                    spavanje = true;
                }

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

                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (brojNuspjelihPokusaja == brojPokusaja) {
                    break;
                }

                if (spavanje) {
                    try {
                        sleep(vrijemeSpavanja);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SlanjeZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //TODO ovo je privremeno 
            break;
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setKonfig(Konfiguracija konfig) {
        this.konfig = konfig;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setBrojCiklusa(int brojCiklusa) {
        this.ukupniBrojCiklusa = brojCiklusa;
    }

    public void setCekaj(int cekaj) {
        this.cekaj = cekaj;
    }

    public void setRazmakDretvi(int razmakDretvi) {
        this.razmakDretvi = razmakDretvi;
    }

}
