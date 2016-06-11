/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ejb.sb;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import org.foi.nwtis.alebenkov.ws.serveri.MeteoPodaci;

/**
 *
 * @author abenkovic
 */
@Stateful
@LocalBean
public class MeteoPrognosticar {
    private List<String> adrese;
    private List<MeteoPodaci> meteoPodaci;

    public List<String> getAdrese() {
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public List<String> dajAdrese() {
        return this.getAdrese();
    }

    
 
    
}
