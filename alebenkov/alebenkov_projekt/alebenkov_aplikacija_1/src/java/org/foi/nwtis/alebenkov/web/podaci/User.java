/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.podaci;

/**
 *
 * @author alebenkov
 */
public class User {
    private String user;
    private String pass;
    private int role;
    private int rang;

    public User(String user, String pass, int role, int rang) {
        this.user = user;
        this.pass = pass;
        this.role = role;
        this.rang = rang;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
    
    
    
}
