/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.ejb.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.dkermek.rest.klijenti.GMKlijent;
import org.foi.nwtis.dkermek.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dkermek.web.podaci.Lokacija;
import org.foi.nwtis.dkermek.web.podaci.MeteoPodaci;
import org.foi.nwtis.dkermek.web.podaci.MeteoPrognoza;

/**
 *
 * @author grupa_1
 */
@Stateless
@LocalBean
public class MeteoAdresniKlijent {
    private String apiKey = "adsaasasa"; //TODO preuzeti iz datoteke postavki
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void postaviKorisnickePodatke(String apiKey) {
        this.apiKey = apiKey;
    }

    public MeteoPodaci dajVazeceMeteoPodatke(String adresa) {      
        Lokacija l = dajLokaciju(adresa);
        OWMKlijent owmk = new OWMKlijent(apiKey);
        MeteoPodaci mp = owmk.getRealTimeWeather(l.getLatitude(), l.getLongitude());
        return mp;
    }

    public Lokacija dajLokaciju(String adresa) {
        GMKlijent gmk = new GMKlijent();
        Lokacija l = gmk.getGeoLocation(adresa);        
        return l;
    }

    public MeteoPrognoza[] dajMeteoPrognoze(String adresa) {
        OWMKlijent owmk = new OWMKlijent(apiKey);
        Lokacija l = dajLokaciju(adresa);
        owmk.getWeatherForecast(l.getLatitude(), l.getLongitude());
        return null;
    }
}
