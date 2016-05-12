<%-- 
    Document   : ispisAktivnihKorisnika
    Created on : Apr 13, 2016, 2:18:34 PM
    Author     : grupa_1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis aktivnih korisnika</title>
    </head>
    <body>
        <h1>Ispis aktivnih korisnika</h1>
        
        <c:forEach items="${applicationScope.KORISNICI}" var="k">
            ${k.prezime} ${k.ime} <br>
        </c:forEach>
    </body>
</html>
