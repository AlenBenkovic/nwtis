/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.matnovak.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.matnovak.ws.serveri.Adresa;

/**
 *
 * @author grupa_2
 */
@Named(value = "odabirAdresa")
@RequestScoped
public class OdabirAdresa {

    private List<Adresa> adrese;
    private String odabranaAdresa;
    private Map<String,Object> listAdresa = new LinkedHashMap<String, Object>();
    
    /**
     * Creates a new instance of OdabirAdresa
     */
    public OdabirAdresa() {
    }

    public List<Adresa> getAdrese() {
        return adrese;
    }

    public void setAdrese(List<Adresa> adrese) {
        this.adrese = adrese;
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public Map<String,Object> getListAdresa() {
        adrese = MeteoWSKlijent.dajSveAdrese();
        System.out.println("org.foi.nwtis.matnovak.web.zrna.OdabirAdresa.getAdrese()");
        for (Adresa adresa : adrese) {
            listAdresa.put(adresa.getAdresa(), adresa.getAdresa());
        }
        return listAdresa;
    }

    public void setListAdresa(Map<String,Object> listAdresa) {
        this.listAdresa = listAdresa;
    }
    
}
