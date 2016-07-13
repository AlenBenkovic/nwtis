/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.jsp.beans;

import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.podaci.User;
import org.foi.nwtis.alebenkov.web.server.DBOps;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author alebenkov
 */
public class ListaKorisnikaBean implements Serializable{
    private List<User> korisnici;
    private HttpServletRequest request;
    private int brojStranica;
    private Konfiguracija konfig = null;

    public ListaKorisnikaBean() {
        
    }

    public List<User> getKorisnici() {
        DBOps db = new DBOps();
        korisnici = db.ucitajKorisnike();
        return korisnici;
    }

    public void setKorisnici(List<User> korisnici) {
        this.korisnici = korisnici;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public int getBrojStranica() {
        this.konfig = SlusacAplikacije.getServerConfig();
        this.brojStranica = Integer.parseInt(konfig.dajPostavku("stranicenje"));
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }
    
    
}
