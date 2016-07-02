<%-- 
    Document   : users
    Created on : Jul 1, 2016, 11:40:31 AM
    Author     : abenkovic
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="use" class="org.foi.nwtis.alebenkov.jsp.beans.ListaKorisnikaBean"/>
<jsp:setProperty name="use" property="request" value="${pageContext.request}"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
        <title>Pregled korisnika</title>
    </head>
    <body>
        <h1>Korisnici</h1>
        <display:table id="log" name="${use.korisnici}" pagesize="${use.brojStranica}">
            <display:setProperty name="basic.show.header" value="true"/>
            <display:column property="user" title="Korisničko ime" sortable="true"/>
            <display:column property="pass" title="Lozinka" sortable="true"/>
            <display:column property="role" title="Role" sortable="true"/>
            <display:column property="rang" title="Rang" sortable="true"/>
        </display:table>
       
        <br/>
        <a href="${pageContext.servletContext.contextPath}/jsp/index.jsp" >Povratak</a>
    </body>
</html>
