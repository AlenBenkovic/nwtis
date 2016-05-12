/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.ws.klijenti;

/**
 *
 * @author grupa_3
 */
public class MeteoWSKlijent {

    public static java.util.List<org.foi.nwtis.matnovak.ws.serveri.Adresa> dajSveAdrese() {
        org.foi.nwtis.matnovak.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.matnovak.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.matnovak.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }
    
}
