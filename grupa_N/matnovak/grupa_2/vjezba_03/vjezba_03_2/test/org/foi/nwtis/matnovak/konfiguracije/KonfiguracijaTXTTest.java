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

/**
 *
 * @author grupa_2
 */
public class KonfiguracijaTXTTest {
    Konfiguracija podaci = null;
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
            podaci.spremiPostavku("1", "Mato");
            podaci.spremiPostavku("2", "Iva");
            podaci.spremiPostavku("3", "Marko");
            podaci.spremiPostavku("4", "Mirta");
            podaci.spremiPostavku("5", "Ivo");
            podaci.spremiPostavku("6", "Marta");
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
        System.out.println(broj1 + " = "+ broj2);
        assertEquals(broj1, broj2);
    }

    /**
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTXT.
     */
    @Test
    public void testUcitajKonfiguraciju_String() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        KonfiguracijaTXT instance = new KonfiguracijaTXT(datoteka);
        instance.ucitajKonfiguraciju(datoteka);
        int broj1 = instance.dajSvePostavke().size();
        int broj2 = podaci.dajSvePostavke().size();
        assertEquals(broj1, broj2);
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
