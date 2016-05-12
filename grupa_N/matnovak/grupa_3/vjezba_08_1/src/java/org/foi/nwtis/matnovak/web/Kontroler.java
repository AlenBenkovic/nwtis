/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.matnovak.web.kontrole.Korisnik;

/**
 *
 * @author grupa_3
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String putanja = request.getServletPath();
        String odrediste = null;
        switch(putanja){
            case "/Kontroler" :
                odrediste = "/jsp/index.html";
                break;
            case "/PrijavaKorisnika" :
                odrediste = "/jsp/login.jsp";
                break;
            case "/OdjavaKorisnika" :
                odrediste = "/Kontroler";
                break;
            case "/ProvjeraKorisnika" :
                
                String korime = request.getParameter("korime");
                String lozinka = request.getParameter("lozinka");
                
                if(korime == null || korime.length() == 0
                        || lozinka == null || lozinka.length() == 0){
                    throw new NeuspjesnaPrijava("Neispravni podaci prijave!");
                } 
                
                HttpSession session = request.getSession();
                Korisnik k = new Korisnik(korime, "Matija", "Novak", request.getRemoteAddr(), session.getId(), 0);
                session.setAttribute("korisnik", k);
                
                odrediste = "/IspisPodataka";
                break;
            case "/IspisPodataka" :
                odrediste = "/privatno/ispisPodataka.jsp";
                break;
            case "/IspisAktivnihKorisnika" :
                odrediste = "/admin/ispisAktivnihKorisnika.jsp";
                break;
            case "/IspisKorisnika" :
                odrediste = "/admin/ispisKorisnika.jsp";
                break;
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(odrediste);
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
