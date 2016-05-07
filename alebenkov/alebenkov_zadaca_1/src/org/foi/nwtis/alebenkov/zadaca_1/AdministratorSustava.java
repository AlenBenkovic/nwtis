/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Matcher mParametri;

    public AdministratorSustava(String parametri) throws Exception {
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri servera ne odgovoraju!");
        } else {
            this.server = mParametri.group(1);
            this.port = Integer.parseInt(mParametri.group(2));
            this.korisnik = mParametri.group(3);
            this.lozinka = mParametri.group(4);
            this.naredba = mParametri.group(5);
        }
    }

    public void pokreniAdminSustava() {
        //System.out.println("Pokrecem administratora s naredbama: " + server + ":" + port + " Korisnik:" + korisnik + " pass: " + lozinka + " " + naredba);
        Socket admin = null;
        InputStream is = null;
        OutputStreamWriter os = null;
        StringBuilder odgovor;
        int c;
        try {
            admin = new Socket(server, port);
            is = admin.getInputStream();
            os = new OutputStreamWriter(admin.getOutputStream());

            os.write("USER " + korisnik + "; PASSWD " + lozinka + "; " + naredba.toUpperCase() + ";");
            os.flush();
            admin.shutdownOutput();
            odgovor = new StringBuilder();
            while ((c = is.read()) != -1) {
                odgovor.append((char) c);
            }
            System.out.println(odgovor);

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

    public Matcher provjeraParametara(String p) {
        String regex = "^-admin -s ([^\\s]+) -port ([8-9]\\d{3}) -u ([a-zA-Z0-9_]+) -p ([a-zA-Z0-9_]+) -(|pause|start|stop|stat|new) *$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            return null;
        }
    }

}
