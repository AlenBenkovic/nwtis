/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.web;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 *
 * @author grupa_1
 */
public class Brojac_2 implements Brojaci {

    private static int brojInstanci = 0;
    private int redniBroj = 0;

    private PropertyChangeSupport podrskaZaPromjenu;

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
        int stari = this.redniBroj;
        this.redniBroj = redniBroj;
        podrskaZaPromjenu.firePropertyChange("redniBroj", stari, redniBroj);
    }

    @Override
    public void run() {
        System.out.println("Klasa: " + this.getClass().getName());
        Random random = new Random(System.currentTimeMillis());
        int noviBroj = random.nextInt(100);
        this.setRedniBroj(noviBroj);
    }

}
