/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.web;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author grupa_1
 */
public class SlusacPromjena implements PropertyChangeListener {
    private int brojPromjena = 0;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        brojPromjena++;
        System.out.println("Broj promjene: " + brojPromjena);
        System.out.println("Varijabla: " + evt.getPropertyName());
        System.out.println("Stara vrijdnost: " + evt.getOldValue());
        System.out.println("Nova vrijdnost: " + evt.getNewValue());
    }
    
    
}
