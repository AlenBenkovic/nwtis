/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.rest.serveri;

import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.alebenkov.web.OperacijeBP;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;

/**
 * REST Web Service
 *
 * @author grupa_1
 */
@Path("/meteoREST")
public class MeteoRESTResourceContainer {

    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MeteoRESTResourceContainer
     */
    public MeteoRESTResourceContainer() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.alebenkov.rest.serveri.MeteoRESTResourceContainer
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
        
        

        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Adresa a : adrese) {
            jab.add(a.getAdresa());
        }
        JsonObjectBuilder jbf = Json.createObjectBuilder();
        jbf.add("adrese", jab);
        return jbf.build().toString();
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
    public Response postJson(String content
    ) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id
    ) {
        return MeteoRESTResource.getInstance(id);
    }
}
