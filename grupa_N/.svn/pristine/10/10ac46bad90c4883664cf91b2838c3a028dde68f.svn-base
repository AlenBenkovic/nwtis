/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.slusaci;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.foi.nwtis.matnovak.web.kontrole.Korisnik;

/**
 * Web application lifecycle listener.
 *
 * @author grupa_2
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
        if(event.getName().compareTo("korisnik") == 0){
            System.out.println("Prijavljen korisnik: "+event.getValue());
            
            ServletContext sc =  event.getSession().getServletContext();
            Object o = sc.getAttribute("KORISNICI");
            ArrayList<Korisnik> korisnici = null;
            if(o == null){
                korisnici = new ArrayList<Korisnik>(); 
            }
            else if(o instanceof ArrayList)  {
                korisnici = (ArrayList<Korisnik>) o;
            }
            else {
                System.err.println("Pogrešna klasa za evidenciju korisnika!");
            }
            
            if(event.getValue() instanceof Korisnik){
                Korisnik k = (Korisnik) event.getValue();
                korisnici.add(k);
                sc.setAttribute("KORISNICI", korisnici);
                
                ArrayList<Korisnik> korisnici2 = (ArrayList<Korisnik>) sc.getAttribute("KORISNICI");
                System.out.println("Broj prijavljenih korisnika: "+korisnici2.size());
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if(event.getName().compareTo("korisnik") == 0){
                  
            ServletContext sc =  event.getSession().getServletContext();
            Object o = sc.getAttribute("KORISNICI");
            ArrayList<Korisnik> korisnici = null;
            
            if(o == null){
                korisnici = new ArrayList<Korisnik>(); 
            }
            else if(o instanceof ArrayList)  {
                korisnici = (ArrayList<Korisnik>) o;
            }
            else {
                System.err.println("Pogrešna klasa za evidenciju korisnika!");
            }
            
            String idSesije = event.getSession().getId();
            for(Korisnik k : korisnici) {
                if(k.getSes_ID().compareTo(idSesije) == 0){
                    korisnici.remove(k);
                    sc.setAttribute("KORISNICI", korisnici);
                    System.out.println("Odjavljen korisnik: "+event.getValue() + " " + k.getKorisnik());
                }
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }
}
