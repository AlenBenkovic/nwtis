/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

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
    
    public String provjeraKorisnika(){
        //TODO provjeri korisnika prema
        if(this.korisnickoIme.equals("alebenkov") && this.korisnickaLozinka.equals("123456")){
            return "OK";
        } else {
            return "NOT_OK";
        }
    }
    
}
