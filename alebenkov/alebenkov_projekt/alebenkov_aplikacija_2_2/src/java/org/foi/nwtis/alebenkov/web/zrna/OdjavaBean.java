/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alebenkov
 */
@Named(value = "odjavaBean")
@RequestScoped
public class OdjavaBean {

    private HttpServletRequest request;
    private HttpSession s;
    private boolean prijavljen = false;

    /**
     * Creates a new instance of OdjavaBean
     */
    public OdjavaBean() {
    }

    public String odjava() {
        request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        s = request.getSession(false);
        if(s.getAttribute("user") != null){
            s.invalidate();
            return "prijava.xhtml";
        } else {
            return "prijava.xhtml";
        }
        
    }

    public boolean isPrijavljen() {
        return prijavljen;
    }

}
