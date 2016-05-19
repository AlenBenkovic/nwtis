/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.konfiguracije;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_3
 */
public class KonfiguracijaXML extends KonfiguracijaApstraktna {

    public KonfiguracijaXML(String datoteka) {
        super(datoteka);
    }

    @Override
    public void ucitajKonfiguraciju() throws NemaKonfiguracije {
        this.postavke.clear();
        
        if(this.datoteka == null || this.datoteka.length() == 0){
            throw new NemaKonfiguracije("Naziv datoteke nije ispravan.");
        }
        
        try {
            this.postavke.loadFromXML(new FileInputStream(datoteka));
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaXML.class.getName()).log(Level.SEVERE, null, ex);
            throw new NemaKonfiguracije("Postoji problem kod čitanja datoteke.");
        }
    }

    @Override
    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije {
        this.datoteka = datoteka;
        ucitajKonfiguraciju();
    }

    @Override
    public void spremiKonfiguraciju() throws NeispravnaKonfiguracija {     
        if(this.datoteka == null || this.datoteka.length() == 0){
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan.");
        }
        
        try {
            this.postavke.storeToXML(new FileOutputStream(datoteka),"NWTiS matnovak - " + new Date());
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaXML.class.getName()).log(Level.SEVERE, null, ex);
            throw new NeispravnaKonfiguracija("Postoji problem kod spremanja datoteke.");
        }
    }

    @Override
    public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
        this.datoteka = datoteka;
        spremiKonfiguraciju();
    }
    
}