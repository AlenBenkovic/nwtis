<%-- 
    Document   : index
    Created on : Jul 2, 2016, 2:45:21 PM
    Author     : alebenkov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>alebenkov_aplikacija_1</h1>
        <h2>Admin:</h2>
        <a href="${pageContext.servletContext.contextPath}/jsp/admin/users.jsp" >Pregled korisnika</a><br/>
        <a href="${pageContext.servletContext.contextPath}/jsp/admin/dnevnik.jsp" >Pregled dnevnika rada</a><br/>
        <a href="${pageContext.servletContext.contextPath}/jsp/admin/zahtjevi.jsp" >Pregled zahtjeva korisnika</a><br/>
        <h2>User:</h2>
        <a href="${pageContext.servletContext.contextPath}/jsp/user/zahtjevi.jsp" >Pregled vlastitih zahtjeva</a>
    </body>
</html>
