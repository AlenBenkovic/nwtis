/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.web.PreuzmiMeteoPodatke;

/**
 * Web application lifecycle listener.
 *
 * @author abenkovic
 */
public class SlusacAplikacije implements ServletContextListener {

    static private ServletContext context = null;
    static private Konfiguracija konfigOstalog = null;
    static private BP_konfiguracija konfigBP = null;
    private PreuzmiMeteoPodatke pmp;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            String konfiguracija = context.getInitParameter("konfiguracija");
            String putanje = context.getRealPath("/WEB-INF") + File.separator;

            SlusacAplikacije.konfigBP = new BP_konfiguracija(putanje + konfiguracija);
            SlusacAplikacije.konfigOstalog = KonfiguracijaApstraktna.preuzmiKonfiguraciju(putanje + konfiguracija);

            if (konfigBP.getStatus()) {

                System.out.println("Ucitana konfiguracija.");
                pmp = new PreuzmiMeteoPodatke();
                pmp.start();

            }
        } catch (NemaKonfiguracije ex) {
            System.out.println("Greska prilikom preuzimanja konfiguracije");
        }

    }

    public static Konfiguracija getKonfigOstalog() {
        return konfigOstalog;
    }

    public static BP_konfiguracija getKonfigBP() {
        return konfigBP;
    }

  

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (pmp != null && pmp.isAlive()) {
            pmp.interrupt();
        }
    }
}
