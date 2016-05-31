/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.rest.serveri;

import java.sql.Timestamp;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.alebenkov.web.OperacijeBP;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS;

/**
 * REST Web Service
 *
 * @author grupa_1
 */
public class MeteoRESTResource {

    private String id;

    /**
     * Creates a new instance of MeteoRESTResource
     */
    private MeteoRESTResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the MeteoRESTResource
     */
    public static MeteoRESTResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoRESTResource class.
        return new MeteoRESTResource(id);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.dkermek.rest.serveri.MeteoRESTResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        OperacijeBP obp = new OperacijeBP();
        List<Adresa> adreseLista = obp.ucitajAdrese();
        Adresa adrese[] = new Adresa[adreseLista.size()];
        adrese = adreseLista.toArray(adrese); //prebacujem listu u polje
        int br = Integer.parseInt(id);

        for (int i = 0; i < adrese.length; i++) {
            if (adrese[i].getIdadresa() == br) {
                GeoMeteoWS gmws = new GeoMeteoWS();
                MeteoPodaci mp = gmws.dajZadnjeMeteoPodatkeZaAdresu(adrese[i].getAdresa());
                JsonObjectBuilder jbf = Json.createObjectBuilder();
                jbf.add("adresa", adrese[i].getAdresa());
                jbf.add("temperatura", mp.getTemperatureValue());
                jbf.add("tempMin", mp.getTemperatureMin());
                jbf.add("tempMax", mp.getTemperatureMax());
                jbf.add("vrijeme", mp.getWeatherMain());
                jbf.add("vrijemeOpis", mp.getWeatherValue());
                jbf.add("vlaga", mp.getHumidityValue());
                jbf.add("tlak", mp.getPressureValue());
                jbf.add("vjetar", mp.getWindSpeedValue());
                jbf.add("smjerVjetra", mp.getWindDirectionValue());
                jbf.add("datum", mp.getLastUpdate().getTime());
                return jbf.build().toString();
            }
        }

        return "";
    }

    /**
     * PUT method for updating or creating an instance of MeteoRESTResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content
    ) {
    }

    /**
     * DELETE method for resource MeteoRESTResource
     */
    @DELETE
    public void delete() {
    }
}
