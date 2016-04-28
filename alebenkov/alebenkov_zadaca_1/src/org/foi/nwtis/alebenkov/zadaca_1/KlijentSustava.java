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
public class KlijentSustava {

    private int port;
    private String serverIP;

    public KlijentSustava(String server, int port) {
        this.port = port;
        this.serverIP = server;

    }

    public void PokreniKlijentSustava() {
        System.out.println("Pokrecem klijenta...");
        Socket server = null;
        InputStream is = null;
        OutputStream os = null;
        StringBuilder response;
        int character;
        try {
            System.out.println("Spajam se na server " + server + ":" + port);
            server = new Socket(this.serverIP, this.port);
            is = server.getInputStream();
            os = server.getOutputStream();

            String command = "USER Lala; PLAY;";

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
