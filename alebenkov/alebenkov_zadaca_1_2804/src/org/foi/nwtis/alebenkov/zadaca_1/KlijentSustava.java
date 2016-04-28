package org.foi.nwtis.alebenkov.zadaca_1;

import java.net.Socket;

/**
 *
 * @author abenkovic
 */
public class KlijentSustava {

    private int port;
    private String server;

    public KlijentSustava(String server, int port) {
        this.port = port;
        this.server = server;

    }

    public void PokreniKlijentSustava() {
        System.out.println("Pokrecem klijenta...");
        try {
            System.out.println("Spajam se na server " + server + ":" + port);
            Socket klijent = new Socket(this.server, this.port);
        } catch (Exception e) {
            System.out.println("GRESKA: " + e.getMessage());
        }
    }
}
