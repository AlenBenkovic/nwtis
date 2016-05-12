<%-- 
    Document   : login
    Created on : Apr 6, 2016, 6:16:49 PM
    Author     : grupa_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijava</title>
    </head>
    <body>
        <h1>Prijavljivanje korisnika</h1>
        <form action="${pageContext.servletContext.contextPath}/ProvjeraKorisnika" method="POST">
            <label for="korime">Korisničko ime: </label>
            <input name="korime" id="korime" type="text" 
                   size="10" maxlength="10" /><br/>
            <label for="lozinka">Lozinka: </label>
            <input name="lozinka" id="lozinka" type="password" 
                   size="10" maxlength="10" /><br/>
            <input type="submit" value="Prijavi se"/>
        </form>
    </body>
</html>
