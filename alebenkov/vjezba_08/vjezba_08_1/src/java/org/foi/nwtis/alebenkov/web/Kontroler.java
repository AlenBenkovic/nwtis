/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.io.IOException;
import java.rmi.ServerException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author abenkovic
 */
public class Kontroler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String zahtjev = request.getServletPath();
        String cilj = null;

        switch (zahtjev) {
            case "/Kontroler":
                cilj = "/jsp/index.jsp";
                break;
            case "/PrijavaKorisnika":
                cilj = "/jsp/login.jsp";
                break;
            case "/OdjavaKorisnika":
                cilj = "/Kontroler";
                break;
            case "/ProvjeraKorisnika":
                /*String ki = request.getParameter("ki");
                String pw = request.getParameter("pw");
                if (ki == null || ki.length() == 0 || pw == null || pw.length() == 0) {
                    throw new NeuspjesnaPrijava("Neispravni podaci za prijavljivanje.");
                }
                HttpSession session = request.getSession();
                Korisnik k = new Korisnik(ki, "Kermek", "Dragutin", request.getRemoteAddr(), session.getId(), 0);
                session.setAttribute("korisnik", k);*/

                cilj = "/IspisPodataka";
                break;
            case "/IspisPodataka":
                cilj = "/privatno/ispisPodataka.jsp";
                break;
            case "/IspisAktivnihKorisnika":
                cilj = "/admin/ispisAktvnihKorisnika.jsp";
                break;
            case "/IspisKorisnika":
                cilj = "/admin/ispisKorisnika.jsp";
                break;
            default:
                cilj = null;
                break;
        }

        if (cilj == null) {
            throw new ServerException("Krivi URL.");
        }

        //ovo sluzi za preusmjeravanje na zeljenu destinaciju
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(cilj); 
        dispatcher.forward(request, response);
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
