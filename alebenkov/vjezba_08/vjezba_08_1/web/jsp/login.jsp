<%-- 
    Document   : login
    Created on : May 11, 2016, 9:03:31 PM
    Author     : abenkovic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijava korisnika</title>
    </head>
    <body>
        <h1>Prijava korisnika</h1>
        <form action="${pageContext.servletContext.contextPath}/ProvjeraKorisnika">
            <label for="ki">Korisničko ime: </label><br>
            <input name="ki" id="ki" size="10" maxlength="10"><br>
            <label for="pw">Lozinka: </label><br>
            <input type="password" name="pw" id="pw" size="10" maxlength="10"><br>
            <input type="submit" value=" Prijavi se "/>
        </form>
    </body>
</html>
