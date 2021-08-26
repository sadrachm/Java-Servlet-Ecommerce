<%-- 
    Document   : session
    Created on : May 14, 2021, 11:10:50 AM
    Author     : Sadrach
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%            String x = request.getParameter("ID");
            if (session.getAttribute(x) != null){
                String w = session.getAttribute(x).toString();
                session.setAttribute(x, w+1);
            }
            else {
                String y = new String("1");
                session.setAttribute(x, y);
            }
%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="utf-8">
  <title>The Best Company</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="nav">
    <a href="goHome" class="nav-left">Home</a>
    <a href="goCart" class="nav-right">Cart</a>
  </div>
  <hr>
  <div class="header">
    <h1>The Best Company in the World</h1>
  </div>
  <hr>
  <h1 style="text-align:center;">Your Item has been added to cart</h1>
  <form action="goHome" method="get" enctype="text/plain" style="font-size:1.5em;">
    <button type="submit">Home</button>
  </form>
</body>
</html>
