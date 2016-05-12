/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.matnovak.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.matnovak.ws.serveri.Adresa;

/**
 *
 * @author grupa_3
 */
@Named(value = "odabirAdresa")
@RequestScoped
public class OdabirAdresa {
    private List<String> adrese;
    private String odabranaAdresa;
    
    /**
     * Creates a new instance of OdabirAdresa
     */
    public OdabirAdresa() {
    }

    public List<String> getAdrese() {
        List<Adresa> adreseWS = MeteoWSKlijent.dajSveAdrese();
        adrese = new ArrayList<String>();
        
        for (Adresa adresa : adreseWS) {
            adrese.add(adresa.getAdresa());
        }
        
        System.out.println("Pozivan servis MeteoWSKlijent.dajSveAdrese()");
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }    
}
