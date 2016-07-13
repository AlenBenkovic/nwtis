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
import org.foi.nwtis.alebenkov.web.obrada.PozadinskaObrada;

/**
 * Web application lifecycle listener.
 *
 * @author alebenkov
 */
public class SlusacAplikacije implements ServletContextListener {

    static private PozadinskaObrada pozadinskaDretva = null;
    static private BP_konfiguracija bpConfig = null;//konfiguracijski podaci za bp
    static private Konfiguracija serverConfig = null;//ostali konfiguracijski podaci vezani uz rad servera

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Pokrecem alebenkov_aplikacija_2 i ucitavam konfiguraciju...");
        ServletContext context = sce.getServletContext();
        String konfiguracija = context.getInitParameter("konfiguracija");
        String putanje = context.getRealPath("/WEB-INF") + File.separator;

        try {
            bpConfig = new BP_konfiguracija(putanje + konfiguracija);
            //context.setAttribute("bpConfig", bpConfig);
            serverConfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(putanje + konfiguracija);
            context.setAttribute("serverConfig", serverConfig);
            System.out.println("2 | Konfiguracija ucitana.");
        } catch (NemaKonfiguracije ex) {
            System.out.println("2 | Greska prilikom preuzimanja konfiguracije. " + ex.getMessage());
        }

        if (bpConfig.getStatus()) {
            System.out.println("2 | Pokrecem pozadinsku obradu");
            pozadinskaDretva = new PozadinskaObrada();
            pozadinskaDretva.start();

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Gasim alebenkov_aplikacija_2");
        if (pozadinskaDretva.isAlive()) {
            pozadinskaDretva.interrupt();
        }
    }

    public static Konfiguracija getServerConfig() {
        return serverConfig;
    }

    public static void setServerConfig(Konfiguracija serverConfig) {
        SlusacAplikacije.serverConfig = serverConfig;
    }

}