/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.matnovak.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.matnovak.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author grupa_2
 */
@WebServlet(name = "Vjezba_07_2", urlPatterns = {"/Vjezba_07_2"}, initParams = {
    @WebInitParam(name = "konfiguracija", value = "NWTiS.db.config_1.xml")})
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

            String dir = this.getServletContext().getRealPath("/WEB-INF");
            String datoteka = dir + File.separator
                    + this.getInitParameter("konfiguracija");

            BP_Konfiguracija bp_konfig = null;

            try {
                bp_konfig = new BP_Konfiguracija(datoteka);
                out.println("Server: " + bp_konfig.getServerDatabase());
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
            }

            String url = bp_konfig.getServerDatabase() + bp_konfig.getUserDatabase();
            String user = bp_konfig.getUserUsername();
            String password = bp_konfig.getUserPassword();
            try {
                Class.forName(bp_konfig.getDriverDatabase());
            } catch (ClassNotFoundException ex) { 
                Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try ( Connection c = DriverManager.getConnection(url, user, password);
                  Statement s = c.createStatement();
                  ResultSet rs = s.executeQuery("SELECT ime, prezime FROM polaznici");    ) {
               
                out.println("<table><tr><th>Ime</th><th>Prezime</th></tr>");
                while (rs.next()) {
                    out.println("<tr><td>"+rs.getString("ime") + "</td><td> " + rs.getString("prezime")+"</td></tr>");
                }
                out.println("</table>");
            } catch (SQLException ex) { 
                Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
            }
                

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
