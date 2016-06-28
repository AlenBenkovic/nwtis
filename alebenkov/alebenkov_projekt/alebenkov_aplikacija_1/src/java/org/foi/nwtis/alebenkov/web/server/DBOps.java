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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class DBOps {

    private BP_konfiguracija bpConfig = null;
    private Konfiguracija konfig = null;
    private String url = null;
    private String korisnik = null;
    private String lozinka = null;
    private Connection connection = null;
    private Statement statemant = null;
    private ResultSet rs = null;
    private String sql = null;
    private boolean sqlExe;
    private int sqlUp;

    public DBOps() {
        this.bpConfig = SlusacAplikacije.getBpConfig();
        this.konfig = SlusacAplikacije.getServerConfig();
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
        int[] korisnik = {0, 0};

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

    public boolean dodajKorisnika(String newUser, String newPass, String newRole) {
        int role = 2;
        if (newRole.contains("ADMIN")) {
            role = 1;
        }
        int brojRedaka = 0;

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM alebenkov_korisnici where user = '" + newUser + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                brojRedaka++; //ovo se moze rjesiti na elegantniji nacin ali ovo mi je trenutno najbrzi (getRow, beforeFirst,..) ali je potrebno podici odredjene zastavice na resultsetu
            }
            if (brojRedaka == 0) {

                sql = "INSERT INTO alebenkov_korisnici(user, pass, role, rang) VALUES ('" + newUser + "','" + newPass + "','" + role + "', 1 )";
                sqlExe = statemant.execute(sql);
                System.out.println("SERVER | Korisnik dodan u bazu.");
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

    public String povecajRang(String user) {
        String status = "";
        int brojRedaka = 0;
        int trenutniRang = 0;

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM alebenkov_korisnici where user = '" + user + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                brojRedaka++; //ovo se moze rjesiti na elegantniji nacin ali ovo mi je trenutno najbrzi (getRow, beforeFirst,..) ali je potrebno podici odredjene zastavice na resultsetu
                trenutniRang = rs.getInt("rang");
            }
            if (trenutniRang == 0) {
                status = "ERR 35";
            } else if (trenutniRang > 4) {
                status = "ERR 34";
            } else {
                int noviRang = trenutniRang + 1;
                sql = "UPDATE alebenkov_korisnici SET rang='" + noviRang + "' WHERE user = '" + user + "'";
                sqlUp = statemant.executeUpdate(sql);
                System.out.println("SERVER | Rang korisnika povecan.");
                status = "OK 10";
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
        return status;
    }

    public String smanjiRang(String user) {
        String status = "";
        int brojRedaka = 0;
        int trenutniRang = 0;

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM alebenkov_korisnici where user = '" + user + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                brojRedaka++; //ovo se moze rjesiti na elegantniji nacin ali ovo mi je trenutno najbrzi (getRow, beforeFirst,..) ali je potrebno podici odredjene zastavice na resultsetu
                trenutniRang = rs.getInt("rang");
            }
            if (trenutniRang == 0) {
                status = "ERR 35";
            } else if (trenutniRang < 2) {
                status = "ERR 34";
            } else {
                int noviRang = trenutniRang - 1;
                sql = "UPDATE alebenkov_korisnici SET rang='" + noviRang + "' WHERE user = '" + user + "'";
                sqlUp = statemant.executeUpdate(sql);
                System.out.println("SERVER | Rang korisnika smanjen.");
                status = "OK 10";
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

        return status;
    }

    public int[] statistikaKorisnika() {

        int[] statistika = {0, 0, 0};

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM alebenkov_korisnici";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                statistika[0]++;
                if (rs.getInt("role") == 1) {
                    statistika[1]++;
                } else {
                    statistika[2]++;
                }
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

        return statistika;

    }

    public boolean dnevnik(String user, String naredba, String odgovor) {

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "INSERT INTO alebenkov_dnevnik(user, naredba, odgovor) VALUES ('" + user + "','" + naredba + "','" + odgovor + "')";
            sqlExe = statemant.execute(sql);
            System.out.println("SERVER | Zapis dodan u dnevnik.");
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

    public boolean provjeraKvote(String user) {
        int rang = 0;
        int kvota = 0;
        int brojUpita = 0;

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            //uzimam rang zadanog korisnika
            sql = "SELECT * FROM alebenkov_korisnici where user = '" + user + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                rang = rs.getInt("rang");
                break;
            }

            //uzimam kvotu za dobiveni rang
            kvota = Integer.parseInt(konfig.dajPostavku("kvota" + rang));

            //uzimam trenutno vrijeme i smanjujem ga za zadani interval korisnika
            int intervalKorisnika = Integer.parseInt(konfig.dajPostavku("intervalKorisnika")) * 1000;
            long trenutnoVrijeme = System.currentTimeMillis();
            long granicaVremena = trenutnoVrijeme - intervalKorisnika;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss.S");
            String granica = sdf.format(granicaVremena);

            //nakon sto imam krajnju vremensku granicu radim upit nad dnevnikom da vidim koliko je korisnik do sada napravio upita u tom razdoblju
            sql = "SELECT * FROM alebenkov_dnevnik where user = '" + user + "' AND time > '" + granica + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                brojUpita++;
            }
            
            System.out.println("BROJ UPITA: " + brojUpita + " KVOTA: " + kvota + " GRANICA: " + granica);
            
            //ukoliko nije nadmasio kvotu saljem true
            if(brojUpita<kvota){
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

}
