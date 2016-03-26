/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.konfiguracije;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csx
 */
public class KonfiguracijaXML extends KonfiguracijaApstraktna{

    public KonfiguracijaXML() {
    }

    public KonfiguracijaXML(String datoteka) {
        super(datoteka);
    }

    

    @Override
    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije {
        try {
            this.postavke.loadFromXML(new FileInputStream(datoteka));
        } catch (IOException ex) {
            throw new NemaKonfiguracije(ex.getMessage());
        }
    }


    @Override
    public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
        try {
            this.postavke.storeToXML(new FileOutputStream(datoteka), "NWTiS alebenkov");
        } catch (IOException ex) {
            throw new NeispravnaKonfiguracija(ex.getMessage());
        }
    }
    
}
