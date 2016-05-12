/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author grupa_3
 */
public class SlusacPromjena implements PropertyChangeListener{
    private int brojPromjena = 0;
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        brojPromjena++;
        System.out.println(brojPromjena + ": ");
        System.out.println(evt.getPropertyName() 
                + " OLD: " + evt.getOldValue()
                + " NEW: " + evt.getNewValue());
    }
    
}
