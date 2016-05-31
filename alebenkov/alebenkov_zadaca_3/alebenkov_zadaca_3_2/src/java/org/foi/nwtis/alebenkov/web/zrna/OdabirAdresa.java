/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.alebenkov.ws.klijenti.MeteoRESTKlijent;
import org.foi.nwtis.alebenkov.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.alebenkov.ws.klijenti.OWMKlijent;
import org.foi.nwtis.alebenkov.ws.serveri.Adresa;
import org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci;

/**
 *
 * @author abenkovic
 */
@Named(value = "odabirAdresa")
@RequestScoped
public class OdabirAdresa implements Serializable {
    private List<Adresa> adrese;
    private List<String> odabranaAdresa;
    private Map<String, Object> listaAdresa = new LinkedHashMap<>();
    private boolean start = true; //boolean mi sluzi za hide/show pojedinih elemenata kod renderiranja stranice
    private boolean multiple = false;
    private boolean single = false;
    private boolean p1 = false;
    private boolean p2 = false;
    private boolean m3 = false;
    List<MeteoPodaci> meteo;
    List<MeteoPodaci> meteoLast;
    List<MeteoPodaci> meteoNext;
    List<Integer> idAdresa;

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

    public List<String> getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(List<String> odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public Map<String, Object> getListaAdresa() {
        adrese = MeteoWSKlijent.dajSveAdrese();
        listaAdresa = new LinkedHashMap<>();
        for (Adresa a : adrese) {
            listaAdresa.put(a.getAdresa(), a.getAdresa());
        }
        return listaAdresa;
    }
    
    private int nadjiIDadrese(int j){
        for (Adresa a : adrese) {
            if(a.getAdresa().equals(odabranaAdresa.get(j))){
                return a.getIdadresa();
            }
        }
        return 0;
    }

    public void setListaAdresa(Map<String, Object> listaAdresa) {
        this.listaAdresa = listaAdresa;
    }

    public void submit() {
        System.out.println(odabranaAdresa);
        if (odabranaAdresa.size() > 1) {
            multiple = true;
            start = false;
        } else if (odabranaAdresa.size() == 1) {
            single = true;
            start = false;
        }
    }

    public void allMeteoData() {
        this.meteo = MeteoWSKlijent.dajSveMeteoPodatkeZaAdresu(odabranaAdresa.get(0));
        p1 = true;

    }

    public void next5MeteoData() {
        
        String APPID = "";
        String lat="";
        String lon="";
        for (int k = 0; k<adrese.size(); k++){
            if(adrese.get(k).getAdresa().equals(odabranaAdresa.get(0))){
                lat = adrese.get(k).getGeoloc().getLatitude();
                lon = adrese.get(k).getGeoloc().getLongitude();
            }
        }
        OWMKlijent owm = new OWMKlijent("251fce21bdb88341273354e83b1f0b87");
        meteoNext = owm.getRealTimeWeather(lat, lon);
        p2 = true;
    }

    public void lastMeteoData() {
        MeteoRESTKlijent mrk = new MeteoRESTKlijent();
        meteoLast = new ArrayList<>();
        for(int i = 0; i<odabranaAdresa.size(); i++){
            int id = nadjiIDadrese(i);
            System.out.println("ID: " + id);
            MeteoPodaci mp = mrk.dajMeteoPodatak(id);
            meteoLast.add(mp);
        }
        m3 = true;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public boolean isSingle() {
        return single;
    }

    

    public boolean isP1() {
        return p1;
    }

    public boolean isP2() {
        return p2;
    }

    public boolean isM3() {
        return m3;
    }

    public List<MeteoPodaci> getMeteo() {
        return meteo;
    }

    public boolean isStart() {
        return start;
    }

    public List<MeteoPodaci> getMeteoLast() {
        return meteoLast;
    }

    public List<MeteoPodaci> getMeteoNext() {
        return meteoNext;
    }
    
    

}
