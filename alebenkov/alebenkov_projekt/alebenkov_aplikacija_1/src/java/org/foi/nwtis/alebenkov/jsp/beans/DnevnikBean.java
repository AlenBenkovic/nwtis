/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.jsp.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.podaci.Dnevnik;
import org.foi.nwtis.alebenkov.web.server.DBOps;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author alebenkov
 */
public class DnevnikBean implements Serializable {
    private List<Dnevnik> dnevnik;
    private HttpServletRequest request;
    private int brojStranica;
    private Konfiguracija konfig = null;

    public DnevnikBean() {
        
    }

    public List<Dnevnik> getDnevnik() throws ParseException {
        DBOps db = new DBOps();
        dnevnik = db.ucitajDnevnik(1);
        if (request.getParameter("submit") != null) {
            if (request.getParameter("servis") != "") {
                for (Iterator<Dnevnik> iterator = dnevnik.iterator(); iterator.hasNext();) {
                    if (!iterator.next().getNaredba().contains(request.getParameter("servis"))) {
                        iterator.remove();
                    }
                }
            }
            if (request.getParameter("naziv") != "") {
                for (Iterator<Dnevnik> iterator = dnevnik.iterator(); iterator.hasNext();) {
                    if (!iterator.next().getNaredba().contains(request.getParameter("naziv"))) {
                        iterator.remove();
                    }
                }
            }
            if (request.getParameter("od") != "") {
                Date d = new Date();
                for (Iterator<Dnevnik> iterator = dnevnik.iterator(); iterator.hasNext();) {
                    Date datum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(request.getParameter("od"));
                    if (iterator.next().getTime().before(datum)) {
                        iterator.remove();
                    }

                }
            }
            if (request.getParameter("do") != "") {
                Date d = new Date();
                for (Iterator<Dnevnik> iterator = dnevnik.iterator(); iterator.hasNext();) {
                    Date datum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(request.getParameter("do"));
                    if (iterator.next().getTime().after(datum)) {
                        iterator.remove();
                    }

                }
            }

        }
        return dnevnik;
    }

    public void setDnevnik(List<Dnevnik> dnevnik) {
        this.dnevnik = dnevnik;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public int getBrojStranica() {
        this.konfig = SlusacAplikacije.getServerConfig();
        this.brojStranica = Integer.parseInt(konfig.dajPostavku("stranicenje"));
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }

}
