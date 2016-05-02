/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.Serializable;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author abenkovic
 */
public class Evidencija implements Serializable {

    PotapanjeBrodova igra;
 

    public Evidencija(PotapanjeBrodova igra) {
        this.igra = igra;
      
    }

    public PotapanjeBrodova dohvatiSpremljenuIgru() {
        return igra;
    }

}
