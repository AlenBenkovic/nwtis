/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.filteri;

import java.io.IOException;
import static java.lang.Math.toIntExact;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.alebenkov.ejb.eb.Dnevnik;
import org.foi.nwtis.alebenkov.ejb.sb.DnevnikFacade;

/**
 *
 * @author alebenkov
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/faces/user/meteo.xhtml"})
public class UserFilter implements Filter {

    DnevnikFacade dnevnikFacade = lookupDnevnikFacadeBean();
    Dnevnik dnevnik;

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public UserFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        long pocetak = System.currentTimeMillis();

        if (debug) {
            log("AdminFilter:doFilter()");
        }

        boolean nastavi = true;

        HttpSession sesija = ((HttpServletRequest) request).getSession(false);
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        if (sesija == null) {
            nastavi = false;
        } else if (sesija.getAttribute("user") == null) {
            nastavi = false;
        } else if (Integer.parseInt(sesija.getAttribute("role").toString()) != 2) {
            nastavi = false;
        }

        if (!nastavi) {
            RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/faces/prijava.xhtml");
            rd.forward(request, response);
            dnevnik = new Dnevnik();
            dnevnik.setAkcija(url);
            dnevnik.setKorisnik("N/A");
            dnevnik.setTrajanje(toIntExact(System.currentTimeMillis() - pocetak));
            dnevnik.setVrijeme(new Date());
            dnevnikFacade.create(dnevnik);
        } else {
            chain.doFilter(request, response);
            dnevnik = new Dnevnik();
            dnevnik.setAkcija(url);
            dnevnik.setKorisnik(sesija.getAttribute("user").toString());
            dnevnik.setTrajanje(toIntExact(System.currentTimeMillis() - pocetak));
            dnevnik.setVrijeme(new Date());
            dnevnikFacade.create(dnevnik);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AdminFilter:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private DnevnikFacade lookupDnevnikFacadeBean() {
        try {
            Context c = new InitialContext();
            return (DnevnikFacade) c.lookup("java:global/alebenkov_aplikacija_2/alebenkov_aplikacija_2_1/DnevnikFacade!org.foi.nwtis.alebenkov.ejb.sb.DnevnikFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
