/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.rest.klijenti.GMKlijent;
import org.foi.nwtis.alebenkov.web.podaci.Lokacija;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;
import org.foi.nwtis.alebenkov.ws.serveri.GeoMeteoWS;

/**
 *
 * @author grupa_1
 */
@WebServlet(name = "DodajAdresu", urlPatterns = {"/dodajAdresu"})
public class DodajAdresu extends HttpServlet {

    private String adresa;
    private String akcija;
    private Lokacija lokacija;
    private GeoMeteoWS gmws;
    private MeteoPodaci mp;
    private boolean upisBP = false;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); //ukoliko ne podesim encoding javlja se error pri unosu određenih gradova
        this.adresa = request.getParameter("adresa");
        this.akcija = request.getParameter("akcija");
        if (akcija.equals("dohvatGP")) {
            //TODO dohvat GEO podataka

            GMKlijent gmk = new GMKlijent();
            this.lokacija = gmk.getGeoLocation(adresa);

        } else if (akcija.equals("spremiGP")) {
            //TODO spremanje podataka o adresi u tablicu bp adrese
            GMKlijent gmk = new GMKlijent();
            this.lokacija = gmk.getGeoLocation(adresa);
            OperacijeBP obp = new OperacijeBP();
            upisBP = obp.upisAdreseBP(adresa, lokacija.getLatitude(), lokacija.getLongitude());
        } else if (akcija.equals("dohvatMP")) {
            //TODO dohvat vazecih metopodataka upisane adrese
            gmws = new GeoMeteoWS();
            mp = gmws.dajVazeceMeteoPodatkeZaAdresu(adresa);
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DodajAdresu</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>alebenkov_zadaca_3_1</h1><br/>");
            if (lokacija != null && akcija.equals("dohvatGP")) {
                out.println("Podaci za adresu: " + adresa + "<br/>Geografska širina (latitude): " + this.lokacija.getLatitude() + "<br/> Geografska dužina (longitude): " + this.lokacija.getLongitude() + "<br/>");
            }
            if (mp != null && akcija.equals("dohvatMP")) {
                out.println("Podaci za adresu: " + adresa + "<br/>");
                out.println("Temperatura: " + mp.getTemperatureValue() + "<br/>");
                out.println("Vlažnost zraka: " + mp.getHumidityValue() + "<br/>");
                out.println("Vrijeme: " + mp.getWeatherValue() + "<br/>");

            } 
            if(upisBP && akcija.equals("spremiGP")){
                out.println("Adresa uspješno spremljena u bazu podataka.");
            } else if(akcija.equals("spremiGP") && !upisBP) {
                out.println("Zapis nije spremljen u bazu. Vjerojatno vec postoji zapis u bp.");
            }
            out.println("<br /><a href=\"" + request.getContextPath() +"\">Povratak nazad </a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
