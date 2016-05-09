/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.bp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;

/**
 *
 * @author abenkovic
 */
public class Vjezba_06_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Pogresan broj arguemenata!");
            return;
        }
        
        Vjezba_06_2 vj = new Vjezba_06_2();
        vj.ispisiPodatke(args[0]);

        
    }
    
    public void ispisiPodatke(String datoteka){
        BP_konfiguracija bp = new BP_konfiguracija(datoteka);
        if (bp.getStatus()) {
            System.out.println(bp.getDriverDatabase());
            String connUrl = bp.getServerDatabase() + bp.getUserDatabase();
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            String sql = "SELECT ime, prezime FROM polaznici";

            try {
                Class.forName(bp.getDriverDatabase()); //dovoljno pozvati jednom na razini projekta da bi se ucitao sam driver
            } catch (ClassNotFoundException ex) {
                System.out.println("Greska kod ucitavanja drivera: " + ex.getMessage());
                return;
            }
            try {
                conn = DriverManager.getConnection(connUrl, bp.getUserUsername(), bp.getUserPassword());
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");

                    System.out.println(ime + " " +prezime);
                }

            } catch (SQLException ex) {
                System.out.println("Greska u radu s bazom: " + ex.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

}
