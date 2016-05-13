/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author abenkovic
 */
@Named(value = "odaberiKorisnika")
@RequestScoped
public class OdaberiKorisnika {

    private String odabraniKorisnik;
    private Map<String, String> korisnici;
    /**
     * Creates a new instance of OdaberiKorisnika
     */
    public OdaberiKorisnika() {
        korisnici = new LinkedHashMap<String, String>();
        korisnici.put("Alen Benkovic", "alebenkov");
        korisnici.put("Alesn Besankovic", "alas");
        korisnici.put("Alen Bsaenkovic", "alekov");
        korisnici.put("Alen asadBenkovic", "alesasaov");
        korisnici.put("Alen Benkgfgovic", "alov");
        
    }

    public String getOdabraniKorisnik() {
        return odabraniKorisnik;
    }

    public void setOdabraniKorisnik(String odabraniKorisnik) {
        this.odabraniKorisnik = odabraniKorisnik;
    }

    public Map<String, String> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(Map<String, String> korisnici) {
        this.korisnici = korisnici;
    }
    
    public String odabirKorisnika(){
        return "OK";
    }
    
}
