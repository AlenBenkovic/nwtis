/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.konfiguracije;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author grupa_3
 */
public class KonfiguracijaTXTTest {
    Konfiguracija podaci  =null;
    String datoteka = "NWTiS_matnovak.txt";
    
    public KonfiguracijaTXTTest() {
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
            podaci = KonfiguracijaApstraktna.kreirajKonfiguraciju(datoteka);
            podaci.spremiPostavku("1", "Mara");
            podaci.spremiPostavku("2", "Bara");
            podaci.spremiPostavku("3", "Sara");
            podaci.spremiPostavku("4", "Tara");
            podaci.spremiKonfiguraciju();
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(KonfiguracijaTXTTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
        podaci.obrisiSvePostavke();
        podaci = null;
        File f = new File(datoteka);
        if(f.exists()){
            f.delete();
        }
    }

    /**
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTXT.
     */
    @Test
    public void testUcitajKonfiguraciju() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        KonfiguracijaTXT instance = new KonfiguracijaTXT(datoteka);
        instance.ucitajKonfiguraciju();
        int broj1 = instance.dajSvePostavke().size();
        int broj2 = podaci.dajSvePostavke().size();
        System.out.println(broj1 + " = " + broj2);
        assertEquals(broj1, broj2);
    }

    /**
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTXT.
     */
    @Test
    @Ignore
    public void testUcitajKonfiguraciju_String() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        String datoteka = "";
        KonfiguracijaTXT instance = null;
        instance.ucitajKonfiguraciju(datoteka);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spremiKonfiguraciju method, of class KonfiguracijaTXT.
     */
    @Test
    public void testSpremiKonfiguraciju() throws Exception {
        System.out.println("spremiKonfiguraciju");
        KonfiguracijaTXT instance = null;
        instance.spremiKonfiguraciju();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spremiKonfiguraciju method, of class KonfiguracijaTXT.
     */
    @Test
    public void testSpremiKonfiguraciju_String() throws Exception {
        System.out.println("spremiKonfiguraciju");
        String datoteka = "";
        KonfiguracijaTXT instance = null;
        instance.spremiKonfiguraciju(datoteka);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
