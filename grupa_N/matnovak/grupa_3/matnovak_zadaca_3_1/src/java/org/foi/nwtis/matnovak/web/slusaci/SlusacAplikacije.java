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
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.matnovak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.matnovak.web.PreuzmiMeteoPodatke;

/**
 * Web application lifecycle listener.
 *
 * @author grupa_3
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {
    static private ServletContext context = null;
    private PreuzmiMeteoPodatke pmp = null;
    
    static public ServletContext getContext() {
        return context;
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        String dir = context.getRealPath("/WEB-INF");
        System.out.println("WEB-INF dir: " + dir);
        String datoteka = dir + File.separator + context.getInitParameter("konfiguracija");
        BP_Konfiguracija bp_konfig = null;
        try {
            bp_konfig = new BP_Konfiguracija(datoteka);
            System.out.println("Server: " + bp_konfig.getServerDatabase());
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        context.setAttribute("BP_Konfig", bp_konfig);
        
        pmp = new PreuzmiMeteoPodatke();
        pmp.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(pmp != null && pmp.isAlive()){
            pmp.interrupt();
        }
    }


}
