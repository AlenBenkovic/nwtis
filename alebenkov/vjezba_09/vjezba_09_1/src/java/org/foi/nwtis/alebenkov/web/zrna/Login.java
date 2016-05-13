/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author abenkovic
 */
@Named(value = "login")
@RequestScoped
public class Login {

    private String korisnickoIme;
    private String korisnickaLozinka;

    /**
     * Creates a new instance of Login
     */
    public Login() {
        this.korisnickoIme = "alebenkov";
        this.korisnickaLozinka = "";
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
        System.out.println("Korisnicko ime: " + korisnickoIme);
    }

    public String getKorisnickaLozinka() {
        return korisnickaLozinka;
    }

    public void setKorisnickaLozinka(String korisnickaLozinka) {
        this.korisnickaLozinka = korisnickaLozinka;
        System.out.println("Korisnicko ime: " + korisnickaLozinka);
    }

    public String provjeraKorisnika() {
        //TODO provjeri korisnika prema
        if (this.korisnickoIme.equals("alebenkov") && this.korisnickaLozinka.equals("123456")) {
            return "OK";
        } else {
            return "NOT_OK";
        }
    }

    public void validirajKorisnickoIme(FacesContext facesContext, UIComponent arg1, Object value) throws ValidatorException {
        String kIme = ((String) value).trim();
        if (kIme.length() < 4 || kIme.length() > 10) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Nispravan broj znakova. ");
            message.setDetail(message.getSummary());
            facesContext.addMessage("kIme", message);
            throw new ValidatorException(message);
        }
    }
    
     public void validirajKorisnickuLozinku(FacesContext facesContext, UIComponent arg1, Object value) throws ValidatorException {
        String kLoz = ((String) value).trim();
        if (kLoz.length() < 4 || kLoz.length() > 10) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Nispravan broj znakova. ");
            message.setDetail(message.getSummary());
            facesContext.addMessage("kLoz", message);
            throw new ValidatorException(message);
        }
    }

}
