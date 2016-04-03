package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csx
 */
public class ObradaZahtjeva extends Thread {

    private Socket server;

    public ObradaZahtjeva(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        System.out.println("Pokrecem serversku dretvu " + this.getName() + " stanje " + this.getState());

        try {
            is = server.getInputStream();
            os = server.getOutputStream();
            
            String slanjeNaredbe = "SERVER KAZE OK!";

            os.write(slanjeNaredbe.getBytes());
            os.flush();
            server.shutdownOutput();

            StringBuilder naredba = new StringBuilder();
            while (is.available() > 0) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                naredba.append((char) znak);
            }

            System.out.println("Dobivena naredba: " + naredba.toString());

        } catch (IOException ex) {
            System.out.println("GRESKA");
        }
        finally{
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
                                
            } catch (IOException ex) {
                Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR");
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

}
