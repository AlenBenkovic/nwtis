<%-- 
    Document   : test
    Created on : Apr 13, 2016, 2:49:09 PM
    Author     : grupa_1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        driver="${applicationScope.BP_Konfig.driverDatabase}"<br>
        url="${applicationScope.BP_Konfig.serverDatabase}${applicationScope.BP_Konfig.userDatabase}"<br>
        user="${applicationScope.BP_Konfig.userUsername}"<br>
        password="${applicationScope.BP_Konfig.userPassword}"<br>

    </body>
</html>
