/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author grupa_1
 */
@Named(value = "lokalizacija")
@SessionScoped
public class Lokalizacija implements Serializable {

    private final static Map<String, Object> jezici = new HashMap<>();
    private String odabraniJezik;
    private Locale vazeciJezik;

    static {
        jezici.put("hr", new Locale("hr"));
        jezici.put("en", new Locale("en"));
        jezici.put("de", new Locale("de"));
    }

    /**
     * Creates a new instance of Lokalizacija
     */
    public Lokalizacija() {
        vazeciJezik = new Locale("hr");
    }

    public String getOdabraniJezik() {
        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
    }

    public Map<String, Object> getJezici() {
        return jezici;
    }

    public Locale getVazeciJezik() {
        return vazeciJezik;
    }

    public Object odaberiJezik() {
        if (jezici.get(odabraniJezik) != null) {
            FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) jezici.get(odabraniJezik));
            vazeciJezik = (Locale) jezici.get(odabraniJezik);
            return "OK";
        } else {
            return "ERROR";
        }
    }
}
