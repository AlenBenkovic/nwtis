/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.ws.serveri;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.matnovak.rest.klijenti.GMKlijent;
import org.foi.nwtis.matnovak.rest.klijenti.OWMKlijent;
import org.foi.nwtis.matnovak.web.podaci.Adresa;
import org.foi.nwtis.matnovak.web.podaci.Lokacija;
import org.foi.nwtis.matnovak.web.podaci.MeteoPodaci;

/**
 *
 * @author grupa_2
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {
    private static int brojac = 0;
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "dajSveAdrese")
    public java.util.List<Adresa> dajSveAdrese() {
        System.out.println("zahtjev zaprimljen dajSveAdrese");
        List<Adresa> listaAdresa = new ArrayList<>();
        String adrese[] = {"Hrvatska, Varaždin, Pavlinska 2"
                          ,"Hrvatska, Varaždin, Kralja Petra Krešimira 4"
                          ,"Hrvatska, Zagreb, Ilica 1"
                          ,"Hrvatska, Čakovec, Ul. kralja tomislava 5"
                          ,"Hrvatska, Split, Trg Braće Radića 15"
                          ,"Hrvatska, Osijek, Ul. Ivana Gundulića 66"
                          ,"Hrvatska, Rijeka, Trg Ivana Koblera 1"};
        
        for (String adresa : adrese) {
            listaAdresa.add(dajAdresu(adresa));
        }
        
        return listaAdresa;
    }
    
    private Adresa dajAdresu(String adresa){
        GMKlijent gmk = new GMKlijent();
        Lokacija l = gmk.getGeoLocation(adresa);
        Adresa a = new Adresa(brojac++, adresa, l);
        
        return a;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajVazeceMeteoPodatkeZaAdresu")
    public MeteoPodaci dajVazeceMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        Adresa a = dajAdresu(adresa);
        String APPID = "xxx"; //TODO pruzmi iz konfiguracijske datoteke
        OWMKlijent owmk = new OWMKlijent(APPID);
        MeteoPodaci mp = owmk.getRealTimeWeather(a.getGeoloc().getLatitude(), a.getGeoloc().getLongitude());
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
