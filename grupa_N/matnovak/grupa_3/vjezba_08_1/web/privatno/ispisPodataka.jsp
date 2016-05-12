<%-- 
    Document   : ispisPodataka
    Created on : Apr 6, 2016, 8:39:46 PM
    Author     : grupa_3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis podataka</title>
    </head>
    <body>
        <h1>Ispis podataka</h1>
        Korisničko ime: ${sessionScope.korisnik.korisnik}<br/>
        Ime: ${sessionScope.korisnik.ime}<br/>
        Prezime: ${sessionScope.korisnik.prezime}<br/>
        Vrsta: ${sessionScope.korisnik.vrsta}<br/>
        Sesija ID: ${sessionScope.korisnik.ses_ID}<br/>
        IP adresa:  ${sessionScope.korisnik.ip_adresa}<br/>
    </body>
</html>
