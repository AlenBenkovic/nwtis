/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alen
 */
public class PregledSustava {
    protected String parametri;
    protected Matcher mParametri;

    public PregledSustava(String parametri) throws Exception {
        this.parametri = parametri;
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri ne odgovaraju!");
        }
    }

    public Matcher provjeraParametara(String p) {
        String sintaksa = "^-show -s ([^\\s]+)$";

        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            System.out.println("Ne odgovara!");
            return null;
        }
    }

    public void pokreniPreglednik() {
        String datoteka = mParametri.group(1);
        File dat = new File(datoteka);
        if (!dat.exists()) {
            System.out.println("Datoteka konfiguracije ne postoji.");
            return;
        }
        //TODO sami dovrÅ¡iti
        return;
    }
}