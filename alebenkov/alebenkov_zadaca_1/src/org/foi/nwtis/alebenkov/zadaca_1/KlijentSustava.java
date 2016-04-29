package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 *
 * @author abenkovic
 */
public class KlijentSustava {

    private int port;
    private String serverIP;
    private String korisnik;
    private int x;
    private int y;
    //private String rUser = "^-user -s (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) -port ([8-9]\\d{3}) -u ([a-zA-Z0-9_]+) (\\[(\\d+)\\,(\\d+)\\]|-stat)";
    private boolean stat = false; //ukoliko se koristi konstruktor bez koordinata stavljam na true

    public KlijentSustava(String server, int port, String korisnik, boolean stat) {
        System.out.println("SA NAREDBOM STAT");
        this.port = port;
        this.serverIP = server;
        this.korisnik = korisnik;
        this.stat = stat; 
    }
    
    public KlijentSustava(Matcher m) {
        
    }

    public void PokreniKlijentSustava() {
        System.out.println("Pokrecem klijenta...");
        Socket server = null;
        InputStream is = null;
        OutputStream os = null;
        String command;
        StringBuilder response;
        int character;
        try {
            System.out.println("Spajam se na server " + server + ":" + port);
            server = new Socket(this.serverIP, this.port);
            is = server.getInputStream();
            os = server.getOutputStream();

            if(stat){
                command = "USER Lala; STAT;";
            } else {
                command = "USER Lala; ["+x+","+y+"];";
            }
            

            os.write(command.getBytes());
            os.flush();
            server.shutdownOutput();

            response = new StringBuilder();
            while ((character = is.read()) != -1) {
                response.append((char) character);
            }
            System.out.println("User got a response: " + response);
        } catch (IOException ex) {
                System.out.println("Server is not responding. Exiting now.");
            }
            finally{
                try {
                    if (is != null)
                        is.close();
                    if (os != null)
                        os.close();
                    if (server != null)
                        server.close();
                } catch (IOException ex) {
                    Logger.getLogger(KlijentSustava.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
}
