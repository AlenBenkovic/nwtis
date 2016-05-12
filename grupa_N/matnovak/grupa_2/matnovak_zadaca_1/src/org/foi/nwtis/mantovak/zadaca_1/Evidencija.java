/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mantovak.zadaca_1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author grupa_2
 */
public class Evidencija implements Serializable {
    public ArrayList<String> igraci = new ArrayList<String>();
    public ArrayList<Brod> brodovi = new ArrayList<Brod>();
    public ArrayList<Potez> potezi = new ArrayList<Potez>();
}
