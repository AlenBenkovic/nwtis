/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.slusaci;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author abenkovic
 */
@WebListener()
public class Vjezba_07_4 implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        System.out.println("Inicijalizirana aplikacija: " + sc.getContextPath());
        System.out.println("Inicijalni parametar konfiguracija: " + sc.getInitParameter("konfiguracija"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        System.out.println("Završava aplikacija: " + sc.getContextPath());
    }
}
