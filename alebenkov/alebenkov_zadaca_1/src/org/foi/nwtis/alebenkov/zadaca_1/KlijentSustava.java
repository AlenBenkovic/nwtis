package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa klijenta
 * @author Alen Benkovic
 */
public class KlijentSustava {

    private int port;
    private String server;
    private String korisnik;
    private int x;
    private int y;
    private boolean stat = false;
    private boolean xy = false;
    private final Matcher mParametri;

    /**
     * Konstruktor klase klijenta
     * @param parametri
     * @throws Exception
     */
    public KlijentSustava(String parametri) throws Exception {
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri servera ne odgovoraju!");
        } else {
            this.server = mParametri.group(1);
            this.port = Integer.parseInt(mParametri.group(2));
            this.korisnik = mParametri.group(3);
            if (mParametri.group(5) != null) {
                this.x = Integer.parseInt(mParametri.group(5));
                this.y = Integer.parseInt(mParametri.group(7));
                this.xy = true;
            } else if(mParametri.group(4) != null) {
                this.stat = true;
            }
        }

    }

    /**
     *
     */
    public void PokreniKlijentSustava() {
        //System.out.println("Pokrecem klijenta...");
        Socket server = null;
        InputStream is = null;
        OutputStream os = null;
        String command;
        StringBuilder odgovor;
        int znak;
        try {
            //System.out.println("Spajam se na server " + this.server + ":" + port);
            server = new Socket(this.server, this.port);
            is = server.getInputStream();
            os = server.getOutputStream();

            if (stat) {
                command = "USER "+korisnik+"; STAT;";
            } else if(xy) {
                command = "USER "+korisnik+"; [" + x + "," + y + "];";
            } else {
                command = "USER "+korisnik+"; PLAY;";
            }

            os.write(command.getBytes());
            os.flush();
            server.shutdownOutput();

            odgovor = new StringBuilder();
            while ((znak = is.read()) != -1) {
                odgovor.append((char) znak);
            }
            System.out.println(odgovor);
        } catch (IOException ex) {
            System.out.println("Ne mogu se spojiti na server."+ ex.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(KlijentSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Provjera dobivenih parametara
     * @param p
     * @return matcher ili null ako parametri ne odgovaraju
     */
    public Matcher provjeraParametara(String p) {
        String regex = "^-user -s ([^\\s]+) -port ([8-9]\\d{3}) -u ([a-zA-Z0-9_]+)( -x ((10)|[1-9]\\d?) -y ((10)|[1-9]\\d?)| -stat)?";
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
