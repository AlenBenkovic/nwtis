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
import org.foi.nwtis.dkermek.ws.serveri.Adresa;
import org.foi.nwtis.dkermek.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.dkermek.ws.serveri.MeteoPodaci;

/**
 *
 * @author grupa_1
 */
@Singleton
@LocalBean
public class MeteoOsvjezivac {

    private List<MeteoPrognosticar> meteoprognosticari = new ArrayList<>();
    
    private List<MeteoPodaci> meteopodaci;
    
    @Resource(mappedName = "jms/NWTiS_vjezba_12")
    private Queue nWTiS_vjezba_12;

    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_vjezba_12")
    private JMSContext context;
    
    @EJB
    private MeteoPrognosticar meteoPrognosticar;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8084/dkermek_zadaca_3_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;

    @Schedule(hour = "*",  minute = "*", second = "*/10")
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
        //if(meteopodaci == null){
            meteopodaci = new ArrayList<MeteoPodaci>();
        //}
        for(MeteoPrognosticar metProg : meteoprognosticari){
            List<String> adrese = metProg.getAdrese();

            for (String adresa : adrese) {
                MeteoPodaci mp = dajVazeceMeteoPodatkeZaAdresu(adresa);
                meteopodaci.add(mp);
            }

            metProg.setMeteoPodaci(meteopodaci);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private MeteoPodaci dajVazeceMeteoPodatkeZaAdresu(java.lang.String adresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.dkermek.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajVazeceMeteoPodatkeZaAdresu(adresa);
    }

    public void sendJMSMessageToNWTiS_vjezba_12(String messageData) {
        context.createProducer().send(nWTiS_vjezba_12, messageData);
    }

    public java.util.List<org.foi.nwtis.dkermek.ws.serveri.Adresa> dajSveAdrese() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.dkermek.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }

//    public List<MeteoPodaci> getMeteopodaci() {
//        return meteopodaci;
//    }
//
//    public void setMeteopodaci(List<MeteoPodaci> meteopodaci) {
//        this.meteopodaci = meteopodaci;
//    }

    public List<MeteoPrognosticar> getMeteoprognosticari() {
        return meteoprognosticari;
    }

    public void setMeteoprognosticari(List<MeteoPrognosticar> meteoprognosticari) {
        this.meteoprognosticari = meteoprognosticari;
    }
    
    
}
