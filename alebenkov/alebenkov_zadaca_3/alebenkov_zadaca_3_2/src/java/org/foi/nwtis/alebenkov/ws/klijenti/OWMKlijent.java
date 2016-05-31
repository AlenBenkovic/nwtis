/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ws.klijenti;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci;

/**
 *
 * @author nwtis_1
 */
public class OWMKlijent {

    String apiKey;
    OWMRESTHelper helper;
    Client client;

    public OWMKlijent(String apiKey) {
        this.apiKey = apiKey;
        helper = new OWMRESTHelper(apiKey);
        client = ClientBuilder.newClient();
    }

    public List<MeteoPodaci> getRealTimeWeather(String latitude, String longitude) {
        WebTarget webResource = client.target(OWMRESTHelper.getOWM_BASE_URI())
                .path(OWMRESTHelper.getOWM_Forecast_Path());
        webResource = webResource.queryParam("lat", latitude);
        webResource = webResource.queryParam("lon", longitude);
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", apiKey);

        String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        try {
            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jo = reader.readObject();

            List<MeteoPodaci> mpLista = new ArrayList<>();

            for (int i = 0; i < jo.getJsonArray("list").size(); i++) {
                MeteoPodaci mp = new MeteoPodaci();
                mp.setTemperatureValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("temp").doubleValue()).floatValue());
                mp.setTemperatureMin(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("temp_min").doubleValue()).floatValue());
                mp.setTemperatureMax(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("temp_max").doubleValue()).floatValue());
                mp.setPressureValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("pressure").doubleValue()).floatValue());
                mp.setHumidityValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("humidity").doubleValue()).floatValue());
                mp.setWindSpeedValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("wind").getJsonNumber("speed").doubleValue()).floatValue());
                mp.setWindDirectionValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("wind").getJsonNumber("deg").doubleValue()).floatValue());
                mp.setWeatherValue(jo.getJsonArray("list").getJsonObject(i).getJsonArray("weather").getJsonObject(0).getString("description"));
                mp.setWeatherMain(jo.getJsonArray("list").getJsonObject(i).getJsonArray("weather").getJsonObject(0).getString("main"));
                mp.setName(jo.getJsonArray("list").getJsonObject(i).getString("dt_txt"));//mala prilagodna kako bi uzeo string i spremio ga u meteo podatke

                mpLista.add(mp);
            }

            /*mp.setTemperatureValue(new Double(jo.getJsonObject("main").getJsonNumber("temp").doubleValue()).floatValue());
            mp.setTemperatureMin(new Double(jo.getJsonObject("main").getJsonNumber("temp_min").doubleValue()).floatValue());
            mp.setTemperatureMax(new Double(jo.getJsonObject("main").getJsonNumber("temp_max").doubleValue()).floatValue());
            mp.setTemperatureUnit("celsius");
            
            mp.setHumidityValue(new Double(jo.getJsonObject("main").getJsonNumber("humidity").doubleValue()).floatValue());
            mp.setHumidityUnit("%");
            
            mp.setPressureValue(new Double(jo.getJsonObject("main").getJsonNumber("pressure").doubleValue()).floatValue());
            mp.setPressureUnit("hPa");
            
            mp.setWindSpeedValue(new Double(jo.getJsonObject("wind").getJsonNumber("speed").doubleValue()).floatValue());
            mp.setWindSpeedName("");
            
            if (jo.getJsonObject("wind").getJsonNumber("deg") != null) { // za neke gradove ne postoji vrijednost pa mi javlja gresku
                mp.setWindDirectionValue(new Double(jo.getJsonObject("wind").getJsonNumber("deg").doubleValue()).floatValue());
                mp.setWindDirectionCode("");
                mp.setWindDirectionName("");
                
            } else {
                float  a = 0;//ako ne stavim ništa onda javlja problem kod upisa u bazu zbog null vrijednosti
                mp.setWindDirectionValue(a);
                mp.setWindDirectionCode("");
                mp.setWindDirectionName("");
            }
            
            mp.setCloudsValue(jo.getJsonObject("clouds").getInt("all"));
            mp.setCloudsName(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
            mp.setPrecipitationMode("");
            
            mp.setWeatherNumber(jo.getJsonArray("weather").getJsonObject(0).getInt("id"));
            mp.setWeatherValue(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
            mp.setWeatherIcon(jo.getJsonArray("weather").getJsonObject(0).getString("icon"));
            mp.setWeatherMain(jo.getJsonArray("weather").getJsonObject(0).getString("main"));

            //mp.setCountry(new String(jo.getJsonObject("sys").getJsonString("country").getString()));
            mp.setCountry(jo.getJsonObject("sys").getString("country"));
            mp.setName(jo.getString("name"));
             */
            return mpLista;

        } catch (Exception ex) {
            System.out.println("GRESKA: " + ex.getMessage());
            Logger.getLogger(OWMKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
