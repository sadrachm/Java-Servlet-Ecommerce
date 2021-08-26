
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
      <sql:query dataSource = "${snapshot}" var = "result">
         SELECT * from guitars;
      </sql:query>
<c:set var="count" value = "0"/>
  <div class="row">
      <c:forEach var="item" items="${result.rows}">
        <div class="<c:out value ='${item.class}'/>">
          <form action = "template.jsp" method = "post">
            <c:if test= "${count<3}">
                <input type ="image" src="img/lesPaul<c:out value = '${count+1}'/>.jpg" name="submit" value="hey" style="display: block;width: 75%;margin-left: auto;margin-right: auto;">
                <h2 style="text-align: center; color: red"> <c:out value = "${item.price}"/> <br><a href="les.html" style="text-decoration: none;"><c:out value = "${item.name}"/></a><br>
                </h2>
                <p style="text-align: center;"> <c:out value = "${item.shortDescription}"/></p>
                <input type ="hidden" name="ID" value ="<c:out value = '${count}'/>">
            </c:if>
            <c:if test= "${count>=3 && count <6}">
                <input type ="image" src="img/tele<c:out value = '${(count%3)+1}'/>.jpg" name="submit" value="hey" style="display: block;width: 75%;margin-left: auto;margin-right: auto;">
                <h2 style="text-align: center; color: red"> <c:out value = "${item.price}"/> <br><a href="les.html" style="text-decoration: none;"><c:out value = "${item.name}"/></a><br>
                </h2>
                <p style="text-align: center;"> <c:out value = "${item.shortDescription}"/></p>
                <input type ="hidden" name="ID" value ="<c:out value = '${count}'/>">
            </c:if>
            <c:if test= "${count>=6 && count <9}">
                <input type ="image" src="img/fender<c:out value = '${(count%3)+1}'/>.jpg" name="submit" value="hey" style="display: block;width: 75%;margin-left: auto;margin-right: auto;">
                <h2 style="text-align: center; color: red"> <c:out value = "${item.price}"/> <br><a href="les.html" style="text-decoration: none;"><c:out value = "${item.name}"/></a><br>
                </h2>
                <p style="text-align: center;"> <c:out value = "${item.shortDescription}"/></p>
                <input type ="hidden" name="ID" value ="<c:out value = '${count}'/>">
            </c:if>
            <c:if test= "${count>8}">
                <input type ="image" src="img/acoustic<c:out value = '${(count%3)+1}'/>.jpg" name="submit" value="hey" style="display: block;width: 75%;margin-left: auto;margin-right: auto;">
                <h2 style="text-align: center; color: red"> <c:out value = "${item.price}"/> <br><a href="les.html" style="text-decoration: none;"><c:out value = "${item.name}"/></a><br>
                </h2>
                <p style="text-align: center;"> <c:out value = "${item.shortDescription}"/></p>
                <input type ="hidden" name="ID" value ="<c:out value = '${count}'/>">
            </c:if>
            <c:set var = "count" value = "${count+1}"/>
          </form>
        </div>
    </c:forEach>
  </div>

<jsp:include page = "/last5" flush = "true"/>
   </body>
</html>
