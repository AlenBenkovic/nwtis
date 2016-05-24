/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


/**
 *
 * @author abenkovic
 */
@Named(value = "pregledDatoteke")
@RequestScoped
public class PregledDatoteke implements Serializable {
    
    private String sadrzaj;

    /**
     * Creates a new instance of PregledDatoteke
     */
    public PregledDatoteke() {
        
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }
    
    public String povratakPregledPreuzetihPodataka(){
        return "OK";
    }
  


   
    
}
