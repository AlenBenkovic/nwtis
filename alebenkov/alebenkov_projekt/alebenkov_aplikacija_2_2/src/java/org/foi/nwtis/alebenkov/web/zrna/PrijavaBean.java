/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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

    public String provjeraKorisnika() {
        Korisnik k = korisnikFacade.find(this.user);
        if (k != null && k.getPass().equals(pass)) {
            if (k.getRang() == 1) {
                return "OK_ADMIN";
            } else {
                return "OK_USER";
            }
        } else {
            return "NOT_OK";
        }
    }

}
