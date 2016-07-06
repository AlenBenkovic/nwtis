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
    private String poruka;

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
        System.out.println("Korisnicko ime: " + user);
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
        System.out.println("Korisnicko lozinka: " + pass);
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String provjeraKorisnika() {
        Korisnik k = korisnikFacade.find(this.user);
        if (k != null && k.getPass().equals(pass)) {
            HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
            HttpSession s = request.getSession();
            if (k.getOdobreno() == 0) {
                poruka = "Vaš račun još nije odobren!";
                return "prijava.xhtml";
            } else if (k.getRole() == 1) {
                s.setAttribute("user", k.getKorisnik());
                s.setAttribute("role", k.getRole());
                s.setAttribute("rang", k.getRang());
                return "OK_ADMIN";
            } else {
                s.setAttribute("user", k.getKorisnik());
                s.setAttribute("role", k.getRole());
                s.setAttribute("rang", k.getRang());
                return "OK_USER";
            }
        } else {
            poruka = "Neispravno korisničko ime ili lozinka!";
            return "prijava.xhtml";
        }
    }

}
