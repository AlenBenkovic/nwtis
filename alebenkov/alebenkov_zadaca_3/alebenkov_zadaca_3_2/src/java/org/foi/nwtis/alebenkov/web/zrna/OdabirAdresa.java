/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static javafx.scene.input.KeyCode.G;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.alebenkov.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.alebenkov.ws.serveri.Adresa;
import org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS;
import org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci;

/**
 *
 * @author abenkovic
 */
@Named(value = "odabirAdresa")
@RequestScoped
public class OdabirAdresa implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8084/alebenkov_zadaca_3_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;
    private List<Adresa> adrese;
    private List<String> odabranaAdresa;
    private Map<String, Object> listaAdresa = new LinkedHashMap<>();
    private boolean start = true;
    private boolean multiple = false;
    private boolean single = false;
    private boolean p1 = false;
    private boolean p2 = false;
    private boolean m3 = false;
    List<MeteoPodaci> meteo;

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
        this.meteo = dajSveMeteoPodatkeZaAdresu(odabranaAdresa.get(0));
        p1 = true;

    }

    public void next5MeteoData() {
        p2 = true;
    }

    public void lastMeteoData() {
        m3 = true;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public boolean isSingle() {
        return single;
    }

    private java.util.List<org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci> dajSveMeteoPodatkeZaAdresu(java.lang.String adresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaAdresu(adresa);
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

}
