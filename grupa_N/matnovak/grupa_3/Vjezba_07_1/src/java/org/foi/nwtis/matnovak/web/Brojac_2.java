/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 *
 * @author grupa_3
 */
public class Brojac_2 implements Brojaci{
    private static int brojInstanci = 0;
    private int redniBroj = 0;
    private PropertyChangeSupport  podrskaZaPromjenu = null; 
    
    public Brojac_2() {
        brojInstanci++;
        podrskaZaPromjenu = new PropertyChangeSupport(this);
    }

    @Override
    public void dodajSlusaca(PropertyChangeListener slusac) {
        podrskaZaPromjenu.addPropertyChangeListener(slusac);
    }

    @Override
    public void obrisiSlusaca(PropertyChangeListener slusac) {
        podrskaZaPromjenu.removePropertyChangeListener(slusac);
    }

    @Override
    public void setRedniBroj(int redniBroj) {
        podrskaZaPromjenu.firePropertyChange("redniBroj", this.redniBroj, redniBroj);
        this.redniBroj = redniBroj;
    }

    @Override
    public void run() {
        System.out.println("instanca "+brojInstanci+" Klase:" + this.getClass().getName());
        Random generator  = new Random(System.currentTimeMillis());
        int broj  = generator.nextInt(100);
        setRedniBroj(broj);
    }
    
}
