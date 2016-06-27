/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class DBOps {

    private BP_konfiguracija bpConfig = null;
    private String url = null;
    private String korisnik = null;
    private String lozinka = null;

    public DBOps() {
        this.bpConfig = SlusacAplikacije.getBpConfig();
        this.url = bpConfig.getServerDatabase() + bpConfig.getUserDatabase();
        this.korisnik = bpConfig.getUserDatabase();
        this.lozinka = bpConfig.getUserPassword();

        try {
            Class.forName(bpConfig.getDriverDatabase()); //dovoljno pozvati jednom na razini projekta da bi se ucitao sam driver
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR | Greska kod ucitavanja drivera: " + ex.getMessage());
        }
    }

    public int provjeraKorisnika(String user, String pass) {
        int korisnik = 2;

        return korisnik;
    }

}
