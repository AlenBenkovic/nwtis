/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.konfiguracije.bp;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkermek.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkermek.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author grupa_1
 */
public class BP_KonfiguracijaTest {

    Konfiguracija konfiguracija;
    String datoteka = "NWTiS_test.txt";

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
            konfiguracija = KonfiguracijaApstraktna.kreirajKonfiguraciju(datoteka);
            konfiguracija.spremiPostavku("server.database", "jdbc:mysql://192.168.15.1/");
            konfiguracija.spremiPostavku("admin.username", ">nwtis_admin");
            konfiguracija.spremiPostavku("admin.password", "nwtis_foi");
            konfiguracija.spremiPostavku("admin.database", "mysql");
            konfiguracija.spremiPostavku("user.username", "nwtis_g1");
            konfiguracija.spremiPostavku("user.password", "g1_nwtis");
            konfiguracija.spremiPostavku("user.database", "nwtis_g1");
            konfiguracija.spremiPostavku("driver.database.odbc", "sun.jdbc.odbc.JdbcOdbcDriver");
            konfiguracija.spremiPostavku("driver.database.mysql", "com.mysql.jdbc.Drive");
            konfiguracija.spremiPostavku("driver.database.derby", "org.apache.derby.jdbc.ClientDriver");
            konfiguracija.spremiPostavku("driver.database.postgresql", "org.postgresql.Driver");
            konfiguracija.spremiKonfiguraciju();
// ako se želi da test padne
//          konfiguracija.obrisiPostavku("admin.username");
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        konfiguracija.obrisiSvePostavke();
        konfiguracija = null;
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
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("admin.database");
            String result = instance.getAdminDatabase();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getAdminPassword method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetAdminPassword() {
        System.out.println("getAdminPassword");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("admin.password");
            String result = instance.getAdminPassword();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getAdminUsername method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetAdminUsername() {
        System.out.println("getAdminUsername");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("admin.username");
            String result = instance.getAdminUsername();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getDriverDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetDriverDatabase_0args() {
        System.out.println("getDriverDatabase");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String url[] = konfiguracija.dajPostavku("server.database").split(":");
            String expResult = konfiguracija.dajPostavku("driver.database." + url[1]);
            String result = instance.getDriverDatabase();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getDriverDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetDriverDatabase_String() {
        System.out.println("getDriverDatabase");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String url[] = konfiguracija.dajPostavku("server.database").split(":");
            String expResult = konfiguracija.dajPostavku("driver.database." + url[1]);            
            String result = instance.getDriverDatabase(konfiguracija.dajPostavku("server.database"));
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getDriversDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetDriversDatabase() {
        System.out.println("getDriversDatabase");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            Properties expResult = new Properties();
            for (Enumeration e = konfiguracija.dajPostavke(); e.hasMoreElements();) {
                String k = (String) e.nextElement();
                if (k.startsWith("driver.database.")) {
                    expResult.setProperty(k, konfiguracija.dajPostavku(k));
                }
            }
            Properties result = instance.getDriversDatabase();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getServerDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetServerDatabase() {
        System.out.println("getServerDatabase");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("server.database");
            String result = instance.getServerDatabase();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getUserDatabase method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetUserDatabase() {
        System.out.println("getUserDatabase");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("user.database");
            String result = instance.getUserDatabase();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getUserPassword method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetUserPassword() {
        System.out.println("getUserPassword");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("user.password");
            String result = instance.getUserPassword();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getUserUsername method, of class BP_Konfiguracija.
     */
    @Test
    public void testGetUserUsername() {
        System.out.println("getUserUsername");
        try {
            BP_Konfiguracija instance = new BP_Konfiguracija(datoteka);
            String expResult = konfiguracija.dajPostavku("user.username");
            String result = instance.getUserUsername();
            assertEquals(expResult, result);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(BP_KonfiguracijaTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

}
