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
import org.foi.nwtis.alebenkov.web.server.PozadinskoPreuzimanje;
import org.foi.nwtis.alebenkov.web.server.ServerSustava;

/**
 * Slusac aplikacije koji pokrece sam server i pozadinske dretve
 *
 * @author abenkovic
 */
public class SlusacAplikacije implements ServletContextListener {
    private ServerSustava server = null;
    static private PozadinskoPreuzimanje pozadinskaDretva = null;
    static private BP_konfiguracija bpConfig = null;
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Pokrecem alebenkov_aplikacija_1 i ucitavam konfiguraciju...");
        ServletContext context = sce.getServletContext();
        String konfiguracija = context.getInitParameter("konfiguracija");
        String putanje = context.getRealPath("/WEB-INF") + File.separator;

        //konfiguracijski podaci za bp
        Konfiguracija serverConfig = null;//ostali konfiguracijski podaci vezani uz rad servera
        
        try {
            bpConfig = new BP_konfiguracija(putanje + konfiguracija);
            //context.setAttribute("bpConfig", bpConfig);
            serverConfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(putanje + konfiguracija);
            context.setAttribute("serverConfig", serverConfig);
            System.out.println("Konfiguracija ucitana.");
        } catch (NemaKonfiguracije ex) {
            System.out.println("Greska prilikom preuzimanja konfiguracije. " + ex.getMessage());
        }
        
        if(bpConfig.getStatus()){
            System.out.println("Kreiram server");
            server = new ServerSustava(context);
            server.start();
            pozadinskaDretva = new PozadinskoPreuzimanje();
            pozadinskaDretva.start();
            
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Gasim alebenkov_aplikacija_1");
        if(server.isAlive()){
            server.interrupt();
        }
        if(pozadinskaDretva.isAlive()){
            pozadinskaDretva.interrupt();
        }
        
    }

    public static BP_konfiguracija getBpConfig() {
        return bpConfig;
    }

    public static PozadinskoPreuzimanje getPozadinskaDretva() {
        return pozadinskaDretva;
    }

    
    
    
    
    
}
