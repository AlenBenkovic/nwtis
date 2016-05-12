/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.matnovak.konfiguracije.bp.BP_Konfiguracija;

/**
 * Web application lifecycle listener.
 *
 * @author grupa_2
 */
public class SlusacAplikacije implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String dir = context.getRealPath("/WEB-INF");
        String datoteka = dir + File.separator
                + context.getInitParameter("konfiguracija");

        BP_Konfiguracija bp_konfig = null;

        try {
            bp_konfig = new BP_Konfiguracija(datoteka);
            context.setAttribute("BP_Konfig", bp_konfig);
            System.out.println("Server: " + bp_konfig.getServerDatabase());
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      
    }
}
