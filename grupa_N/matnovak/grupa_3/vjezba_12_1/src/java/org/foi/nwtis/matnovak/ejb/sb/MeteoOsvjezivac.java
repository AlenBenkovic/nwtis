/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.ejb.sb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.matnovak.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.matnovak.ws.serveri.MeteoPodaci;

/**
 *
 * @author grupa_3
 */
@Singleton
@LocalBean
public class MeteoOsvjezivac {

    private List<MeteoPrognosticar> meteoPrognosticari = new ArrayList<>();
    
    @Resource(mappedName = "jms/NWTiS_vjezba_12")
    private Queue nWTiS_vjezba_12;

    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_vjezba_12")
    private JMSContext context;

    private List<MeteoPodaci> meteoPodaci;
    
//    @EJB
//    private MeteoPrognosticar meteoPrognosticar;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8084/matnovak_zadaca_3_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;

    @Schedule(hour = "*",  minute = "*", second = "*/10")
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
        for(MeteoPrognosticar mpr : meteoPrognosticari){
            meteoPodaci = new ArrayList<>();
            List<String> adrese = mpr.dajAdrese();
            for (String adresa : adrese) {
                MeteoPodaci mp = dajVazeceMeteoPodatkeZaAdresu(adresa);
                meteoPodaci.add(mp);
            }

            mpr.setMeteoPodaci(meteoPodaci);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private MeteoPodaci dajVazeceMeteoPodatkeZaAdresu(java.lang.String adresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.matnovak.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajVazeceMeteoPodatkeZaAdresu(adresa);
    }

    public void sendJMSMessageToNWTiS_vjezba_12(String messageData) {
        context.createProducer().send(nWTiS_vjezba_12, messageData);
    }

    public java.util.List<org.foi.nwtis.matnovak.ws.serveri.Adresa> dajSveAdrese() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.matnovak.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }
    
    public void dodajMeteoPrognosticara(MeteoPrognosticar mpr){
        meteoPrognosticari.add(mpr);
    }
}
