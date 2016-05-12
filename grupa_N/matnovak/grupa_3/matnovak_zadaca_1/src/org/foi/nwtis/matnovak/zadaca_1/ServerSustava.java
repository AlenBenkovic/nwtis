/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.zadaca_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.matnovak.konfiguracije.Konfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author Matija Novak <matija.novak@foi.hr>
 */
public class ServerSustava {
    protected String parametri;
    protected int port = 9999;//TODO učitaj iz konfiguracije
    protected Konfiguracija konfig = null;
    protected String datoteka = "NWTiS_matnovak.txt";//TODO preuzmi iz parametara
    protected boolean kraj = false;
    protected String datotekaEvidencije = "NWTiS_matnovak_ER.txt";//TODO preuzmi iz parametara
    protected Evidencija evidencija = new Evidencija();
    
    public ServerSustava(String parametri) {
        this.parametri = parametri;
    }
    
    public void pokreni(){
        if(!provjeriParametre()){
            System.out.println("Problem s parametrima!");
        }
        
        evidencija.brodovi.add(new Brod());
        SerijalizatorEvidencije se = new SerijalizatorEvidencije(datotekaEvidencije, this);
        se.start();
        
        try {
            konfig =KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            //TODO učitaj sve što je potrebno
            
            ServerSocket serverSocket = new ServerSocket(port);
            while(!kraj){
                Socket socket = serverSocket.accept();
                //TODO obrati pažnju na broj aktivnih dretvi
                ObradaZahtjeva obradaZahtjeva = new ObradaZahtjeva(socket,konfig);
                obradaZahtjeva.start();
            }
            
        } catch (IOException | NemaKonfiguracije ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean provjeriParametre(){
        String sintaksa = "^-server -konf ([a-zA-Z0-9_.]*)( -load)?";
        
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(this.parametri);
        boolean status = m.matches();
        if (status) {
            if(m.group(1) != null){
                System.out.println("Datoteka: " + m.group(1));
            }
            if(m.group(2) != null){
                System.out.println("Učitavanje evidencije");
            }
            return true;
        } else {
            return false;
        }
    }
}
