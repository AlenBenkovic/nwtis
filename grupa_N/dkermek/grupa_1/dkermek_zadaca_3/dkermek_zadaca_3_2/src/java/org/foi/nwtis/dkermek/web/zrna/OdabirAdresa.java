/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.web.zrna;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dkermek.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.dkermek.ws.serveri.Adresa;

/**
 *
 * @author grupa_1
 */
@Named(value = "odabirAdresa")
@RequestScoped
public class OdabirAdresa {

    private List<Adresa> adrese;
    private String odabranaAdresa;
    private Map<String, Object> listaAdresa = new LinkedHashMap<>();

    /**
     * Creates a new instance of OdabirAdresa
     */
    public OdabirAdresa() {
    }

    public List<Adresa> getAdrese() {
        adrese = MeteoWSKlijent.dajSveAdrese();
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

    public Map<String, Object> getListaAdresa() {
        adrese = MeteoWSKlijent.dajSveAdrese();
        listaAdresa = new LinkedHashMap<>();
        for(Adresa a : adrese) {
            listaAdresa.put(a.getAdresa(), a.getAdresa());
        }
        return listaAdresa;
    }

    public void setListaAdresa(Map<String, Object> listaAdresa) {
        this.listaAdresa = listaAdresa;
    }

}
