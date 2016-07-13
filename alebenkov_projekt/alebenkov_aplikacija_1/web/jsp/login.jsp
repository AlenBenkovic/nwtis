<%-- 
    Document   : login
    Created on : Jun 30, 2016, 9:39:11 PM
    Author     : abenkovic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijava</title>
    </head>
    <body>
        <form method="POST" action="j_security_check">
            <table border="0">
                <tr>
                    <td>Login</td>
                    <td><input type="text" name="j_username"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="j_password"></td>
                </tr>
            </table>
            <input type="submit" value="Login!">
            </center>
        </form>
    </body>
</html>
