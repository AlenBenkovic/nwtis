/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.filteri;

import java.io.IOException;
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

/**
 *
 * @author alebenkov
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/faces/user/meteo.xhtml"})
public class UserFilter implements Filter {

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

        if (debug) {
            log("AdminFilter:doFilter()");
        }


        boolean nastavi = true;

        HttpSession sesija = ((HttpServletRequest) request).getSession(false);
        if (sesija == null) {
            nastavi = false;
        } else {
            if (sesija.getAttribute("user") == null) {
                nastavi = false;
            }  else if (Integer.parseInt(sesija.getAttribute("role").toString()) != 2) {
                nastavi = false;
            }
        }

        if (!nastavi) {
            RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/faces/prijava.xhtml");
            rd.forward(request, response);
        } else {
            chain.doFilter(request, response);
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

}