/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.Serializable;

/**
 * Klasa za igrace
 * @author Alen Benkovic
 */
public class Igrac implements Serializable{

    private final String ime;
    private final int idIgraca;
    private final int idIgre;
    private int brojPoteza = 0;
    private int brojPogodaka = 0;
    private int brojBrodova = 0;

    /**
     * Konstruktor klase Igrac
     * @param ime
     * @param idIgraca
     * @param idIgre
     * @param brojBrodova
     */
    public Igrac(String ime, int idIgraca, int idIgre, int brojBrodova) {
        this.ime = ime;
        this.idIgraca = idIgraca;
        this.idIgre = idIgre;
        this.brojBrodova = brojBrodova;
        System.out.println("SERVER | Kreiram igraca: " + ime + " | ID: " + this.idIgraca + " | ID IGRE: " + this.idIgre);
    }

    /**
     *
     * @return ime igraca
     */
    public String dajIme() {
        return ime;
    }

    /**
     *
     * @return id igraca
     */
    public int dohvatiId() {
        return idIgraca;
    }

    /**
     *
     */
    public void povecajBrojPoteza() {
        brojPoteza += 1;
    }

    /**
     *
     */
    public void povecajBrojPogodaka() {
        brojPogodaka += 1;
    }

    /**
     *
     * @return broj poteza igraca
     */
    public int dohvatiBrojPoteza() {
        return brojPoteza;
    }

    /**
     *
     * @return broj brodova igraca
     */
    public int dohvatiBrojBrodova() {
        return brojBrodova;
    }

    /**
     *
     */
    public void smanjiBrojBrodova() {
        this.brojBrodova = this.brojBrodova - 1;
    }

    /**
     *
     * @return broj pogodaka igraca
     */
    public int dohvatiBrojPogodaka() {
        return brojPogodaka;
    }

    /**
     *
     * @return id igre
     */
    public int dohvatiIdIgre() {
        return idIgre;
    }

}
