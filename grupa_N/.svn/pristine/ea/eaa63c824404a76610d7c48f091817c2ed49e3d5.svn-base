/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.ejb.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.matnovak.rest.klijenti.GMKlijent;
import org.foi.nwtis.matnovak.rest.klijenti.OWMKlijent;
import org.foi.nwtis.matnovak.web.podaci.Adresa;
import org.foi.nwtis.matnovak.web.podaci.Lokacija;
import org.foi.nwtis.matnovak.web.podaci.MeteoPodaci;
import org.foi.nwtis.matnovak.web.podaci.MeteoPrognoza;

/**
 *
 * @author grupa_3
 */
@Stateless
@LocalBean
public class MeteoAdresniKlijent {
    private String apiKey = "";
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void postaviKorisnickePodatke(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public MeteoPodaci dajVazeceMeteoPodatke(String adresa){
        Lokacija l = dajLokaciju(adresa);
        String APPID = this.apiKey;
        OWMKlijent owmk = new OWMKlijent(APPID);
        MeteoPodaci mp = owmk.getRealTimeWeather(l.getLatitude(), l.getLongitude());
        return mp;
    }
    
    public Lokacija dajLokaciju(String adresa) {
        GMKlijent gmk = new GMKlijent();
        Lokacija lokacija = gmk.getGeoLocation(adresa);
        return lokacija;
    }
    
    public MeteoPrognoza[] dajMeteoPrognoze(String adresa){
        OWMKlijent owmk = new OWMKlijent(apiKey);
        Lokacija l = dajLokaciju(adresa);
        owmk.getWeatherForecast(l.getLatitude(), l.getLongitude());
        return null;
    }
}
