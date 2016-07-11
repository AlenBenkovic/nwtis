/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.alebenkov.web.komunikacija.SocketServer;

/**
 *
 * @author alebenkov
 */
@Named(value = "meteoBean")
@RequestScoped
public class MeteoBean {

    private String odgovor = "";
    private String user = "";
    private String pass = "";
    private SocketServer server;
    private String adresa="";

    /**
     * Creates a new instance of MeteoBean
     */
    public MeteoBean() {
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession s = request.getSession(false);
        if (!s.isNew()) {
            user = s.getAttribute("user").toString();
            pass = s.getAttribute("pass").toString();
        }

        this.server = new SocketServer();
    }
    
     public void add() {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; ADD \"" + this.adresa.trim() + "\";");
    }
     
     public void test() {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; TEST \"" + this.adresa.trim() + "\";");
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOdgovor() {
        return odgovor;
    }
    
    
     

}
