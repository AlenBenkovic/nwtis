/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.foi.nwtis.matnovak.ejb.eb.Adrese;
import org.foi.nwtis.matnovak.ejb.sb.AdreseFacade;
import org.foi.nwtis.matnovak.ejb.sb.MeteoAdresniKlijent;
import org.foi.nwtis.matnovak.web.podaci.Lokacija;

/**
 *
 * @author grupa_3
 */
@Named(value = "odabirAdresaPrognoza")
@SessionScoped
public class OdabirAdresaPrognoza implements Serializable {

    @EJB
    private MeteoAdresniKlijent meteoAdresniKlijent;

    @EJB
    private AdreseFacade adreseFacade;

    private String novaAdresa;
    private Map<String, Object> ponudeneAdrese;
    private List<String> oznacenePonudneAdrese;
    private Map<String, Object> odabraneAdrese;
    private List<String> oznaceneOdabraneAdrese;
    private String azuriranaAdresa;
    private List<String> prognozeVremena;
    private boolean prikaziAzuriranje = false;
    
    /**
     * Creates a new instance of OdabirAdresaPrognoza
     */
    public OdabirAdresaPrognoza() {
        odabraneAdrese = new HashMap<>();

    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }

    public Map<String, Object> getPonudeneAdrese() {
        List<Adrese> adrese = adreseFacade.findAll();
        ponudeneAdrese = new HashMap<>();
        for (Adrese adresa : adrese) {
            Iterator<Map.Entry<String, Object>> iterator = odabraneAdrese.entrySet().iterator();
            boolean vecJeOdabrana = false;
            while (iterator.hasNext()) {
                Map.Entry<String, Object> odabranaAdresa = iterator.next();
                if(odabranaAdresa.getValue().toString().compareTo(adresa.getIdadresa().toString()) == 0){
                    vecJeOdabrana = true;
                    break;
                }
            }
            if(!vecJeOdabrana){
                ponudeneAdrese.put(adresa.getAdresa(), adresa.getIdadresa());
            }
        }

        return ponudeneAdrese;
    }

    public void setPonudeneAdrese(Map<String, Object> ponudeneAdrese) {
        this.ponudeneAdrese = ponudeneAdrese;
    }

    public Map<String, Object> getOdabraneAdrese() {
        return odabraneAdrese;
    }

    public void setOdabraneAdrese(Map<String, Object> odabraneAdrese) {
        this.odabraneAdrese = odabraneAdrese;
    }

    public String getAzuriranaAdresa() {
        return azuriranaAdresa;
    }

    public void setAzuriranaAdresa(String azuriranaAdresa) {
        this.azuriranaAdresa = azuriranaAdresa;
    }

    public List<String> getPrognozeVremena() {
        return prognozeVremena;
    }

    public void setPrognozeVremena(List<String> prognozeVremena) {
        this.prognozeVremena = prognozeVremena;
    }

    public List<String> getOznacenePonudneAdrese() {
        return oznacenePonudneAdrese;
    }

    public void setOznacenePonudneAdrese(List<String> oznacenePonudneAdrese) {
        this.oznacenePonudneAdrese = oznacenePonudneAdrese;
    }

    public List<String> getOznaceneOdabraneAdrese() {
        return oznaceneOdabraneAdrese;
    }

    public void setOznaceneOdabraneAdrese(List<String> oznaceneOdabraneAdrese) {
        this.oznaceneOdabraneAdrese = oznaceneOdabraneAdrese;
    }
    
     public boolean isPrikaziAzuriranje() {
        return prikaziAzuriranje;
    }

    public void setPrikaziAzuriranje(boolean prikaziAzuriranje) {
        this.prikaziAzuriranje = prikaziAzuriranje;
    }

    public String dodajAdresu() {
        Lokacija l = meteoAdresniKlijent.dajLokaciju(novaAdresa);
        Adrese adresa = new Adrese(Integer.BYTES, novaAdresa, l.getLatitude(), l.getLongitude());
        adreseFacade.create(adresa);
        return "";
    }

    public String preuzmiAdrese() {
         
        for (String idAdrese : oznacenePonudneAdrese) {
            Iterator<Map.Entry<String, Object>> iterator = ponudeneAdrese.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> adresa = iterator.next();
                String nazivAdrese = adresa.getKey();
                if(adresa.getValue().toString().compareTo(idAdrese) == 0){
                    odabraneAdrese.put(nazivAdrese, idAdrese);
                    
                    break;
                }
            }
        }

        return "";
    }

    public String makniAdrese() {
        return "";
    }

    public String azurirajAdresu() {
        if(oznacenePonudneAdrese.size() != 1){
            //TODO ispisati grešku ili nešto
        }else{
            String idAdrese = oznacenePonudneAdrese.get(0);
            Iterator<Map.Entry<String, Object>> iterator = ponudeneAdrese.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> adresa = iterator.next();
                String nazivAdrese = adresa.getKey();
                if(adresa.getValue().toString().compareTo(idAdrese) == 0){
                    azuriranaAdresa = nazivAdrese;
                    prikaziAzuriranje = true;
                    break;
                }
            }
        }
        return "";
    }

    public String upisiAdresu() {
        azuriranaAdresa = "";
        prikaziAzuriranje = false;
        //TODO dovrši
        return "";
    }

    public String pregledPrognoza() {
        return "";
    }

    public String zatvoriPrognoze() {
        return "";
    }
}
