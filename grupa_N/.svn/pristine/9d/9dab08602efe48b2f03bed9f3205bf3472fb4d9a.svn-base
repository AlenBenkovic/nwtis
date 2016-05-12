/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.zadaca_1;

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
 * @author Matija Novak <matija.novak@foi.hr>
 */
public class KlijentSustava {
    protected String parametri;
    protected String posluzitelj = "127.0.0.1";//TODO preuzmi iz parametara
    protected int port = 9999;//TODO preuzmi iz parametara
    protected String korisnik = "matnovak";//TODO preuzmi iz parametara

    public KlijentSustava(String parametri) {
        this.parametri = parametri;
    }
    
    public void pokreni(){
        if(!provjeriParametre()){
            System.out.println("Problem s parametrima!");
        }
        
        try {
            Socket socket = new Socket(posluzitelj, port);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            String zahtjev = "USER " + korisnik + "; PLAY;";
            os.write(zahtjev.getBytes());
            os.flush();
            socket.shutdownOutput();
            
            StringBuffer odgovor = new StringBuffer();
            int bajt;
            while((bajt = is.read())!=-1){
                odgovor.append((char) bajt);
            }
            System.out.println("Odgovor: " + odgovor);
            os.close();
            is.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(KlijentSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean provjeriParametre(){
        String sintaksa = "^-user -s ([a-zA-Z0-9_.]*) -port (9{4})";
        
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(this.parametri);
        boolean status = m.matches();
        if (status) {
            if(m.group(1) != null){
                System.out.println("Server: " + m.group(1));
            }
            if(m.group(2) != null){
                System.out.println("Port:" + m.group(2));
            }
            return true;
        } else {
            return false;
        }
    }
}
