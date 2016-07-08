/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna.admin;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
@Named(value = "kategorijeBean")
@RequestScoped
public class KategorijeBean {

    @EJB
    private KorisnikFacade korisnikFacade;
    private List<Korisnik> korisnici;
    private String odgovor = "";
    private String user = "";
    private String pass = "";
    private SocketServer server;
    private Korisnik k;
    private boolean prikaziOdgovor = false;

    /**
     * Creates a new instance of KategorijeBean
     */
    public KategorijeBean() {
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession s = request.getSession(false);
        if (!s.isNew()) {
            user = s.getAttribute("user").toString();
            pass = s.getAttribute("pass").toString();
        }

        this.server = new SocketServer();
    }

    public List<Korisnik> getKorisnici() {
        korisnici = korisnikFacade.findAll();
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public boolean isPrikaziOdgovor() {
        if (odgovor.length() > 0) {
            prikaziOdgovor = true;
        }
        return prikaziOdgovor;
    }

    public void setPrikaziOdgovor(boolean prikaziOdgovor) {
        this.prikaziOdgovor = prikaziOdgovor;
    }

    public void povecaj(Korisnik k) {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; UP " + k.getKorisnik() + ";");

    }

    public void smanji(Korisnik k) {
        odgovor = server.posalji("USER " + user + "; PASSWD " + pass + "; DOWN " + k.getKorisnik() + ";");

    }

}
