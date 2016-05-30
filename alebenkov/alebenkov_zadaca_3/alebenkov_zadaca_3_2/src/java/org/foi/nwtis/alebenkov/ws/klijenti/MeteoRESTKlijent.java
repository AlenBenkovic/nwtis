/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ws.klijenti;

import java.io.StringReader;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci;

/**
 *
 * @author abenkovic
 */
public class MeteoRESTKlijent {

    public MeteoPodaci dajMeteoPodatak(int id) {
        MeteoRESTResourceClient mrc = new MeteoRESTResourceClient(id);
        String odgovor = mrc.getJson();
        try {
            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jo = reader.readObject();

            MeteoPodaci mp = new MeteoPodaci();
            mp.setTemperatureValue(new Double(jo.getJsonNumber("temperatura").doubleValue()).floatValue());
            mp.setTemperatureMin(new Double(jo.getJsonNumber("tempMin").doubleValue()).floatValue());
            mp.setTemperatureMax(new Double(jo.getJsonNumber("tempMax").doubleValue()).floatValue());

            mp.setWeatherMain(jo.getString("vrijeme"));
            mp.setWeatherValue(jo.getString("vrijemeOpis"));
            
            mp.setName(jo.getString("adresa"));

            mp.setHumidityValue(new Double(jo.getJsonNumber("vlaga").doubleValue()).floatValue());
            mp.setPressureValue(new Double(jo.getJsonNumber("tlak").doubleValue()).floatValue());

            mp.setWindSpeedValue(new Double(jo.getJsonNumber("vjetar").doubleValue()).floatValue());
            mp.setWindDirectionValue(new Double(jo.getJsonNumber("smjerVjetra").doubleValue()).floatValue());

           //eh ti datumi...
            Long ts = jo.getJsonNumber("datum").longValue();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(ts);
            XMLGregorianCalendar xc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            mp.setLastUpdate(xc);
            return mp;

        } catch (Exception ex) {
            System.out.println("GRESKA: " + ex.getMessage());

        }
        return null;
    }

    static class MeteoRESTResourceClient {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8084/alebenkov_zadaca_3_1/webresources";

        public MeteoRESTResourceClient(int id) {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("meteoREST/" + id);
        }

        public Response postJson(Object requestEntity) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public String getJson() throws ClientErrorException {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
        }

        public void close() {
            client.close();
        }
    }

}
