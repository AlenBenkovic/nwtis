/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.foi.nwtis.alebenkov.web.kontrole.Datoteka;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.unbescape.html.HtmlEscape;

/**
 * Klasa za pregled svih preuzetih web stranica
 * @author abenkovic
 */
@Named(value = "pregledPreuzetihPodataka")
@RequestScoped
public class PregledPreuzetihPodataka implements Serializable {

    private List<Datoteka> popisDatoteka;
    private String lokacija;
    private String sadrzaj;

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

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public String pregledDatoteke() {
        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.lokacija = params.get("lokacija");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(lokacija));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String sadrzajHtml = sb.toString();
            sadrzaj = HtmlEscape.escapeHtml5(sadrzajHtml);
            return "OK";

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PregledPreuzetihPodataka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PregledPreuzetihPodataka.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(PregledPreuzetihPodataka.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "ERROR";
    }
}
