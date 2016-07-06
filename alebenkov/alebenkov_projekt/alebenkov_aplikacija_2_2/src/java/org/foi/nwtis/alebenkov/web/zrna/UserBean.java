/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alebenkov
 */
@Named(value = "userBean")
@Dependent
public class UserBean {

    private String user;
    private int role;
    private int rang;
    private boolean admin=false;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession s = request.getSession();
        user = s.getAttribute("user").toString();
        role = Integer.parseInt(s.getAttribute("role").toString());
        rang = Integer.parseInt(s.getAttribute("rang").toString());
        if(role==1){
            admin = true;
        }
        System.out.println(" " + user + " " + role + " " + rang + " " + admin);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
