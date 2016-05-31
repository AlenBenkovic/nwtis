/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.slusaci;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 * Web application lifecycle listener.
 *
 * @author abenkovic
 */
public class SlusacApp implements ServletContextListener {

    static private ServletContext context = null;
    static private Konfiguracija konfigOstalog = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            String konfiguracija = context.getInitParameter("konfiguracija");
            String putanje = context.getRealPath("/WEB-INF") + File.separator;

            this.konfigOstalog = KonfiguracijaApstraktna.preuzmiKonfiguraciju(putanje + konfiguracija);
            System.out.println("Konfiguracija ucitana.");

        } catch (NemaKonfiguracije ex) {
            System.out.println("Greska prilikom preuzimanja konfiguracije");
        }
    }

    public static Konfiguracija getKonfigOstalog() {
        return konfigOstalog;
    }
    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
