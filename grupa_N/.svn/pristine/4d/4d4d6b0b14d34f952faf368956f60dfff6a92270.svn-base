/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.konfiguracije;

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
 * @author grupa_1
 */
public class KonfiguracijaTxtTest {

    Konfiguracija podaci = null;
    String datoteka = "NWTiS_dkermek.txt";

    public KonfiguracijaTxtTest() {
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
            podaci.spremiPostavku("1", "Pero");
            podaci.spremiPostavku("2", "Mato");
            podaci.spremiPostavku("3", "Ivo");
            podaci.spremiPostavku("4", "Jozo");
            podaci.spremiPostavku("5", "Bara");
            podaci.spremiPostavku("6", "Mara");
            podaci.spremiPostavku("7", "Dara");
            podaci.spremiPostavku("8", "Sara");
            podaci.spremiKonfiguraciju();
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(KonfiguracijaTxtTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    @Ignore
    public void tearDown() {
        podaci.obrisiSvePostavke();
        podaci = null;
        File f = new File(datoteka);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    public void testUcitajKonfiguraciju() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        KonfiguracijaTxt instance = new KonfiguracijaTxt(datoteka);
        instance.ucitajKonfiguraciju();
        int broj1 = instance.dajSvePostavke().size();
        int broj2 = podaci.dajSvePostavke().size();
        System.out.println(broj1 + " " + broj2);
        assertEquals(broj1, broj2);
    }

    /**
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    @Ignore
    public void testUcitajKonfiguraciju_String() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        String datoteka = "";
        KonfiguracijaTxt instance = null;
        instance.ucitajKonfiguraciju(datoteka);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spremiKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    @Ignore
    public void testSpremiKonfiguraciju() throws Exception {
        System.out.println("spremiKonfiguraciju");
        KonfiguracijaTxt instance = null;
        instance.spremiKonfiguraciju();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spremiKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    @Ignore
    public void testSpremiKonfiguraciju_String() throws Exception {
        System.out.println("spremiKonfiguraciju");
        String datoteka = "";
        KonfiguracijaTxt instance = null;
        instance.spremiKonfiguraciju(datoteka);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
