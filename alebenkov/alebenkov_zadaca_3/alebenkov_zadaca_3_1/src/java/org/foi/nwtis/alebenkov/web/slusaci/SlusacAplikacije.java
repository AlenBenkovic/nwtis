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
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;

/**
 * Web application lifecycle listener.
 *
 * @author abenkovic
 */
public class SlusacAplikacije implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String path = sc.getRealPath("/WEB-INF") + java.io.File.separator; //uzimam koja je to putanja do WEB-INF direktorija jer tamo se nalazi datoteka s postavkama
        String datoteka = path + sc.getInitParameter("konfiguracija");
        BP_konfiguracija bp = new BP_konfiguracija(datoteka);
        
        if (bp.getStatus()){
            sc.setAttribute("BP_Konfig", bp);
            System.out.println("Učitana konfiguracija.");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}