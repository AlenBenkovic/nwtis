/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.bp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dkermek.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author grupa_1
 */
public class Vjezba_06_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Nema dovoljno argumenata");
            return;
        }

        BP_Konfiguracija konfig = null;
        try {
            konfig = new BP_Konfiguracija(args[0]);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (konfig == null) {
            System.out.println("Pogreška u konfiguraciji");
            return;
        }
        String url = konfig.getServerDatabase() + konfig.getUserDatabase();
        String query = "select kor_ime, prezime, ime from POLAZNICI";

        try {
            Class.forName(konfig.getDriverDatabase());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        try (
                Connection con = DriverManager.getConnection(url,
                        konfig.getUserUsername(), konfig.getUserPassword());
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            System.out.println("Popis polaznika:");
            while (rs.next()) {
                String mb = rs.getString(1);
                String pr = rs.getString(2);
                String im = rs.getString(3);
                System.out.println(mb + " " + pr + " " + im);
            }
        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
}