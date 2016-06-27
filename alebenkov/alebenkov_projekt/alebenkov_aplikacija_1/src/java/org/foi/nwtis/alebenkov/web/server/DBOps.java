/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Connection connection = null;
    private Statement statemant = null;
    private ResultSet rs = null;
    private String sql = null;

    public DBOps() {
        this.bpConfig = SlusacAplikacije.getBpConfig();
        this.url = bpConfig.getServerDatabase() + bpConfig.getUserDatabase();
        this.korisnik = bpConfig.getUserUsername();
        this.lozinka = bpConfig.getUserPassword();

        try {
            Class.forName(bpConfig.getDriverDatabase()); //dovoljno pozvati jednom na razini projekta da bi se ucitao sam driver
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR | Greska kod ucitavanja drivera: " + ex.getMessage());
        }
    }

    public int[] provjeraKorisnika(String user, String pass) {
        int[] korisnik = {0,0};

        try {
            connection = DriverManager.getConnection(url, this.korisnik, this.lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM alebenkov_korisnici where user = '" + user + "' AND pass = '" + pass + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                korisnik[0] = rs.getInt("role");
                korisnik[1] = rs.getInt("rang");
            }

        } catch (SQLException ex) {
            System.out.println("ERROR | Greska u radu s bazom: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (statemant != null) {
                try {
                    statemant.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return korisnik;
    }

}
