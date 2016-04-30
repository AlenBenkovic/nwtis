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
    private UUID idIgre;

    public Igrac(String ime, int idIgraca, UUID idIgre) {
        this.ime = ime;
        this.idIgraca = idIgraca;
        this.idIgre = idIgre;
        System.out.println("SERVER | Kreiram igraca: "+ ime + " |ID: " + this.idIgraca + " |ID IGRE: "+ this.idIgre);
    }
    
    public String dajIme(){
        return ime;
    }
    
    

}
