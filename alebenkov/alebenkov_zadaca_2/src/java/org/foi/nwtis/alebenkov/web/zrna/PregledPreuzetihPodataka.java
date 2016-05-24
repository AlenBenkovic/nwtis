/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import org.foi.nwtis.alebenkov.web.kontrole.Datoteka;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author abenkovic
 */
@Named(value = "pregledPreuzetihPodataka")
@RequestScoped
public class PregledPreuzetihPodataka implements Serializable {

    private List<Datoteka> popisDatoteka;

    /**
     * Creates a new instance of PregledPreuzetihPodataka
     */
    public PregledPreuzetihPodataka() {
        
        
    }

    public List<Datoteka> getPopisDatoteka() {
        popisDatoteka = (List<Datoteka>) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("datotekeWeb");
        return popisDatoteka;
    }

    public void setPopisDatoteka(List<Datoteka> popisDatoteka) {
        this.popisDatoteka = popisDatoteka;
    }
    
    public String pregledDatoteke(){
        return "OK";
    }

}
