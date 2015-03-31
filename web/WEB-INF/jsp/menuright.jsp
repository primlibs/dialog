<%-- 
    Document   : menuright
    Created on : 31.03.2015, 10:57:14
    Author     : Юрий
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <ul class="nav navbar-nav  pull-right">
      
      <li><a href="#"> ОП</a></li>
        <li style="top: 15px;">  </li>    
        <li style="top: 15px;"> <security:authentication property="principal.username" /> </li>
        <li>  <a href="<c:url value="/logout"/>"> <img width="20px" src="<c:url value="img/exit.png"/>">Выйти</a>  </li>

    </ul>
