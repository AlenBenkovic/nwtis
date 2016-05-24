/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.kontrole;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abenkovic
 */
public class Datoteka implements Serializable{
    
    private String apsolutnaPutanja;
    private String nazivDatoteke;
    private long velicina;
    private Date vrijemeKreiranja;

    public Datoteka(String apsolutnaPutanja, String nazivDatoteke, long velicina, Date vrijemeKreiranja) {
        this.apsolutnaPutanja = apsolutnaPutanja;
        this.nazivDatoteke = nazivDatoteke;
        this.velicina = velicina;
        this.vrijemeKreiranja = vrijemeKreiranja;
    }

    public String getApsolutnaPutanja() {
        return apsolutnaPutanja;
    }

    public void setApsolutnaPutanja(String apsolutnaPutanja) {
        this.apsolutnaPutanja = apsolutnaPutanja;
    }

    public String getNazivDatoteke() {
        return nazivDatoteke;
    }

    public void setNazivDatoteke(String nazivDatoteke) {
        this.nazivDatoteke = nazivDatoteke;
    }

    public long getVelicina() {
        return velicina;
    }

    public void setVelicina(long velicina) {
        this.velicina = velicina;
    }

    public Date getVrijemeKreiranja() {
        return vrijemeKreiranja;
    }

    public void setVrijemeKreiranja(Date vrijemeKreiranja) {
        this.vrijemeKreiranja = vrijemeKreiranja;
    }

     
    
}
