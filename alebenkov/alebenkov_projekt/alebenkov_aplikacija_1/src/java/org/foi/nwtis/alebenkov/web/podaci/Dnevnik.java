/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.podaci;

import java.util.Date;

/**
 *
 * @author alebenkov
 */
public class Dnevnik {
    private int id;
    private String user;
    private String naredba;
    private String odgovor;
    private Date time;

    public Dnevnik(int id, String user, String naredba, String odgovor, Date time) {
        this.id = id;
        this.user = user;
        this.naredba = naredba;
        this.odgovor = odgovor;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNaredba() {
        return naredba;
    }

    public void setNaredba(String naredba) {
        this.naredba = naredba;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    
    
}
