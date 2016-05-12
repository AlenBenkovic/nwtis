/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.bp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.matnovak.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author grupa_2
 */
public class Vjezba_06_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Krivi broj argumenata!");
            return;
        }
        BP_Konfiguracija bp_konfig = null;
        try {
            bp_konfig = new BP_Konfiguracija(args[0]);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = bp_konfig.getServerDatabase() + bp_konfig.getUserDatabase();
        String user = bp_konfig.getUserUsername();
        String password = bp_konfig.getUserPassword();
        try {
            Class.forName(bp_konfig.getDriverDatabase());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection c = DriverManager.getConnection(url, user, password);
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT ime, prezime FROM polaznici");
            while(rs.next()) {
                System.out.println(rs.getString("ime") + " " + rs.getString("prezime"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
