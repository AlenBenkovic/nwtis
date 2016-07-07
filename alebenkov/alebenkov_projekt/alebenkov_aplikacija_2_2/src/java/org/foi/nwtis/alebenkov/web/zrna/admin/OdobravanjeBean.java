/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna.admin;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik;
import org.foi.nwtis.alebenkov.ejb.sb.KorisnikFacade;
import org.foi.nwtis.alebenkov.web.komunikacija.SocketServer;

/**
 *
 * @author alebenkov
 */
@Named(value = "odobravanjeBean")
@RequestScoped
public class OdobravanjeBean {

    private List<Korisnik> naCekanju;
    private String odgovor = "";
    private String user;
    private String pass;
    private SocketServer server;
    private Korisnik k;
    private boolean prikaziOdgovor=false;

    @EJB
    private KorisnikFacade korisnikFacade;

    /**
     * Creates a new instance of RegCekBean
     */
    public OdobravanjeBean() {
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession s = request.getSession(false);
        user = s.getAttribute("user").toString();
        pass = s.getAttribute("pass").toString();
        this.server = new SocketServer();
    }

    public List<Korisnik> getNaCekanju() {
        naCekanju = korisnikFacade.korisniciNaCekanju();
        return naCekanju;
    }

    public void setNaCekanju(List<Korisnik> naCekanju) {
        this.naCekanju = naCekanju;
    }

    public boolean isPrikaziOdgovor() {
        if(odgovor.length() > 0){
            prikaziOdgovor = true;
        }
        return prikaziOdgovor;
    }

    public String getOdgovor() {
        return odgovor;
    }
    
    

    public void odobri(Korisnik k) {
        String role;
        if (k.getRole() == 1) {
            role = "ADMIN";
        } else {
            role = "USER";
        }
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; ADD " + k.getKorisnik() + "; PASSWD " + k.getPass() + "; ROLE " + role + ";");

    }

    public void odbaci(Korisnik k) {
        korisnikFacade.odbaciKorisnika(k.getKorisnik());

    }

}
