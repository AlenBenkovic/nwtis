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
        ServletContext context = sce.getServletContext();
        String konfiguracija = context.getInitParameter("konfiguracija");
        String putanje = context.getRealPath("/WEB-INF") + File.separator;

        BP_konfiguracija konfig = null;
        konfig = new BP_konfiguracija(putanje + konfiguracija);

        if (konfig.getStatus()) {
            context.setAttribute("BP_Konfig", konfig);
            System.out.println("Ucitana konfiguracija.");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
