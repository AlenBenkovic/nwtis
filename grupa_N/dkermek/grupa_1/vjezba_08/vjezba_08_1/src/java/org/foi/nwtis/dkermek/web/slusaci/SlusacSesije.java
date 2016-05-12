/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.web.slusaci;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.foi.nwtis.dkermek.web.kontrole.Korisnik;

/**
 * Web application lifecycle listener.
 *
 * @author grupa_1
 */
public class SlusacSesije implements HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().compareTo("korisnik") == 0) {
            System.out.println("Prijavljen korisnik: " + event.getValue());

            ServletContext context = event.getSession().getServletContext();
            Object o = context.getAttribute("KORISNICI");
            ArrayList<Korisnik> korisnici = null;

            if (o == null) {
                korisnici = new ArrayList();
            } else if (o instanceof ArrayList) {
                korisnici = (ArrayList<Korisnik>) o;
            } else {
                System.out.println("Pogreška klasa za evidenciju korisnika");
            }
            if (event.getValue() instanceof Korisnik) {
                Korisnik k = (Korisnik) event.getValue();
                korisnici.add(k);
                context.setAttribute("KORISNICI", korisnici);
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().compareTo("korisnik") == 0) {
            ServletContext context = event.getSession().getServletContext();
            Object o = context.getAttribute("KORISNICI");
            ArrayList<Korisnik> korisnici = null;

            if (o == null) {
                korisnici = new ArrayList();
            } else if (o instanceof ArrayList) {
                korisnici = (ArrayList<Korisnik>) o;
            } else {
                System.out.println("Pogreška klasa za evidenciju korisnika");
            }
            String idSedije = event.getSession().getId();
            for (Korisnik k : korisnici) {
                if (k.getSes_ID().compareTo(idSedije) == 0) {
                    korisnici.remove(k);
                    context.setAttribute("KORISNICI", korisnici);
                    System.out.println("Odjavljen korisnik: " + k.getKorisnik());

                    break;
                }
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event
    ) {
    }
}
