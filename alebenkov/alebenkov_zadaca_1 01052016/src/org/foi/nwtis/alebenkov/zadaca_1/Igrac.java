/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.UUID;

/**
 *
 * @author abenkovic
 */
public class Igrac {

    private String ime;
    private int idIgraca;
    private int idIgre;
    private int brojPoteza = 0;
    private int brojPogodaka = 0;
    private int brojBrodova = 0;

    public Igrac(String ime, int idIgraca, int idIgre, int brojBrodova) {
        this.ime = ime;
        this.idIgraca = idIgraca;
        this.idIgre = idIgre;
        this.brojBrodova = brojBrodova;
        System.out.println("SERVER | Kreiram igraca: " + ime + " |ID: " + this.idIgraca + " |ID IGRE: " + this.idIgre);
    }

    public String dajIme() {
        return ime;
    }

    public int dohvatiId() {
        return idIgraca;
    }

    public void povecajBrojPoteza() {
        brojPoteza += 1;
    }

    public void povecajBrojPogodaka() {
        brojPogodaka += 1;
    }

    public int dohvatiBrojPoteza() {
        return brojPoteza;
    }
    
    public int dohvatiBrojBrodova(){
        return brojBrodova;
    }
    
    public void smanjiBrojBrodova(){
        this.brojBrodova = this.brojBrodova-1;
    }
    
    public int dohvatiBrojPogodaka(){
        return brojPogodaka;
    }
    
    public int dohvatiIdIgre(){
        return idIgre;
    }

}
