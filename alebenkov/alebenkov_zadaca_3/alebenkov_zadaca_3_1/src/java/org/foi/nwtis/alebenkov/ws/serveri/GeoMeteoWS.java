/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ws.serveri;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.rest.klijenti.GMKlijent;
import org.foi.nwtis.alebenkov.rest.klijenti.OWMKlijent;
import org.foi.nwtis.alebenkov.web.OperacijeBP;
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
    private Konfiguracija konfig;
    private String APPID;

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "dajSveAdrese")
    public java.util.List<Adresa> dajSveAdrese() {
        OperacijeBP obp = new OperacijeBP();
        List<Adresa> listaAdresa = obp.ucitajAdrese();
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
        Adresa a = dajAdresu(adresa);
        OWMKlijent owmk = new OWMKlijent(APPID);
        MeteoPodaci mp = owmk.getRealTimeWeather(a.getGeoloc().getLatitude(), a.getGeoloc().getLongitude());
        return mp;
    }

    /**
     * Web service operation
     * @param adresa
     * @return meteo podatke
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaAdresu")
    public MeteoPodaci dajZadnjeMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        OperacijeBP obp = new OperacijeBP();
        MeteoPodaci mp = obp.zadnjiMeteoPodaci(adresa);
        return mp;
    }

    /**
     * Web service operation
     * @param adresa
     * @return listu sa meteo podacima
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaAdresu")
    public java.util.List<MeteoPodaci> dajSveMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        OperacijeBP obp = new OperacijeBP();
        List<MeteoPodaci> lmp = obp.sviMeteoPodaci(adresa);
        return lmp;
    }
}
