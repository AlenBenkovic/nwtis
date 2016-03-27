/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1_15;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author alen
 */
public class Evidencija15 implements Serializable{
    
    private HashMap<String, EvidencijaModel15> evidencijaRada = new HashMap<>();

    public HashMap<String, EvidencijaModel15> getEvidencijaRada() {
        return evidencijaRada;
    }

    public void setEvidencijaRada(HashMap<String, EvidencijaModel15> evidencijaRada) {
        this.evidencijaRada = evidencijaRada;
    }
}