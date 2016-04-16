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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abenkovic
 */
public class AdministratorSustava {

    private int port;
    private String server;
    private String korisnik;
    private String lozinka;
    private String naredba;

    public AdministratorSustava(int port, String server, String korisnik, String lozinka, String naredba) {
        this.port = port;
        this.server = server;
        this.korisnik = korisnik;
        this.lozinka = lozinka;
        this.naredba = naredba;
    }

    public void pokreniAdminSustava() {
        System.out.println("Pokrecem administratora s naredbama: " + server + ":" + port + " Korisnik:" + korisnik + " pass: " + lozinka + " " + naredba);
        Socket admin = null;
        InputStream is = null;
        OutputStream os = null;
        StringBuilder odgovor;
        int character;
        try {
            admin = new Socket(server, port);
            is = admin.getInputStream();
            os = admin.getOutputStream();

            String slanjeNaredbe = "USER " + korisnik + "; PASSWD " + lozinka + "; " + naredba + ";";

            os.write(slanjeNaredbe.getBytes());
            os.flush();
            admin.shutdownOutput();

            odgovor = new StringBuilder();
            while ((character = is.read()) != -1) {
                odgovor.append((char) character);
            }
            
            System.out.println("ADMIN | Odgovor sa servera: " + odgovor);
        } catch (IOException ex) {
            System.out.println("ADMIN | Ne mogu se spojiti sa serverom. Prekidam.");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (admin != null) {
                    admin.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(AdministratorSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
