/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.rest.klijenti;

import java.io.StringReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPrognoza;

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

    public MeteoPodaci getRealTimeWeather(String latitude, String longitude) {
        WebTarget webResource = client.target(OWMRESTHelper.getOWM_BASE_URI())
                .path(OWMRESTHelper.getOWM_Current_Path());
        webResource = webResource.queryParam("lat", latitude);
        webResource = webResource.queryParam("lon", longitude);
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", apiKey);

        String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        try {
            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jo = reader.readObject();

            MeteoPodaci mp = new MeteoPodaci();
            float a = 0;
//            if (jo.getJsonObject("sys").getJsonNumber("sunrise") != null) {
//                mp.setSunRise(new Date(jo.getJsonObject("sys").getJsonNumber("sunrise").bigDecimalValue().longValue() * 1000));
//            } else {
//                //
//            }
//            if (jo.getJsonObject("sys").getJsonNumber("sunset") != null) {
//                mp.setSunSet(new Date(jo.getJsonObject("sys").getJsonNumber("sunset").bigDecimalValue().longValue() * 1000));
//            } else {
//                //
//            }

            mp.setTemperatureValue(new Double(jo.getJsonObject("main").getJsonNumber("temp").doubleValue()).floatValue());
            mp.setTemperatureMin(new Double(jo.getJsonObject("main").getJsonNumber("temp_min").doubleValue()).floatValue());
            mp.setTemperatureMax(new Double(jo.getJsonObject("main").getJsonNumber("temp_max").doubleValue()).floatValue());
            mp.setTemperatureUnit("celsius");

            mp.setHumidityValue(new Double(jo.getJsonObject("main").getJsonNumber("humidity").doubleValue()).floatValue());
            mp.setHumidityUnit("%");

            mp.setPressureValue(new Double(jo.getJsonObject("main").getJsonNumber("pressure").doubleValue()).floatValue());
            mp.setPressureUnit("hPa");

            if (jo.isNull("wind")) { // za neke gradove ne postoji vrijednost pa mi javlja gresku
                //ako ne stavim ništa onda javlja problem kod upisa u bazu zbog null vrijednosti
                mp.setWindDirectionValue(a);
                mp.setWindSpeedValue(a);
                mp.setWindSpeedName("");
                mp.setWindDirectionCode("");
                mp.setWindDirectionName("");

            } else {
                if (jo.getJsonObject("wind").getJsonNumber("deg") == null) {
                    mp.setWindDirectionValue(a);

                } else {
                    mp.setWindDirectionValue(new Double(jo.getJsonObject("wind").getJsonNumber("deg").doubleValue()).floatValue());
                }
                if (jo.getJsonObject("wind").getJsonNumber("speed") == null) {
                    mp.setWindSpeedValue(a);
                } else {
                    mp.setWindSpeedValue(new Double(jo.getJsonObject("wind").getJsonNumber("speed").doubleValue()).floatValue());
                }

                mp.setWindDirectionCode("");
                mp.setWindDirectionName("");

                mp.setWindSpeedName("");
            }

            if (jo.getJsonArray("weather").isNull(0)) {
                mp.setWeatherValue("nepoznato");
                mp.setWeatherMain("npoznato");
            } else {
                mp.setWeatherValue(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
                mp.setWeatherMain(jo.getJsonArray("weather").getJsonObject(0).getString("main"));
            }

//            mp.setCloudsValue(jo.getJsonObject("clouds").getInt("all"));
//            mp.setCloudsName(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
//            mp.setPrecipitationMode("");
//            mp.setWeatherIcon(jo.getJsonArray("weather").getJsonObject(0).getString("icon"));
//            mp.setWeatherNumber(jo.getJsonArray("weather").getJsonObject(0).getInt("id"));
            //mp.setCountry(new String(jo.getJsonObject("sys").getJsonString("country").getString()));
            mp.setCountry(jo.getJsonObject("sys").getString("country"));
            mp.setName(jo.getString("name"));

            mp.setLastUpdate(new Date(jo.getJsonNumber("dt").bigDecimalValue().longValue() * 1000));
            return mp;

        } catch (Exception ex) {
            System.out.println("GRESKA: " + ex.getMessage());
            Logger.getLogger(OWMKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public MeteoPrognoza[] getWeatherForecast(String latitude, String longitude) {

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
            MeteoPrognoza[] mprog = new MeteoPrognoza[jo.getJsonArray("list").size()];

            for (int i = 0; i < jo.getJsonArray("list").size(); i++) {
                MeteoPodaci mp = new MeteoPodaci();
                JsonObject dan = jo.getJsonArray("list").getJsonObject(i);

                if (dan.isNull("main")) {
                    float a = 0;
                    mp.setTemperatureValue(a);
                    mp.setTemperatureMin(a);
                    mp.setTemperatureMax(a);
                    mp.setPressureValue(a);
                    mp.setHumidityValue(a);
                } else {
                    mp.setTemperatureValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("temp").doubleValue()).floatValue());
                    mp.setTemperatureMin(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("temp_min").doubleValue()).floatValue());
                    mp.setTemperatureMax(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("temp_max").doubleValue()).floatValue());
                    mp.setPressureValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("pressure").doubleValue()).floatValue());
                    mp.setHumidityValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("main").getJsonNumber("humidity").doubleValue()).floatValue());
                }

                if (dan.isNull("wind") || dan.getJsonObject("wind").isNull("speed") || dan.getJsonObject("wind").isNull("deg")) {
                    float b = 0;//ako ne stavim ništa onda javlja problem kod upisa u bazu zbog null vrijednosti
                    mp.setWindSpeedValue(b);
                    mp.setWindDirectionValue(b);
                } else {
                    mp.setWindSpeedValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("wind").getJsonNumber("speed").doubleValue()).floatValue());
                    mp.setWindDirectionValue(new Double(jo.getJsonArray("list").getJsonObject(i).getJsonObject("wind").getJsonNumber("deg").doubleValue()).floatValue());

                }

                if (dan.isNull("weather")) {
                    String c = "0";
                    mp.setWeatherValue(c);
                    mp.setWeatherMain(c);
                } else {
                    mp.setWeatherValue(jo.getJsonArray("list").getJsonObject(i).getJsonArray("weather").getJsonObject(0).getString("description"));
                    mp.setWeatherMain(jo.getJsonArray("list").getJsonObject(i).getJsonArray("weather").getJsonObject(0).getString("main"));
                }
                mp.setName(jo.getJsonArray("list").getJsonObject(i).getString("dt_txt"));//mala prilagodna kako bi uzeo datum kao string i spremio ga u meteo podatke
                String adresa = jo.getJsonObject("city").getString("name");
                mprog[i] = new MeteoPrognoza(adresa, i, mp);
            }
            return mprog;

        } catch (Exception ex) {
            System.out.println("GRESKA: " + ex.getMessage());
            Logger.getLogger(OWMKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
