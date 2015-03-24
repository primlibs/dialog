<%-- 
    Document   : menutop
    Created on : 24.03.2015, 15:44:32
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/css_js.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
    <title>  </title>
</head>
<body>
    <div class="head"> 
        <ul class="nav nav-tabs  pull-right">
            <li style="top: 20px;"> <security:authentication property="principal.username" /> </li>
            <li>  <a href="<c:url value="/logout"/>"> <img src="<c:url value="img/exit.png"/>">Выйти</a>  </li>
            
        </ul>
    </div>   
</body>
</html>
