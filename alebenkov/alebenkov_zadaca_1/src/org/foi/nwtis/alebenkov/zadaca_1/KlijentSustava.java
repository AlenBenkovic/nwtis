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
 *
 * @author abenkovic
 */
public class KlijentSustava {

    private int port;
    private String server;
    private String korisnik;
    private int x;
    private int y;
    //private String rUser = "^-user -s (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) -port ([8-9]\\d{3}) -u ([a-zA-Z0-9_]+) (\\[(\\d+)\\,(\\d+)\\]|-stat)";
    private boolean stat = false; //ukoliko se koristi konstruktor bez koordinata stavljam na true
    private boolean xy = false;
    private final Matcher mParametri;

    public KlijentSustava(String parametri) throws Exception {
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("ERROR | Parametri servera ne odgovoraju!");
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

    public void PokreniKlijentSustava() {
        System.out.println("Pokrecem klijenta...");
        Socket server = null;
        InputStream is = null;
        OutputStream os = null;
        String command;
        StringBuilder response;
        int character;
        System.out.println("STAT: " +stat);
        try {
            System.out.println("Spajam se na server " + this.server + ":" + port);
            server = new Socket(this.server, this.port);
            is = server.getInputStream();
            os = server.getOutputStream();

            if (stat) {
                command = "USER Lala; STAT;";
            } else if(xy) {
                command = "USER Lala; [" + x + "," + y + "];";
            } else {
                command = "USER Lala; PLAY;";
            }

            os.write(command.getBytes());
            os.flush();
            server.shutdownOutput();

            response = new StringBuilder();
            while ((character = is.read()) != -1) {
                response.append((char) character);
            }
            System.out.println("SERVER | " + response);
        } catch (IOException ex) {
            System.out.println("Server is not responding. Exiting now.");
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

    public Matcher provjeraParametara(String p) {
        String regex = "^-user -s ([^\\s]+) -port ([8-9]\\d{3}) -u ([a-zA-Z0-9_]+)( -x ((100)|[1-9]\\d?) -y ((100)|[1-9]\\d?)| -stat)?";
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
