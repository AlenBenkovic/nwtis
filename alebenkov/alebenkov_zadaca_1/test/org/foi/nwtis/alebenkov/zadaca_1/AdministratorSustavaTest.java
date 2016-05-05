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
public class AdministratorSustavaTest {

    public AdministratorSustavaTest() {
    }

    /**
     * Test of provjeraParametara method, of class AdministratorSustava.
     */
    @Test
    public void testProvjeraParametara() {
        try {
            System.out.println("provjeraParametara");
            
            String p = "-admin -s localhost -port 8000 -u lala -p 1213 -new";
            AdministratorSustava instance = new AdministratorSustava(p);
            Matcher result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-admin -s localhost -port 8000 -u lala -p 1213 -pause";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-admin -s localhost -port 8000 -u lala -p 1213 -start";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-admin -s localhost -port 8000 -u lala -p 1213 -stat";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
        } catch (Exception ex) {
            Logger.getLogger(AdministratorSustavaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
