/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.podaci.Lokacija;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class OperacijeBP {

    private BP_konfiguracija bpConfig = null;
    private String url = null;
    private String korisnik = null;
    private String lozinka = null;
    private Connection connection = null;
    private Statement statemant = null;
    private ResultSet rs = null;
    private String sql = null;
    private boolean sqlExe;

    public OperacijeBP() {
        this.bpConfig = SlusacAplikacije.getKonfigBP();
        this.url = bpConfig.getServerDatabase() + bpConfig.getUserDatabase();
        this.korisnik = bpConfig.getUserDatabase();
        this.lozinka = bpConfig.getUserPassword();

        try {
            Class.forName(bpConfig.getDriverDatabase()); //dovoljno pozvati jednom na razini projekta da bi se ucitao sam driver
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR | Greska kod ucitavanja drivera: " + ex.getMessage());
        }
    }

    public boolean upisAdreseBP(String adresa, String lat, String lon) {
        
        int brojRedaka = 0;

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM adrese where adresa = '" + adresa + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                brojRedaka++; //ovo se moze rjesiti na elegantniji nacin ali ovo mi je trenutno najbrzi (getRow, beforeFirst,..) ali je potrebno podici odredjene zastavice na resultsetu
            }

            if (brojRedaka == 0) {

                sql = "INSERT INTO adrese(adresa, latitude, longitude) VALUES ('" + adresa + "','" + lat + "','" + lon + "')";
                sqlExe = statemant.execute(sql);
                System.out.println("|| Zapis spremljen u bazu");
                return true;

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
        return false;
    }

    public List<Adresa> ucitajAdrese() {
        List<Adresa> adrese = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM adrese";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                Lokacija l = new Lokacija(rs.getString("latitude"), rs.getString("longitude"));
                Adresa a = new Adresa(rs.getInt("idadresa"), rs.getString("adresa"), l);
                adrese.add(a);
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
        return adrese;
    }

    public boolean spremiMeteo(Adresa a, MeteoPodaci mp) {
        String adresaStanice = mp.getCountry() + ", " + mp.getName();
        int idAdresa = a.getIdadresa();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Timestamp ts = new java.sql.Timestamp(utilDate.getTime());
        sql = "INSERT INTO meteo(idadresa, adresastanice, latitude, longitude, vrijeme, vrijemeopis, temp, tempmin, tempmax, vlaga, tlak, vjetar, vjetarsmjer, preuzeto) "
                + "VALUES ("
                + idAdresa + ",'"
                + adresaStanice + "','"
                + a.getGeoloc().getLatitude() + "','"
                + a.getGeoloc().getLongitude() + "','"
                + mp.getWeatherMain() + "','"
                + mp.getWeatherValue() + "',"
                + mp.getTemperatureValue() + ","
                + mp.getTemperatureMin() + ","
                + mp.getTemperatureMax() + ","
                + mp.getHumidityValue() + ","
                + mp.getPressureValue() + ","
                + mp.getWindSpeedValue() + ","
                + mp.getWindDirectionValue() + ",'"
                + ts + "')";
        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();
            sqlExe = statemant.execute(sql);
            return true;
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
        return false;
    }

}
