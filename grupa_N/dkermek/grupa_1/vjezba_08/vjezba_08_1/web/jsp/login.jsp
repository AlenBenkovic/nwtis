<%-- 
    Document   : login
    Created on : Apr 12, 2016, 3:07:09 PM
    Author     : grupa_1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>>Prijavljivanje korisnika</title>
    </head>
    <body>
        <h1>Prijavljivanje korisnika</h1>
        <form action="${pageContext.servletContext.contextPath}/ProvjeraKorisnika">
            <label for="ki">Korisničko ime: </label>
            <input name="ki" id="ki" size="10" maxlength="10"><br>
            <label for="pw">Lozinka: </label>
            <input type="password" name="pw" id="pw" size="10" maxlength="10">
            <input type="submit" value=" Prijavi se "/>
        </form>
    </body>
</html>
