<%-- 
    Document   : ispisPodataka
    Created on : Apr 12, 2016, 3:11:53 PM
    Author     : grupa_1
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
