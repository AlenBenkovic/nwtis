<%-- 
    Document   : ispisKorisnika
    Created on : Apr 13, 2016, 6:33:21 PM
    Author     : grupa_3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis korisnika</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
    </head>
    <body>
        <h1>Ispis korisnika iz baze</h1>
        <sql:setDataSource 
            var="ds"
            driver="${applicationScope.BP_Konfig.driverDatabase}"
            url="${applicationScope.BP_Konfig.serverDatabase}${applicationScope.BP_Konfig.userDatabase}"
            user="${applicationScope.BP_Konfig.userUsername}"
            password="${applicationScope.BP_Konfig.userPassword}"
            />
        <sql:transaction dataSource="${ds}">
            <sql:query var="rez">
                SELECT * FROM polaznici
            </sql:query>

                <display:table name="${rez.rows}" pagesize="5" export="true">
                <display:column property="kor_ime" title="Korisničko ime" />
                <display:column property="prezime" title="Prezime" />
                <display:column property="ime" title="Ime" />
            </display:table>    

        </sql:transaction>
    </body>
</html>
