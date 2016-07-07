/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.komunikacija;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author alebenkov
 */
public class SocketServer {

    private String naredba;
    private String odgovor;
    private final String server;
    private final int port;

    public SocketServer() {
        Konfiguracija config = SlusacAplikacije.getServerConfig();
        server = config.dajPostavku("adresaServera");
        port = Integer.parseInt(config.dajPostavku("portServera"));
    }

    public String posalji(String naredba) {
        this.naredba = naredba;
        System.out.println("NAREDBA: " + this.naredba);
        Socket server = null;
        InputStream is = null;
        OutputStream os = null;
        StringBuilder odgovor;
        int znak;
        try {
            server = new Socket(this.server, this.port);
            is = server.getInputStream();
            os = server.getOutputStream();
            os.write(naredba.getBytes());
            os.flush();
            server.shutdownOutput();

            odgovor = new StringBuilder();
            while ((znak = is.read()) != -1) {
                odgovor.append((char) znak);
            }
            this.odgovor = odgovor.toString();

        } catch (IOException e) {
            this.odgovor = e.getMessage();
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
                this.odgovor = ex.getMessage();
            }
        }
        return this.odgovor;

    }

}
