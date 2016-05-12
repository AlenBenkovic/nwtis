/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import org.foi.nwtis.matnovak.ejb.sb.MeteoOsvjezivac;
import org.foi.nwtis.matnovak.ejb.sb.MeteoPrognosticar;
import org.foi.nwtis.matnovak.web.websocket.AdresaEndpoint;
import org.foi.nwtis.matnovak.ws.serveri.MeteoPodaci;


/**
 *
 * @author grupa_2
 */
@Named(value = "meteoPrognoza")
@SessionScoped
public class MeteoPrognoza implements Serializable {

    @EJB
    private MeteoOsvjezivac meteoOsvjezivac;

    @EJB
    private MeteoPrognosticar meteoPrognosticar;

    private String odabranaAdresa;
    private List<String> adrese;
    private List<MeteoPodaci> meteoPodaci;
    private String novaAdresa;
    
    /**
     * Creates a new instance of MeteoPrognoza
     */
    public MeteoPrognoza() {
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public List<String> getAdrese() {
        if(adrese == null){
            adrese = meteoPrognosticar.dajAdrese();
            meteoOsvjezivac.dodajMeteoPrognosicara(meteoPrognosticar);
        }
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        meteoPodaci = meteoPrognosticar.getMeteoPodaci();
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }
    
    public String dodajAdresu() {
        if(novaAdresa != null && !novaAdresa.isEmpty()){
            adrese.add(novaAdresa);
            meteoPrognosticar.setAdrese(adrese);
            meteoOsvjezivac.sendJMSMessageToNWTiS_vjezba_12("Dodana adresa:"+novaAdresa);
            AdresaEndpoint.obavijestiPromjenu("Dodana adresa:"+novaAdresa);
        }
        return "DODANA";
    }
    
    public String obrisiAdresu() {
        if(odabranaAdresa != null && !odabranaAdresa.isEmpty()){
            if(adrese.contains(odabranaAdresa)){
                adrese.remove(odabranaAdresa);
                meteoPrognosticar.setAdrese(adrese);
                meteoOsvjezivac.sendJMSMessageToNWTiS_vjezba_12("Obrisana adresa:"+odabranaAdresa);
            }
        }
        return "OBRISANA";
    }
    
    public String osvjeziMeteoPodatke() {
        return "OSVJEZI";
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }
}
