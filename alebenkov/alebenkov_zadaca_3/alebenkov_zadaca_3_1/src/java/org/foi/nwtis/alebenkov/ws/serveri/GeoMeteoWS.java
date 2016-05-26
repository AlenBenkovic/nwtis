/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ws.serveri;

import javax.jws.WebService;
import javax.jws.WebMethod;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;

/**
 *
 * @author abenkovic
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajSveAdrese")
    public java.util.List<Adresa> dajSveAdrese() {
        //TODO write your implementation code here:
        return null;
    }
}
