/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.podaci;

/**
 *
 * @author alebenkov
 */
public class JMSporuka {
    private long pocetakRada;
    private long krajRada;
    private int ukupnoPoruka;
    private int uspjesnoPoruka;
    private int neuspjesnoPoruka;
    private int neispravnePoruke;

    public JMSporuka(long pocetakRada, long krajRada, int ukupnoPoruka, int uspjesnoPoruka, int neuspjesnoPoruka, int neispravnePoruke) {
        this.pocetakRada = pocetakRada;
        this.krajRada = krajRada;
        this.ukupnoPoruka = ukupnoPoruka;
        this.uspjesnoPoruka = uspjesnoPoruka;
        this.neuspjesnoPoruka = neuspjesnoPoruka;
        this.neispravnePoruke = neispravnePoruke;
    }

    public long getPocetakRada() {
        return pocetakRada;
    }

    public void setPocetakRada(long pocetakRada) {
        this.pocetakRada = pocetakRada;
    }

    public long getKrajRada() {
        return krajRada;
    }

    public void setKrajRada(long krajRada) {
        this.krajRada = krajRada;
    }

    public int getUkupnoPoruka() {
        return ukupnoPoruka;
    }

    public void setUkupnoPoruka(int ukupnoPoruka) {
        this.ukupnoPoruka = ukupnoPoruka;
    }

    public int getUspjesnoPoruka() {
        return uspjesnoPoruka;
    }

    public void setUspjesnoPoruka(int uspjesnoPoruka) {
        this.uspjesnoPoruka = uspjesnoPoruka;
    }

    public int getNeuspjesnoPoruka() {
        return neuspjesnoPoruka;
    }

    public void setNeuspjesnoPoruka(int neuspjesnoPoruka) {
        this.neuspjesnoPoruka = neuspjesnoPoruka;
    }

    public int getNeispravnePoruke() {
        return neispravnePoruke;
    }

    public void setNeispravnePoruke(int neispravnePoruke) {
        this.neispravnePoruke = neispravnePoruke;
    }
    
    
    
}
