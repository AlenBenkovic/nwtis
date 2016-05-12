/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author grupa_3
 */
@Named(value = "lokalizacija")
@SessionScoped
public class Lokalizacija implements Serializable {

    private static Map<String, Object> jezici = new HashMap<String, Object>();
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
        odabraniJezik = "hr";
        vazeciJezik = new Locale("hr");
    }

    public String getOdabraniJezik() {
        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
    }

    public Locale getVazeciJezik() {
        //vazeciJezik = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        return vazeciJezik;
    }

    public Object odaberiJezik() {
        if (odabraniJezik != null && jezici.get(odabraniJezik) != null) {

            FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) jezici.get(this.odabraniJezik));
            vazeciJezik = (Locale) jezici.get(this.odabraniJezik);
            return "OK";
        } else {
            return "ERROR";
        }
    }

    public Map<String, Object> getJezici() {
        return jezici;
    }

}
