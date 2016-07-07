/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik;
import org.foi.nwtis.alebenkov.ejb.sb.KorisnikFacade;

/**
 *
 * @author alebenkov
 */
@Named(value = "prijavaBean")
@RequestScoped
public class PrijavaBean {

    @EJB
    private KorisnikFacade korisnikFacade;
    private String user;
    private String pass;
    private boolean odobren = false;
    private boolean greska = false;
    private boolean odbijeno = false;

    /**
     * Creates a new instance of PrijavaBean
     */
    public PrijavaBean() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isOdobren() {
        return odobren;
    }

    public void setOdobren(boolean odobren) {
        this.odobren = odobren;
    }

    public boolean isGreska() {
        return greska;
    }

    public void setGreska(boolean greska) {
        this.greska = greska;
    }

    public boolean isOdbijeno() {
        return odbijeno;
    }

    public void setOdbijeno(boolean odbijeno) {
        this.odbijeno = odbijeno;
    }

    public String provjeraKorisnika() {
        Korisnik k = korisnikFacade.find(this.user);
        if (k != null && k.getPass().equals(pass)) {
            HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
            HttpSession s = request.getSession();
            if (k.getOdobreno() == 0) {
                odobren = true;
                return "prijava.xhtml";
            } else if (k.getOdobreno() == 2) {
                odbijeno = true;
                return "prijava.xhtml";
            } else if (k.getRole() == 1) {
                s.setAttribute("user", k.getKorisnik());
                s.setAttribute("pass", k.getPass());
                s.setAttribute("role", k.getRole());
                s.setAttribute("rang", k.getRang());
                return "OK_ADMIN";
            } else {
                s.setAttribute("user", k.getKorisnik());
                s.setAttribute("pass", k.getPass());
                s.setAttribute("role", k.getRole());
                s.setAttribute("rang", k.getRang());
                return "OK_USER";
            }
        } else {
            greska = true;
            return "prijava.xhtml";
        }
    }

}
