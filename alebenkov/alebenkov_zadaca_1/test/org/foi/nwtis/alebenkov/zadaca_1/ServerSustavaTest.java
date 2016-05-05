/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abenkovic
 */
public class ServerSustavaTest {

    public ServerSustavaTest() {
    }

    /**
     * Test of provjeraPauziran method, of class ServerSustava.
     */
    @Test
    public void testProvjeraPauziran() {
        System.out.println("provjeraPauziran");
        boolean expResult = false;
        boolean result = ServerSustava.provjeraPauziran();
        assertEquals(expResult, result);

    }

    /**
     * Test of SetPauziraj method, of class ServerSustava.
     */
    @Test
    public void testSetPauziraj() {
        System.out.println("SetPauziraj");
        boolean pauziran = false;
        boolean expResult = false;
        boolean result = ServerSustava.provjeraPauziran();
        ServerSustava.SetPauziraj(pauziran);
        assertEquals(expResult, result);

    }

    /**
     * Test of provjeraParametara method, of class ServerSustava.
     */
    @Test
    public void testProvjeraParametara() {
        System.out.println("provjeraParametara");
    

        try {
            String p = "-server -konf NWTiS_alebenkov_zadaca_1.txt";
            ServerSustava instance = new ServerSustava(p);
            Matcher result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-server -konf NWTiS_alebenkov_zadaca_1.txt";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-server -konf NWTiS_alebenkov_zadaca_1.xml";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-server -konf NWTiS_alebenkov_zadaca_1.xml -load";
            result = instance.provjeraParametara(p);
            assertNotNull(result);

           

        } catch (Exception ex) {
            Logger.getLogger(KlijentSustavaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //fail("The test case is a prototype.");
    }

}
