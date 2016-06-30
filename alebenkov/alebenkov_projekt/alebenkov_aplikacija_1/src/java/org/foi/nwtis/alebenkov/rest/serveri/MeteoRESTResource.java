/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.rest.serveri;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.server.DBOps;

/**
 * REST Web Service
 *
 * @author abenkovic
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
     * org.foi.nwtis.alebenkov.rest.serveri.MeteoRESTResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getJson(@Context HttpServletRequest request, @QueryParam("user") String user, @QueryParam("pass") String pass, @QueryParam("date") String datum) {
        System.out.println("Username: " + user + " PASS: " + pass + " DATE: " + datum + " ID: " + id + " REQ: " + request.getQueryString());
        DBOps db = new DBOps();
        JsonObjectBuilder job = Json.createObjectBuilder();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        if (korisnik[0] < 1) {

            db.dnevnik(user, "MeteoREST/" + id + "?" + request.getQueryString(), "Korisnicki podaci nisu ispravni");
            job.add("Status", "Korisnicki podaci nisu ispravni");
            return job.build().toString();

        } else if (!db.provjeraKvote(user)) {
            db.dnevnik(user, "MeteoREST/" + id + "?" + request.getQueryString(), "Prekoracen limit");
            job.add("Status", "Prekoracen limit");
            return job.build().toString();

        } else if (datum == null) {
            List<MeteoPodaci> mpl = db.zadnjaMeteoPrognoza(this.id);
            job.add("Status", "OK");

            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (MeteoPodaci m : mpl) {
                JsonObjectBuilder jobp = Json.createObjectBuilder();
                jobp.add("dana", m.getName());
                jobp.add("temperatura", m.getTemperatureValue().intValue());
                jobp.add("tempMin", m.getTemperatureMin().intValue());
                jobp.add("tempMax", m.getTemperatureMax().intValue());
                jobp.add("vrijeme", m.getWeatherMain());
                jobp.add("vrijemeOpis", m.getWeatherValue());
                jobp.add("vlaga", m.getHumidityValue().intValue());
                jobp.add("tlak", m.getPressureValue().intValue());
                jobp.add("vjetar", m.getWindSpeedValue().intValue());
                jobp.add("smjerVjetra", m.getWindDirectionValue().intValue());
                jobp.add("stanica", m.getCountry());
                jab.add(jobp);
            }
            job.add("Prognoza", jab);
            db.dnevnik(user, "MeteoREST/" + id + "?" + request.getQueryString(), "OK 10");
            return job.build().toString();
        } else {
            List<MeteoPodaci> mpl = db.prognozaZaDan(id, datum);
            job.add("Status", "OK");

            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (MeteoPodaci m : mpl) {
                JsonObjectBuilder jobp = Json.createObjectBuilder();
                jobp.add("dana", m.getName());
                jobp.add("temperatura", m.getTemperatureValue().intValue());
                jobp.add("tempMin", m.getTemperatureMin().intValue());
                jobp.add("tempMax", m.getTemperatureMax().intValue());
                jobp.add("vrijeme", m.getWeatherMain());
                jobp.add("vrijemeOpis", m.getWeatherValue());
                jobp.add("vlaga", m.getHumidityValue().intValue());
                jobp.add("tlak", m.getPressureValue().intValue());
                jobp.add("vjetar", m.getWindSpeedValue().intValue());
                jobp.add("smjerVjetra", m.getWindDirectionValue().intValue());
                jobp.add("preuzeto", m.getLastUpdate().toString());
                jobp.add("stanica", m.getCountry());
                jab.add(jobp);
            }
            job.add("Prognoza", jab);
            db.dnevnik(user, "MeteoREST/" + id + "?" + request.getQueryString(), "OK 10");
            return job.build().toString();

        }
    }

    /**
     * PUT method for updating or creating an instance of MeteoRESTResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource MeteoRESTResource
     */
    @DELETE
    public void delete() {
    }
}
