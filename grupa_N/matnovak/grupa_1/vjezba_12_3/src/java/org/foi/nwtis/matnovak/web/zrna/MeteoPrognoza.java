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
import org.foi.nwtis.dkermek.ws.serveri.MeteoPodaci;
import org.foi.nwtis.matnovak.ejb.sb.MeteoOsvjezivac;
import org.foi.nwtis.matnovak.ejb.sb.MeteoPrognosticar;
import org.foi.nwtis.matnovak.web.websocket.AdresaEndpoint;


/**
 *
 * @author grupa_1
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
        if(meteoPrognosticar != null && adrese == null){
            adrese = meteoPrognosticar.dajAdrese();
            List<MeteoPrognosticar> mpr = meteoOsvjezivac.getMeteoprognosticari();
            mpr.add(meteoPrognosticar);
            meteoOsvjezivac.setMeteoprognosticari(mpr);
        }else{
            System.err.println("MeteoPrognostičar je null!");
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
    
    public String dodajAdresu(){
        if(novaAdresa != null && !novaAdresa.isEmpty()){
            System.out.println("Dodana adresa: "+novaAdresa);
            adrese.add(novaAdresa);
            meteoPrognosticar.setAdrese(adrese);
            meteoOsvjezivac.sendJMSMessageToNWTiS_vjezba_12("Dodana adresa:"+novaAdresa);
            AdresaEndpoint.obavijestiPromjenu("Dodana adresa:"+novaAdresa);
        }
        return "DODANA";
    }
    
    public String obrisiAdresu(){
        if(odabranaAdresa != null){
            if(adrese.contains(odabranaAdresa)){
                adrese.remove(odabranaAdresa);
                meteoPrognosticar.setAdrese(adrese);
                meteoOsvjezivac.sendJMSMessageToNWTiS_vjezba_12("Obrisana adresa:"+odabranaAdresa);
                AdresaEndpoint.obavijestiPromjenu("Obrisana adresa:"+novaAdresa);
            }
        }
        return "OBRISANA";
    }
    
    public String osvjeziMeteoPodatke(){
        meteoPodaci = meteoPrognosticar.getMeteoPodaci();
        return "OSVJEZENO";
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }
}
