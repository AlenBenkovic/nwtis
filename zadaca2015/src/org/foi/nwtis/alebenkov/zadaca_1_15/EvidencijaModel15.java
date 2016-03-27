/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1_15;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alen
 */
public class EvidencijaModel15 implements Serializable{
    private String oznaka;
    private Date prviZahtjev;
    private Date zadnjiZahtjev;
    private int ukupanBrojZahtjeva = 0;
    private int neuspjesanBrojZahtjeva = 0;
    private long ukupnoVrijemeRada = 0;
    private ArrayList<ZahtjevKorisnika> zahtjevi = new ArrayList();
    
    public EvidencijaModel15(String oznaka) {
        this.oznaka = oznaka;
    }
    
    public synchronized boolean dodajZahtjev(ZahtjevKorisnika zahtjev) {
        //TODO dovrÅ¡iti unos/aÅ¾uriranje podataka zahtjeva
        return true;
    }
    
    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public Date getPrviZahtjev() {
        return prviZahtjev;
    }

    public void setPrviZahtjev(Date prviZahtjev) {
        this.prviZahtjev = prviZahtjev;
    }

    public Date getZadnjiZahtjev() {
        return zadnjiZahtjev;
    }

    public void setZadnjiZahtjev(Date zadnjiZahtjev) {
        this.zadnjiZahtjev = zadnjiZahtjev;
    }

    public int getUkupanBrojZahtjeva() {
        return ukupanBrojZahtjeva;
    }

    public void setUkupanBrojZahtjeva(int ukupanBrojZahtjeva) {
        this.ukupanBrojZahtjeva = ukupanBrojZahtjeva;
    }

    public int getNeuspjesanBrojZahtjeva() {
        return neuspjesanBrojZahtjeva;
    }

    public void setNeuspjesanBrojZahtjeva(int neuspjesanBrojZahtjeva) {
        this.neuspjesanBrojZahtjeva = neuspjesanBrojZahtjeva;
    }

    public long getUkupnoVrijemeRada() {
        return ukupnoVrijemeRada;
    }

    public void setUkupnoVrijemeRada(long ukupnoVrijemeRada) {
        this.ukupnoVrijemeRada = ukupnoVrijemeRada;
    }

    public ArrayList<ZahtjevKorisnika> getZahtjevi() {
        return zahtjevi;
    }

    public void setZahtjevi(ArrayList<ZahtjevKorisnika> zahtjevi) {
        this.zahtjevi = zahtjevi;
    }
    
    public class ZahtjevKorisnika {
        private String vrijeme;
        private String ipAdresa;
        private String zahtjev;
        private String odgovor;

        public ZahtjevKorisnika(String vrijeme, String ipAdresa, String zahtjev, String odgovor) {
            this.vrijeme = vrijeme;
            this.ipAdresa = ipAdresa;
            this.zahtjev = zahtjev;
            this.odgovor = odgovor;
        }
        
        public String getVrijeme() {
            return vrijeme;
        }

        public void setVrijeme(String vrijeme) {
            this.vrijeme = vrijeme;
        }

        public String getIpAdresa() {
            return ipAdresa;
        }

        public void setIpAdresa(String ipAdresa) {
            this.ipAdresa = ipAdresa;
        }

        public String getZahtjev() {
            return zahtjev;
        }

        public void setZahtjev(String zahtjev) {
            this.zahtjev = zahtjev;
        }

        public String getOdgovor() {
            return odgovor;
        }

        public void setOdgovor(String odgovor) {
            this.odgovor = odgovor;
        }
    }
    
    /*
    (oznaka dretve, prvi zahtjev vrijeme, 
    zadnji zahtjev vrijeme, ukupan broj zahtjeva, 
    broj neuspjeÅ¡nih zahtjeva, 
    ukupno vrijeme stvarnog rada 
    (zbroj vremena realizacije svakog zahtjeva), 
    kronoloÅ¡ka lista zahtjeva korisnika 
    (vrijeme, ip adresa, zahtjev, odgovor),...)
    */
}