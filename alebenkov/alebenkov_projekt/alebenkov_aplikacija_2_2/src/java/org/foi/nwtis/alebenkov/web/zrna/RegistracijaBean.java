/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.alebenkov.ejb.sb.KorisnikFacade;

/**
 *
 * @author alebenkov
 */
@Named(value = "registracijaBean")
@RequestScoped
public class RegistracijaBean {

    @EJB
    private KorisnikFacade korisnikFacade;

    private String user;
    private String prezime;
    private String pass;
    private String pass2;
    private String mail;
    private int role;
    private boolean passWrong = false;
    private boolean emailWrong = false;
    private boolean sakrij = false;
    private boolean prikazi = true;

    /**
     * Creates a new instance of RegistracijaBean
     */
    public RegistracijaBean() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isPassWrong() {
        return passWrong;
    }

    public void setPassWrong(boolean passWrong) {
        this.passWrong = passWrong;
    }

    public boolean isEmailWrong() {
        return emailWrong;
    }

    public void setEmailWrong(boolean emailWrong) {
        this.emailWrong = emailWrong;
    }

    public boolean isSakrij() {
        return sakrij;
    }

    public void setSakrij(boolean sakrij) {
        this.sakrij = sakrij;
    }

    public boolean isPrikazi() {
        return prikazi;
    }

    public void setPrikazi(boolean prikazi) {
        this.prikazi = prikazi;
    }

    public String registriraj() {
        if (!pass.equals(pass2)) {
            passWrong = true;
        } else if (!mail.contains("@")) {//lose ali bar nesta
            emailWrong = true;
        } else {
            korisnikFacade.kreirajKorisnika(user, prezime, pass, mail, role);
            sakrij = true;
            prikazi = false;
        }
        if (role == 0) {
            return "OK_ADMIN";
        } else {
            return "OK_USER";
        }
    }

}
