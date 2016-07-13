/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna.admin;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik;
import org.foi.nwtis.alebenkov.web.komunikacija.SocketServer;

/**
 *
 * @author alebenkov
 */
@Named(value = "stanjeBean")
@SessionScoped
public class StanjeBean implements Serializable {

    private String odgovor = "";
    private String stanje;
    private String user;
    private String pass;
    private SocketServer server;

    /**
     * Creates a new instance of StanjeBean
     */
    public StanjeBean() {
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession s = request.getSession(false);
        user = s.getAttribute("user").toString();
        pass = s.getAttribute("pass").toString();
        this.server = new SocketServer();

    }

    public String getStanje() {
        stanje = server.posalji("USER " + user + "; PASSWD " + pass + "; STATUS;");
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public void pause() {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; PAUSE;");

    }

    public void stop() {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; STOP;");

    }

    public void start() {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; START;");
    }
    

}
