/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ejb.sb;

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
import org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci;

/**
 *
 * @author abenkovic
 */
@Singleton
@LocalBean
public class MeteoOsvjezivac {

    @Resource(mappedName = "jms/NWTiS_vjezba_12")
    private Queue nWTiS_vjezba_12;

    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_vjezba_12")
    private JMSContext context;

    @EJB
    private MeteoPrognosticar meteoPrognosticar;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8084/alebenkov_zadaca_3_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;

    @Schedule(minute = "*/2")

    public void myTimer() {
        System.out.println("Timer event: " + new Date());

        List<String> adrese = meteoPrognosticar.dajAdrese();
        List<MeteoPodaci> lmp = new ArrayList<>();
        
        StringBuilder podaci = new StringBuilder();
        
        for(String adresa: adrese){
            System.out.println("Uzimam podatke za adresu: " + adresa);
            MeteoPodaci mp = dajVazeceMeteoPodatkeZaAdresu(adresa);
            lmp.add(mp);
            podaci.append("Grad: ").append(mp.getName());
            podaci.append("Temp: ").append(mp.getTemperatureValue());
        }
        
        meteoPrognosticar.setMeteoPodaci(lmp);
        
        

    }

    private MeteoPodaci dajVazeceMeteoPodatkeZaAdresu(java.lang.String adresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajVazeceMeteoPodatkeZaAdresu(adresa);
    }

    public void sendJMSMessageToNWTiS_vjezba_12(String messageData) {
        context.createProducer().send(nWTiS_vjezba_12, messageData);
    }

}
