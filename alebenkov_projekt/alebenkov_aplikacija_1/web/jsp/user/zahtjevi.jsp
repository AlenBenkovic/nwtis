<%-- 
    Document   : users
    Created on : Jul 1, 2016, 11:40:31 AM
    Author     : abenkovic
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="use" class="org.foi.nwtis.alebenkov.jsp.beans.ZahtjeviUserBean"/>
<jsp:setProperty name="use" property="request" value="${pageContext.request}"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
        <title>Pregled dnevnika</title>
    </head>
    <body>
        <h1>Zahtjevi za socket server</h1>
        <form action="zahtjevi.jsp">
            Adresa: <input type="text" name="adresa"/>
            Od: <input type="text" name="od"/>
            Do: <input type="text" name="do"/>
            (dd.MM.yyyy HH:mm:ss)
            <input type="submit" name="submit" value="Filtriraj"/> 
        </form>
        <display:table id="log" name="${use.dnevnik}" pagesize="${use.brojStranica}">
            <display:setProperty name="basic.show.header" value="true"/>
            <display:column property="id" title="ID" sortable="true"/>
            <display:column property="user" title="Korisnik" sortable="true"/>
            <display:column property="naredba" title="Naredba" sortable="true"/>
            <display:column property="odgovor" title="Odgovor" sortable="true"/>
            <display:column property="time" title="Vrijeme" format="{0,date,dd.MM.yyyy HH:mm:ss}" sortable="true"/>
        </display:table>
        
        <br/>
        <a href="${pageContext.servletContext.contextPath}/jsp/index.jsp" >Povratak</a>
    </body>
</html>
