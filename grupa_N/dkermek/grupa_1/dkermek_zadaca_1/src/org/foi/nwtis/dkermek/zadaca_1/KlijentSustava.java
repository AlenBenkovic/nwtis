/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.zadaca_1;

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
 * @author dkermek
 */
public class KlijentSustava {
    protected String parametri;
    protected String posluzitelj = "127.0.0.1"; // todo preuzmi pdoatke iz parametara
    protected int brojZaVrata = 8000; // todo preuzmi pdoatke iz parametara
    protected String korisnik = "dkermek"; // todo preuzmi pdoatke iz parametara

    public KlijentSustava(String parametri) {
        this.parametri = parametri;
    }
    
    public void kreni() {
        if(! provjeriParametre()) {
            System.out.println("Problem s parametrima");
            return;
        }
        try {
            Socket vrata = new Socket(posluzitelj, brojZaVrata);
            OutputStream os = vrata.getOutputStream();
            InputStream is = vrata.getInputStream();
            
            String zahtjev = "USER " + korisnik + "; PLAY;";
            os.write(zahtjev.getBytes());
            os.flush();
            vrata.shutdownOutput();
            
            StringBuffer odgovor = new StringBuffer();
            int bajt;
            
            while((bajt = is.read()) != -1) {
                odgovor.append((char) bajt);                        
            }
            is.close();
            System.out.println(odgovor);
            vrata.close();
        } catch (IOException ex) {
            Logger.getLogger(KlijentSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean provjeriParametre() {
        String sintaksa = "^-user -s ([a-zA-Z0-9_.]*) -port (9{4})";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(this.parametri);
        boolean status = m.matches();
        if (status) {
            if(m.group(1) != null) {
                System.out.println("Server: " + m.group(1));
            }
            if(m.group(2) != null) {
                System.out.println("Port: " + m.group(2));
            }
            return true;
        } else {
            return true; // todo vrati na false i podesi RegEx
        }                        
    }
    
}
