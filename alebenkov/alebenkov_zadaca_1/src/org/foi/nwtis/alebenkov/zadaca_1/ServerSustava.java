/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alen
 */
public class ServerSustava {

    protected String parametri;
    protected Matcher mParametri;
    
   

    public ServerSustava(String parametri) throws Exception {
        this.parametri = parametri;
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri servera ne odgovaraju!");
        }
    }

    public Matcher provjeraParametara(String p) {
        String sintaksa = "^-server -konf +([^\\s]+.(xml|txt))( +-load)?$";

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

    public void pokreniServer() {
        //TODO pokreni server
        System.out.println("Pokrecem server!");
    }

    private void ucitajSerijaliziranuEvidenciju(String datEvid) {
        //TODO napravite sami
    }


}
