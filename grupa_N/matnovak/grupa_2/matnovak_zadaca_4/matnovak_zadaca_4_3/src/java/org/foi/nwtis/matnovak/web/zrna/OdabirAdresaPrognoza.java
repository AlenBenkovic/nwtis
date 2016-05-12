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
import org.foi.nwtis.matnovak.web.podaci.MeteoPrognoza;

/**
 *
 * @author grupa_2
 */
@Named(value = "odabirAdresaPrognoza")
@SessionScoped
public class OdabirAdresaPrognoza implements Serializable {

    @EJB
    private MeteoAdresniKlijent meteoAdresniKlijent;

    @EJB
    private AdreseFacade adreseFacade;

    private String novaAdresa;
    private Map<String, Object> aktivneAdrese;
    private List<String> adreseZaDodavanje;
    private Map<String, Object> odabraneAdrese;
    private List<String> adreseZaMicanje;
    private String azuriranaAdresa;
    private List<MeteoPrognoza> prognozaVremena;
    private String prikaziAzuriranjeAdresa = "none";
    
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

    public Map<String, Object> getAktivneAdrese() {
        List<Adrese> adrese = adreseFacade.findAll();
        aktivneAdrese = new HashMap<>();
        for (Adrese adresa : adrese) {
            boolean nijeOdabrana = true;
            Iterator<Map.Entry<String, Object>> iterator = 
                    odabraneAdrese.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> odabranaAdresa = iterator.next();
                if(odabranaAdresa.getValue().toString().compareTo(adresa.getIdadresa().toString()) == 0){
                    nijeOdabrana=false;
                    break;
                }
            }
            if(nijeOdabrana){
                aktivneAdrese.put(adresa.getAdresa(),adresa.getIdadresa().toString());
            }
        }
        
        return aktivneAdrese;
    }

    public void setAktivneAdrese(Map<String, Object> aktivneAdrese) {
        this.aktivneAdrese = aktivneAdrese;
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

    public List<MeteoPrognoza> getPrognozaVremena() {
        return prognozaVremena;
    }

    public void setPrognozaVremena(List<MeteoPrognoza> prognozaVremena) {
        this.prognozaVremena = prognozaVremena;
    }

    public List<String> getAdreseZaDodavanje() {
        return adreseZaDodavanje;
    }

    public void setAdreseZaDodavanje(List<String> adreseZaDodavanje) {
        this.adreseZaDodavanje = adreseZaDodavanje;
    }

    public List<String> getAdreseZaMicanje() {
        return adreseZaMicanje;
    }

    public void setAdreseZaMicanje(List<String> adreseZaMicanje) {
        this.adreseZaMicanje = adreseZaMicanje;
    }
    
    public String dodajNovuAdresu(){
        Lokacija l = meteoAdresniKlijent.dajLokaciju(novaAdresa);
        Adrese novaAdresaEntity = new Adrese(Integer.BYTES, this.novaAdresa, l.getLatitude(), l.getLongitude());
        adreseFacade.create(novaAdresaEntity);
        return "";
    }
    
    public String preuzmiAdrese(){
        for (String idAdrese : adreseZaDodavanje) {
            Iterator<Map.Entry<String, Object>> iterator = 
                    aktivneAdrese.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> adresa = iterator.next();
                if(adresa.getValue().toString().compareTo(idAdrese) == 0){
                    odabraneAdrese.put(adresa.getKey(), idAdrese);
                }
            }
        }
        return "";
    }
    
    public String makniAdrese(){
        return "";
    }
    
    public String azurirajAdresu(){
        if(adreseZaDodavanje.size()==1){
            String idAzuriraneAdrese = adreseZaDodavanje.get(0);
            Iterator<Map.Entry<String, Object>> iterator = 
                    aktivneAdrese.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> adresa = iterator.next();
                if(adresa.getValue().toString().compareTo(idAzuriraneAdrese) == 0){
                    azuriranaAdresa = adresa.getKey();
                    prikaziAzuriranjeAdresa = "both";
                    break;
                }
            }
        }
        else{
            //TODO napravi nešto ispiši grešku
        }
        return "";
    }
    
    public String spremiAzuriranje(){
        prikaziAzuriranjeAdresa = "none";
        azuriranaAdresa = "";
        return "";
    }
    
    public String pregledajPrognoze(){
        return "";
    }

    public String getPrikaziAzuriranjeAdresa() {
        return prikaziAzuriranjeAdresa;
    }

    public void setPrikaziAzuriranjeAdresa(String prikaziAzuriranjeAdresa) {
        this.prikaziAzuriranjeAdresa = prikaziAzuriranjeAdresa;
    }
}
