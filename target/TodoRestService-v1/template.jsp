<%-- 
    Document   : index
    Created on : May 28, 2008, 7:51:24 PM
    Author     : bob
    Purpose    : The initial document for an application that
                 uses a bean in the conversion of a given Celsius
                 temperature to an equivalent Fahrenheit temperature.
--%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<html>
   <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="styles.css">
      <title>The Best Company</title>
   </head>
   <body>
        <div class="nav">
            <a href="goCart" class="nav-right">Cart</a> 
        </div><hr>
        <div class = "header">
            <h1>The Best Company in the World</h1> 
        </div>
        <hr>
      <sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
         url = "jdbc:mysql://localhost:3306/products?useSSL=false"
         user = "root" password = "Sadrach2"/>
         <c:set var = "count" value = "0"/>
      <%switch(Integer.parseInt(request.getParameter("ID"))) {
          case 0:%><c:set var = "id" value="0"/><%break;
          case 1:%><c:set var = "id" value="1"/><%break;
          case 2:%><c:set var = "id" value="2"/><%break;
          case 3:%><c:set var = "id" value="3"/><%break;
          case 4:%><c:set var = "id" value="4"/><%break;
          case 5:%><c:set var = "id" value="5"/><%break;
          case 6:%><c:set var = "id" value="6"/><%break;
          case 7:%><c:set var = "id" value="7"/><%break;
          case 8:%><c:set var = "id" value="8"/><%break;
          case 9:%><c:set var = "id" value="9"/><%break;
          case 10:%><c:set var = "id" value="10"/><%break;
          case 11:%><c:set var = "id" value="11"/><%break;
           
      }%>
      <sql:query dataSource = "${snapshot}" var = "result">
          SELECT * from guitars where idguitars = <c:out value = "${id}+1"/>
      </sql:query>
    <c:if test= "${id/3<1}">
        <c:set var="longN" value="lesPaul"/>
        <c:set var="shortN" value="les"/>
    </c:if>
    <c:if test= "${id/3>=1 && id/3 < 2}">
        <c:set var="longN" value="tele"/>
        <c:set var="shortN" value="tel"/>
    </c:if>
    <c:if test= "${id/3>=2 && id/3 <3}">
        <c:set var="longN" value="fender"/>
        <c:set var="shortN" value="strat"/>
    </c:if>
    <c:if test= "${id/3>=3}">
        <c:set var="longN" value="acoustic"/>
        <c:set var="shortN" value="acous"/>
    </c:if>
    
<c:set var="count" value = "0"/>
      <c:forEach var="item" items="${result.rows}">
          <h2 style="text-align: center; padding: 20px; font-size: 2.5em;"><c:out value = "${item.name}"/></h2>
          <img src="img/<c:out value = '${longN}'/><c:out value = "${(id%3)+1}"/>.jpg" alt=\"\">
          <br>
          <img src="img/<c:out value = "${shortN}"/><c:out value = "${(id%3)+1}"/>.jpg" alt=\"\">
          <br>
          <img src="img/<c:out value = "${shortN}"/><c:out value = "${(id%3)+1}"/><c:out value = "${(id%3)+1}"/>.jpg" alt=\"\">
        
          <div class ="description">
              <h2 style = "font-size: 3em; text-align: center;">Description</h2>
          </div>
    </c:forEach>
<jsp:include page = "/template" flush = "true"/>
    <form  action="session.jsp" method="get" enctype="text/plain" style="font-size:1.5em;">
      <button style="margin:auto 50%; width:100px;">Add to Cart</button>
      <input type="hidden" name="ID" value="<c:out value = '${id}'/>">
    </form>
<br>
   </body>
</html>
