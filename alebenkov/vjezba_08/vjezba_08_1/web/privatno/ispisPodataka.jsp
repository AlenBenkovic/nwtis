<%-- 
    Document   : ispisPodataka.jsp
    Created on : May 11, 2016, 9:32:43 PM
    Author     : abenkovic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis podataka korisnika</title>
    </head>
    <body>
        <h1>Ispis podataka korisnika</h1>
        Korisničko ime: ${sessionScope.korisnik.korisnik}<br>
        Prezme: ${sessionScope.korisnik.prezime}<br>
        Ime: ${sessionScope.korisnik.ime}<br>
        IP adresa: ${sessionScope.korisnik.ip_adresa}<br>
        Vrsta: ${sessionScope.korisnik.vrsta}<br>
        ID sesije: ${sessionScope.korisnik.ses_ID}<br>      
    </body>
</html>
