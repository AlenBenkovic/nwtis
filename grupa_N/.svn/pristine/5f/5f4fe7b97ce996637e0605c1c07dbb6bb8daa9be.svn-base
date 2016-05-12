/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.dkermek.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dkermek.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author grupa_1
 */
public class Vjezba_07_2 extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Vjezba_07_2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Vjezba_07_2 at " + request.getContextPath() + "</h1>");

             out.println("<table border='1'/>");
             out.println("<tr><td>MB</td><td>Prezime</td><td>Ime</td></tr>");
            String konfiguracija = this.getInitParameter("konfiguracija");
            
            ServletContext context = this.getServletContext();
            String putanje = context.getRealPath("/WEB-INF") + File.separator;
            
            BP_Konfiguracija konfig = null;
            try {
                konfig = new BP_Konfiguracija(putanje + konfiguracija);
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (konfig == null) {
                System.out.println("Pogreška u konfiguraciji");
                return;
            }
            String url = konfig.getServerDatabase() + konfig.getUserDatabase();
            String query = "select kor_ime, prezime, ime from POLAZNICI";

            try {
                Class.forName(konfig.getDriverDatabase());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            try (
                    Connection con = DriverManager.getConnection(url,
                            konfig.getUserUsername(), konfig.getUserPassword());
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);) {

                out.println("Popis polaznika:");
                while (rs.next()) {
                    String mb = rs.getString(1);
                    String pr = rs.getString(2);
                    String im = rs.getString(3);
                    out.println("<tr><td>" + mb + "</td><td>" + pr + "</td><td>" + im + "</td></tr>");
                }
            } catch (Exception ex) {
                System.err.println("SQLException: " + ex.getMessage());
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
