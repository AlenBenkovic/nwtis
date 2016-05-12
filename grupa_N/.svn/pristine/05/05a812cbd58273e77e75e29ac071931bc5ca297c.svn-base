/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.zadaca_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author dkermek
 */
public class ServerSustava {

    protected String parametri;
    protected int port = 8000; //todo učitaj iz konfiguracije
    protected Konfiguracija konfig = null;
    protected String datoteka = "NWTiS_dkermek.txt"; //todo preuzmi iz parametara
    protected String datotekaEvidencije = "NWTiS_dkermek_ER.txt"; //todo preuzmi iz parametara
    protected boolean kraj = false;
    protected Evidencija evidencija;
    
    public ServerSustava(String parametri) {
        this.parametri = parametri;
    }

    public void kreni() {
        if (!provjeriParametre()) {
            System.out.println("Problem s parametrima");
        }
        SerijalizatorEvidencije se = new SerijalizatorEvidencije(datotekaEvidencije, this);
        se.start();

        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            ServerSocket ss = new ServerSocket(port);
            
            while(! kraj) {
                Socket vrata = ss.accept();
                // todo obrati pažnju na broj aktivnih dretvi
                ObradaZahtjeva oz = new ObradaZahtjeva(vrata, konfig);
                oz.start();
            }
            
        } catch (IOException | NemaKonfiguracije ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean provjeriParametre() {
        String sintaksa = "^-server -konf ([a-zA-Z0-9_]*)( -load)?";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(this.parametri);
        boolean status = m.matches();
        if (status) {
            if (m.group(1) != null) {
                System.out.println("Datoteka: " + m.group(1));
            }
            if (m.group(2) != null) {
                System.out.println("Učitavanje evidencije");
            }
            return true;
        } else {
            return false;
        }
    }

}
