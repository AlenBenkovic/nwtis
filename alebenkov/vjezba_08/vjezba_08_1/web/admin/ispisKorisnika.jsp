<%-- 
    Document   : ispisKorisnika
    Created on : May 11, 2016, 9:33:55 PM
    Author     : abenkovic
--%>

<%@page import="org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija"%>
<%@page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis korisnika</title>
    </head>
    <body>
        <h1>Ispis korisnika</h1>
        <%
            String path = this.getServletContext().getRealPath("/WEB-INF") + java.io.File.separator;
            String datoteka = path + this.getInitParameter("konfiguracija");
            BP_konfiguracija bp = new BP_konfiguracija(datoteka);
            if (bp.getStatus()) {
                System.out.println(bp.getDriverDatabase());
                String connUrl = bp.getServerDatabase() + bp.getUserDatabase();
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

            }
        %>
    </body>
</html>
