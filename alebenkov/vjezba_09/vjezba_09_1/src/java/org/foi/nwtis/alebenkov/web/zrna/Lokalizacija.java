/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author abenkovic
 */
@Named(value = "lokalizacija")
@SessionScoped
public class Lokalizacija implements Serializable {

    private static Map<String, String> jezici;

    static {
        jezici = new LinkedHashMap<String, String>();
        jezici.put("Hrvatski", "hr");
        jezici.put("English", "en");
    }

    private String odabraniJezik = "hr";
    private String vazeciJezik;
    private Locale lokal = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    /**
     * Creates a new instance of Lokalizacija
     */
    public Lokalizacija() {
    }

    public Map<String, String> getJezici() {
        return jezici;
    }

    public String getOdabraniJezik() {
        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
    }

    public String getVazeciJezik() {
        return vazeciJezik;
    }

    public void setVazeciJezik(String vazeciJezik) {
        this.vazeciJezik = vazeciJezik;
    }

    public String odaberiJezik() {
        lokal = new Locale(odabraniJezik);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(lokal);
        return "OK";
    }

    public Locale getLokal() {
        return lokal;
    }

    public void promjeniJezik(ValueChangeEvent event) {
        String noviJezik = (String) event.getNewValue();
        odabraniJezik = noviJezik;
        lokal = new Locale(odabraniJezik);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(lokal);
    }

}
