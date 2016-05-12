/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dkermek.konfiguracije.bp.BP_Konfiguracija;

/**
 * Web application lifecycle listener.
 *
 * @author grupa_1
 */
public class SlusacAplikacije implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
            ServletContext context = sce.getServletContext();
            String konfiguracija = context.getInitParameter("konfiguracija");                        
            String putanje = context.getRealPath("/WEB-INF") + File.separator;
            
            BP_Konfiguracija konfig = null;
            try {
                konfig = new BP_Konfiguracija(putanje + konfiguracija);
                context.setAttribute("BP_Konfig" , konfig);
                System.out.println("Učitana konfiguracija.");
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
