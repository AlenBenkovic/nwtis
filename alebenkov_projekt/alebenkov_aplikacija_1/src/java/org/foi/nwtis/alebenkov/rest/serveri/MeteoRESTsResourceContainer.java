/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.rest.serveri;

import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.server.DBOps;

/**
 * REST Web Service
 *
 * @author abenkovic
 */
@Path("/meteo")
public class MeteoRESTsResourceContainer {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MeteoRESTsResourceContainer
     */
    public MeteoRESTsResourceContainer() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.alebenkov.rest.serveri.MeteoRESTsResourceContainer
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getJson(@Context HttpServletRequest request, @QueryParam("user") String user, @QueryParam("pass") String pass) {
        //DAJE SVE ADRESE KAD NEMA ID-a
        System.out.println("Username: " + user + " PASS: " + pass + " CPMAT");
        DBOps db = new DBOps();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        JsonObjectBuilder job = Json.createObjectBuilder();
        if (korisnik[0] < 1) {

            db.dnevnik(user, "MeteoREST/?" + request.getQueryString(), "Korisnicki podaci nisu ispravni");
            job.add("Status", "Korisnicki podaci nisu ispravni");
            return job.build().toString();

        } else if (!db.provjeraKvote(user)) {
            db.dnevnik(user, "MeteoREST/?" + request.getQueryString(), "Prekoracen limit");

            job.add("Status", "Prekoracen limit");
            return job.build().toString();

        } else {
            db.dnevnik(user, "MeteoREST/?" + request.getQueryString(), "OK 10");
            DBOps obp = new DBOps();
            List<Adresa> adreseLista = obp.ucitajAdrese();
            Adresa adrese[] = new Adresa[adreseLista.size()];
            adrese = adreseLista.toArray(adrese); //prebacujem listu u polje

            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (Adresa a : adrese) {
                jab.add(a.getAdresa());
            }
            job.add("status", "OK");
            job.add("adrese", jab);
            return job.build().toString();
        }

    }

    /**
     * POST method for creating an instance of MeteoRESTResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id) {
        return MeteoRESTResource.getInstance(id);
    }
}
