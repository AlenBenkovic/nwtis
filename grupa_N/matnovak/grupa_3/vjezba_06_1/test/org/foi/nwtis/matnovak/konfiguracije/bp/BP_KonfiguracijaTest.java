/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.konfiguracije.bp;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.matnovak.konfiguracije.Konfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.matnovak.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author grupa_2
 */
public class BP_KonfiguracijaTest {

    Konfiguracija podaci = null;
    String datoteka = "NWTiS_matnovak_test.xml";
    static String SERVER_DATABASE = "server.database";
    static String ADMIN_USERNAME = "admin.username";
    static String ADMIN_PASSWORD = "admin.password";
    static String ADMIN_DATABASE = "admin.database";
    static String USER_USERNAME = "user.username";
    static String USER_PASSWORD = "user.password";
    static String USER_DATABASE = "user.database";
    static String DRIVER_ODBC = "driver.database.odbc";
    static String DRIVER_MYSQL = "driver.database.mysql";
    static String DRIVER_DERBY = "driver.database.derby";
    static String DRIVER_POSTGREE = "driver.database.postgresql";

    public BP_KonfiguracijaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            this.podaci = KonfiguracijaApstraktna.kreirajKonfiguraciju(datoteka);
            podaci = KonfiguracijaApstraktna.kreirajKonfiguraciju(datoteka);
            podaci.spremiPostavku(SERVER_DATABASE, "jdbc:mysql://192.168.15.1/");
            podaci.spremiPostavku(ADMIN_USERNAME, "nwtis_admin");
            podaci.spremiPostavku(ADMIN_PASSWORD, "nwtis_foi");
            podaci.spremiPostavku(ADMIN_DATABASE, "mysql");
            podaci.spremiPostavku(USER_USERNAME, "nwtis_g1");
            podaci.spremiPostavku(USER_PASSWORD, "g1_nwtis");
            podaci.spremiPostavku(USER_DATABASE, "nwtis_g1");
            podaci.spremiPostavku(DRIVER_ODBC, "sun.jdbc.odbc.JdbcOdbcDriver");
            podaci.spremiPostavku(DRIVER_MYSQL, "com.mysql.jdbc.Drive");
            podaci.spremiPostavku(DRIVER_DERBY, "org.apache.derby.jdbc.ClientDriver");
            podaci.spremiPostavku(DRIVER_POSTGREE, "org.postgresql.Driver");
            podaci.spremiKonfiguraciju();
            //podaci.obrisiPostavku(DRIVER_MYSQL);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @After
    public void tearDown() {
        podaci.obrisiSvePostavke();
        podaci = null;
        File f = new File(datoteka);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Test of getAdminDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetAdminDatabase() {
        System.out.println("getAdminDatabase");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(ADMIN_DATABASE);
        String result = instance.getAdminDatabase();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdminPassword method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetAdminPassword() {
        System.out.println("getAdminPassword");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(ADMIN_PASSWORD);
        String result = instance.getAdminPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdminUsername method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetAdminUsername() {
        System.out.println("getAdminUsername");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(ADMIN_USERNAME);
        String result = instance.getAdminUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDriverDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetDriversDatabase() {
        System.out.println("getDriverDatabase");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        for (Object kljuc : this.podaci.dajSvePostavke().keySet()) {
            String k = (String) kljuc;
            if(k.startsWith("driver.database.")){
                Properties result = instance.getDriversDatabase();
                if(!result.containsKey(k) || !result.containsValue(this.podaci.dajPostavku(k)))
                {
                    fail("fali neki driver! " + k);
                }
            }
        }
        
    }

    /**
     * Test of getDriverDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetDriverDatabase_String() {
        System.out.println("getDriverDatabase");
        String bp_url = "";
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(DRIVER_MYSQL);
        String result = instance.getDriverDatabase(podaci.dajPostavku(SERVER_DATABASE));
        assertEquals(expResult, result);
//        expResult = podaci.dajPostavku(DRIVER_POSTGREE);
//        result = instance.getDriverDatabase(podaci.dajPostavku(SERVER_DATABASE));
//        assertEquals(expResult, result);
//        expResult = podaci.dajPostavku(DRIVER_ODBC);
//        result = instance.getDriverDatabase(podaci.dajPostavku(SERVER_DATABASE));
//        assertEquals(expResult, result);
//        expResult = podaci.dajPostavku(DRIVER_DERBY);
//        result = instance.getDriverDatabase(podaci.dajPostavku(SERVER_DATABASE));
//        assertEquals(expResult, result);
    }

    /**
     * Test of getDriversDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetDriverDatabase_0args() {
        System.out.println("getDriversDatabase");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(DRIVER_MYSQL);
        String result = instance.getDriverDatabase();
        assertEquals(expResult, result);
    }

    /**
     * Test of getServerDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetServerDatabase() {
        System.out.println("getServerDatabase");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(SERVER_DATABASE);
        String result = instance.getServerDatabase();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetUserDatabase() {
        System.out.println("getUserDatabase");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(USER_DATABASE);
        String result = instance.getUserDatabase();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserPassword method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetUserPassword() {
        System.out.println("getUserPassword");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(USER_PASSWORD);
        String result = instance.getUserPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserUsername method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetUserUsername() {
        System.out.println("getUserUsername");
        BP_Konfiguracija instance = null;
        try {
            instance = new BP_Konfiguracija(datoteka);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String expResult = podaci.dajPostavku(USER_USERNAME);
        String result = instance.getUserUsername();
        assertEquals(expResult, result);
    }

}
