<%-- 
    Document   : login
    Created on : Apr 6, 2016, 8:39:09 PM
    Author     : grupa_3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijava</title>
    </head>
    <body>
        <h1>Prijavite se</h1>
        <form method="POST" action="${pageContext.servletContext.contextPath}/ProvjeraKorisnika">
            <label for="korime">Korisničko ime</label>
            <input name="korime" id="korime" size="10" maxlength="10" />
            <br/>
            <label for="lozinka">Lozinka</label>
            <input name="lozinka" id="lozinka" size="10" maxlength="10" />
            <br/>
            <input type="submit" value="Prijavi se"/>
        </form>
    </body>
</html>
