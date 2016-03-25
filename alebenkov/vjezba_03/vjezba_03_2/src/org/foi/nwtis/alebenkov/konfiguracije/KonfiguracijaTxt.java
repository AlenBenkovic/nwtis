/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.konfiguracije;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author csx
 */
public class KonfiguracijaTxt extends KonfiguracijaApstraktna {

    public KonfiguracijaTxt() {
    }

    public KonfiguracijaTxt(String datoteka) {
        super(datoteka);
    }


    @Override
    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije {
        try {
            this.postavke.load(new FileInputStream(datoteka));
        } catch (IOException ex) {
            throw new NemaKonfiguracije(ex.getMessage());
        }
    }


    @Override
    public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
        try {
            this.postavke.store(new FileOutputStream(datoteka), "NWTiS alebenkov");
        } catch (IOException ex) {
            throw new NeispravnaKonfiguracija(ex.getMessage());
            
        }
    }
    
}
