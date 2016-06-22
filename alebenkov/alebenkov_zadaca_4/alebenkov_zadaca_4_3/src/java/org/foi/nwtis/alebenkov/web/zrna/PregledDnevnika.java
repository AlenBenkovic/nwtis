/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.foi.nwtis.alebenkov.ejb.eb.Dnevnik;
import org.foi.nwtis.alebenkov.ejb.sb.DnevnikFacade;

/**
 *
 * @author abenkovic
 */
@Named(value = "pregledDnevnika")
@SessionScoped
public class PregledDnevnika implements Serializable {

    @EJB
    private DnevnikFacade dnevnikFacade;

    private List<Dnevnik> dnevnik;
    private List<Dnevnik> dnevnikFiltrirano;
    private List<String> datumi;
    private String korisnik;
    private String url;
    private String ip;
    private Date vrijeme;
    private String odDatuma;
    private String doDatuma;
    private Date odDatumaD;
    private Date doDatumaD;
    private int trajanje;
    private int status;

    private boolean filteri = false;
    private boolean filtriraniPodaci = false;

    /**
     * Creates a new instance of PregledDnevnika
     */
    public PregledDnevnika() {

    }

    /**
     *
     * @return
     */
    public List<Dnevnik> getDnevnik() {
        dnevnik = dnevnikFacade.findAll();
        return dnevnik;
    }

    /**
     *
     * @param dnevnik
     */
    public void setDnevnik(List<Dnevnik> dnevnik) {
        this.dnevnik = dnevnik;
    }

    /**
     *
     * @return
     */
    public String getKorisnik() {
        return korisnik;
    }

    /**
     *
     * @param korisnik
     */
    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     * @return
     */
    public Date getVrijeme() {
        return vrijeme;
    }

    /**
     *
     * @param vrijeme
     */
    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    /**
     *
     * @return
     */
    public boolean isFiltriraniPodaci() {
        return filtriraniPodaci;
    }

    /**
     *
     * @param filtriraniPodaci
     */
    public void setFiltriraniPodaci(boolean filtriraniPodaci) {
        this.filtriraniPodaci = filtriraniPodaci;
    }

    /**
     *
     * @return
     */
    public int getTrajanje() {
        return trajanje;
    }

    /**
     *
     * @param trajanje
     */
    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    /**
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public boolean isFilteri() {
        return filteri;
    }

    /**
     *
     * @param filteri
     */
    public void setFilteri(boolean filteri) {
        this.filteri = filteri;
    }

    /**
     * Prikazujem obrazac za filtriranje podataka i ujedno hvatam nove datume i dnevnik
     */
    public void prikaziFiltere() {
        dohvatiDatume();
        getDnevnik();
        filteri = true;
    }

    /**
     *
     */
    public void sakrijFiltere() {
        filteri = false;
    }

    /**
     *
     * @return
     */
    public List<String> getDatumi() {
        return datumi;
    }

    /**
     *
     * @return
     */
    public List<Dnevnik> getDnevnikFiltrirano() {
        return dnevnikFiltrirano;
    }

    /**
     *
     * @param dnevnikFiltrirano
     */
    public void setDnevnikFiltrirano(List<Dnevnik> dnevnikFiltrirano) {
        this.dnevnikFiltrirano = dnevnikFiltrirano;
    }

    /**
     *
     * @param datumi
     */
    public void setDatumi(List<String> datumi) {
        this.datumi = datumi;
    }

    /**
     *
     * @return
     */
    public String getOdDatuma() {
        return odDatuma;
    }

    /**
     *
     * @param odDatuma
     */
    public void setOdDatuma(String odDatuma) {
        this.odDatuma = odDatuma;
    }

    /**
     *
     * @return
     */
    public String getDoDatuma() {
        return doDatuma;
    }

    /**
     *
     * @param doDatuma
     */
    public void setDoDatuma(String doDatuma) {
        this.doDatuma = doDatuma;
    }

    /**
     * Filtriranje samih podataka po zadanim kriterijima 
     */
    public void filtriraj() {
        int b = 0;
        dnevnikFiltrirano = new ArrayList<>(dnevnik);
        System.out.println("OD:" + odDatuma + " DO: " + doDatuma + "Korisnik: " + korisnik + "\nIP adresa: " + ip + "\n Trajanje vece od: " + trajanje + "\nStatus: " + status);
        konvertDatuma();
        for (int i = 0; i < dnevnik.size(); i++) {
            if ((dnevnik.get(i).getVrijeme().equals(odDatumaD) || dnevnik.get(i).getVrijeme().equals(doDatumaD)) || (dnevnik.get(i).getVrijeme().after(odDatumaD) && dnevnik.get(i).getVrijeme().before(doDatumaD))) {
                //ne radi nista   
            } else {
                dnevnikFiltrirano.remove(dnevnik.get(i));
            }
            if (!korisnik.isEmpty()) {
                if (dnevnik.get(i).getKorisnik().equals(korisnik)) {
                    //ne radi nista
                } else {
                    dnevnikFiltrirano.remove(dnevnik.get(i));
                }
            }
            if (!ip.isEmpty()) {
                if (dnevnik.get(i).getIpadresa().equals(ip)) {
                    //ne radi nista
                } else {
                    dnevnikFiltrirano.remove(dnevnik.get(i));
                }
            }
            if(trajanje>0){
                if (dnevnik.get(i).getTrajanje() > trajanje) {
                    //ne radi nista
                } else {
                    dnevnikFiltrirano.remove(dnevnik.get(i));
                }
            }
            if(status>0){
                if (dnevnik.get(i).getStatus() == status) {
                    //ne radi nista
                } else {
                    dnevnikFiltrirano.remove(dnevnik.get(i));
                }
            }

        }

        for (Dnevnik d : dnevnikFiltrirano) {
            System.out.println("FILT: " + d.getVrijeme());
        }
        filtriraniPodaci = true;

    }

    private void dohvatiDatume() {
        datumi = new ArrayList<>();
        for (Dnevnik d : dnevnik) {
            datumi.add(d.getVrijeme().toString());
        }
    }

    private void konvertDatuma() { //najlaksi nacin da si prebacim string u Date, bar meni :D
        for (Dnevnik d : dnevnik) {
            if (d.getVrijeme().toString().compareTo(this.odDatuma) == 0) {
                odDatumaD = d.getVrijeme();
            }
            if (d.getVrijeme().toString().compareTo(this.doDatuma) == 0) {
                doDatumaD = d.getVrijeme();
            }
        }
    }

}
