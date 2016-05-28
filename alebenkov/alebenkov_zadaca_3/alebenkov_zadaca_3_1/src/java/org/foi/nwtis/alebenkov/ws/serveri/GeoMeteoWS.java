/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ws.serveri;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.rest.klijenti.GMKlijent;
import org.foi.nwtis.alebenkov.rest.klijenti.OWMKlijent;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.podaci.Lokacija;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author grupa_1
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    private static int brojac = 0;
    private ServletContext sc;
    private Konfiguracija konfig;
    private String APPID;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajSveAdrese")
    public java.util.List<Adresa> dajSveAdrese() {
        List<Adresa> listaAdresa = new ArrayList<>();
        //TODO preuzmi adrese iz baze podataka
        /*L String adrese[] = {"Hrvatska, Varaždin, Pavlinska 2", 
            "Hrvatska, Varaždin, Kralja Petra Krešimira 4",
            "Hrvatska, Zagreb, Trg Bana Jelačića 1", 
            "Hrvatska, Čakovec, Ul. kralja Tomislava 5",
            "Hrvatska, Split, Trg Braće Radić 15", 
            "Hrvatska, Osijek, Ul. Ivana Gundulića 66",
            "Hrvatska, Rijeka, Trg Ivana Koblera 1"};

        GMKlijent gmk = new GMKlijent();
        for (String a : adrese) {
            Lokacija lokacija = gmk.getGeoLocation(a);
            Adresa adresa = new Adresa(brojac++, a, lokacija);
            listaAdresa.add(adresa);
        }*/
        return listaAdresa;
    }

    Adresa dajAdresu(String adresa) {

        GMKlijent gmk = new GMKlijent();
        Lokacija lokacija = gmk.getGeoLocation(adresa);
        Adresa a = new Adresa(brojac++, adresa, lokacija);

        return a;
    }

    /**
     * Web service operation
     * @param adresa
     * @return 
     */
    @WebMethod(operationName = "dajVazeceMeteoPodatkeZaAdresu")
    public MeteoPodaci dajVazeceMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        this.konfig = SlusacAplikacije.getKonfigOstalog();
        this.APPID = konfig.dajPostavku("APPID");
        System.out.println("APPID: " + this.APPID);
        Adresa a = dajAdresu(adresa);
        OWMKlijent owmk = new OWMKlijent(APPID);
        MeteoPodaci mp = owmk.getRealTimeWeather(a.getGeoloc().getLatitude(), a.getGeoloc().getLongitude());
        System.out.println("TEMP: " + mp.getTemperatureValue());
        return mp;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaAdresu")
    public MeteoPodaci dajZadnjeMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaAdresu")
    public java.util.List<MeteoPodaci> dajSveMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        //TODO write your implementation code here:
        return null;
    }
}
