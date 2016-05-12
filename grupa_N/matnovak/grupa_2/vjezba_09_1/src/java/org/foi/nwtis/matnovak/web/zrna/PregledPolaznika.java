/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.matnovak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.matnovak.web.Polaznik;

/**
 *
 * @author grupa_2
 */
@Named(value = "pregledPolaznika")
@RequestScoped
public class PregledPolaznika {

    private ArrayList<Polaznik> polaznici;
    
    /**
     * Creates a new instance of PregledPolaznika
     */
    public PregledPolaznika() {
    }

    public ArrayList<Polaznik> getPolaznici() {
        BP_Konfiguracija bp_konfig = null;
        String url = bp_konfig.getServerDatabase() + bp_konfig.getUserDatabase();
        String user = bp_konfig.getUserUsername();
        String password = bp_konfig.getUserPassword();
        try {
            Class.forName(bp_konfig.getDriverDatabase());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledPolaznika.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection c = DriverManager.getConnection(url, user, password);
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM polaznici");
            polaznici = new ArrayList<Polaznik>();
            while(rs.next()) {
                Polaznik p = new Polaznik();
                p.setKor_ime(rs.getString("kor_ime"));
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                
                polaznici.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PregledPolaznika.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return polaznici;
    }
    
}
