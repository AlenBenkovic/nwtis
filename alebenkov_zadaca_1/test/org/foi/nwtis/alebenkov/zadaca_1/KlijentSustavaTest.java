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
public class KlijentSustavaTest {

    public KlijentSustavaTest() {
    }

 
    /**
     * Test of provjeraParametara method, of class KlijentSustava.
     */
    @Test
    public void testProvjeraParametara() {
        System.out.println("provjeraParametara");
 
        try {
            String p = "-user -s 127.0.0.1 -port 8000 -u lala -x 1 -y 1";
            KlijentSustava instance = new KlijentSustava(p);
            Matcher result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-user -s 127.0.0.1 -port 8000 -u lala -stat";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            p = "-user -s 127.0.0.1 -port 8000 -u lala";
            result = instance.provjeraParametara(p);
            assertNotNull(result);
            
            
        } catch (Exception ex) {
            Logger.getLogger(KlijentSustavaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
