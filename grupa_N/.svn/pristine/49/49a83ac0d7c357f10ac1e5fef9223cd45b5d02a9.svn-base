/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.konfiguracije;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_1
 */
public class KonfiguracijaTxt extends KonfiguracijaApstraktna {

    public KonfiguracijaTxt(String datoteka) {
        super(datoteka);
    }

    @Override
    public void ucitajKonfiguraciju() throws NemaKonfiguracije {
        if (this.postavke == null) {
            this.postavke = new Properties();
        } else {
            this.postavke.clear();
        }
        if (this.datoteka == null || this.datoteka.length() == 0) {
            throw new NemaKonfiguracije("Naziv datoteke nije ispravan.");
        }
        try {
            this.postavke.load(new FileInputStream(this.datoteka));
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new NemaKonfiguracije("Postoji problem kod čitavanja datoteke.");
        }
    }

    @Override
    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije {
        if (this.postavke == null) {
            this.postavke = new Properties();
        } else {
            this.postavke.clear();
        }
        if (datoteka == null || datoteka.length() == 0) {
            throw new NemaKonfiguracije("Naziv datoteke nije ispravan.");
        }
        try {
            this.postavke.load(new FileInputStream(datoteka));
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new NemaKonfiguracije("Postoji problem kod čitavanja datoteke.");
        }
    }

    @Override
    public void spremiKonfiguraciju() throws NeispravnaKonfiguracija {
        if (this.datoteka == null || this.datoteka.length() == 0) {
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan.");
        }
        try {
            this.postavke.store(new FileOutputStream(this.datoteka), "NWTiS dkermek - " + new Date());
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new NeispravnaKonfiguracija("Postoji problem kod pisanje u datoteku.");
        }
    }

    @Override
    public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
        if (datoteka == null || datoteka.length() == 0) {
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan.");
        }
        try {
            this.postavke.store(new FileOutputStream(datoteka), "NWTiS dkermek - " + new Date());
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new NeispravnaKonfiguracija("Postoji problem kod pisanje u datoteku.");
        }
    }

}
