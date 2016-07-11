/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.rest.klijenti;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.alebenkov.web.podaci.Lokacija;

/**
 *
 * @author nwtis_1
 */
public class GMKlijent {

    GMRESTHelper helper;
    Client client;

    public GMKlijent() {
        client = ClientBuilder.newClient();
    }

    public Lokacija getGeoLocation(String adresa) {
        try {
            WebTarget webResource = client.target(GMRESTHelper.getGM_BASE_URI())
                    .path("maps/api/geocode/json");
            webResource = webResource.queryParam("address",
                    URLEncoder.encode(adresa, "UTF-8"));
            webResource = webResource.queryParam("sensor", "false");

            String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jo = reader.readObject();

            Lokacija loc = new Lokacija();
            if (jo.getJsonArray("results").size() != 0) {
                loc.setLatitude(jo.getJsonArray("results").getJsonObject(0).getJsonObject("geometry").getJsonObject("location").getJsonNumber("lat").toString());
                loc.setLongitude(jo.getJsonArray("results").getJsonObject(0).getJsonObject("geometry").getJsonObject("location").getJsonNumber("lng").toString());
            } else {
                System.out.println("Ne postoji geolokacija za trazenu adresu");
            }

            return loc;

        } catch (UnsupportedEncodingException ex) {
            System.out.println("Greska " + ex.getMessage());
        }
        return null;
    }
}
