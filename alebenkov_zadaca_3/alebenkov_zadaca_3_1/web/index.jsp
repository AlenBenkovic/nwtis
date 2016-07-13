<%-- 
    Document   : index
    Created on : Apr 27, 2016, 1:42:03 PM
    Author     : grupa_1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zadaća 3</title>
    </head>
    <h1>alebenkov_zadaca_3_1</h1><br/>
    <form action="dodajAdresu" method="POST">
        <label for="adresa">Adresa:</label>
        <input name="adresa" id="adresa" size="100" maxlength="254" required="required" ><br> <br />
        <input type="submit" name = "akcija" value="dohvatGP" ><br>
        <input type="submit" name = "akcija" value="spremiGP" ><br>
        <input type="submit" name = "akcija" value="dohvatMP" ><br>
    </form>
</body>
</html>
