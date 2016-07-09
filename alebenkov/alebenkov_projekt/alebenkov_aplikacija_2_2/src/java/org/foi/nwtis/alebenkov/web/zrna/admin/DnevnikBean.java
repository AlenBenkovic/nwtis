/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna.admin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.foi.nwtis.alebenkov.ejb.eb.Dnevnik;
import org.foi.nwtis.alebenkov.ejb.sb.DnevnikFacade;

/**
 *
 * @author alebenkov
 */
@Named(value = "dnevnikBean")
@RequestScoped
public class DnevnikBean {

    @EJB
    private DnevnikFacade dnevnikFacade;
    private List<Dnevnik> dnevnik;
    private String odDatuma;
    private String doDatuma;
    private Date odD;
    private Date doD;
    private boolean f = false;

    /**
     * Creates a new instance of DnevnikBean
     */
    public DnevnikBean() {
    }

    public String getOdDatuma() {
        return odDatuma;
    }

    public void setOdDatuma(String odDatuma) {
        this.odDatuma = odDatuma;
    }

    public String getDoDatuma() {
        return doDatuma;
    }

    public void setDoDatuma(String doDatuma) {
        this.doDatuma = doDatuma;
    }

    public List<Dnevnik> getDnevnik() {
        if (!f) {
            dnevnik = dnevnikFacade.findAll();
        }

        return dnevnik;
    }

    public void filtrirajDatum() {

        try {
            //za brisanje prilikom iteracije http://stackoverflow.com/questions/223918/iterating-through-a-collection-avoiding-concurrentmodificationexception-when-re
            System.out.println("OD: " + odDatuma + " DO: " + doDatuma);
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            odD = df.parse(odDatuma);
            doD = df.parse(doDatuma);

            for (Iterator<Dnevnik> iterator = dnevnik.iterator(); iterator.hasNext();) {
                Dnevnik d = iterator.next();
                if ( d.getVrijeme().before(odD) || d.getVrijeme().after(doD) ) {
                    iterator.remove();
                }
            }
            f = true;

        } catch (ParseException ex) {
            //krivo uneseni datum ne radi nista...
        }

    }

}
