/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.ejb.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import org.foi.nwtis.matnovak.ws.serveri.Adresa;
import org.foi.nwtis.matnovak.ws.serveri.MeteoPodaci;

/**
 *
 * @author grupa_3
 */
@Stateful
@LocalBean
public class MeteoPrognosticar {
    
    @EJB
    private MeteoOsvjezivac meteoOsvjezivac;
    
    private List<String> adrese;
    private List<MeteoPodaci> meteoPodaci;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public List<String> getAdrese() {
        if(adrese == null){
            adrese = new ArrayList<>();
            List<Adresa> listaAdrese = meteoOsvjezivac.dajSveAdrese();
            for (Adresa adresa : listaAdrese) {
                adrese.add(adresa.getAdresa());
            }
        }
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
       // meteoPodaci = meteoOsvjezivac.getMeteoPodaci();
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public java.util.List<String> dajAdrese() {
        return getAdrese();
    }
}
