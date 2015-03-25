<%-- 
    Document   : menutop
    Created on : 24.03.2015, 15:44:32
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>


   
    <div class="head"> 
        <ul class="nav nav-tabs  pull-right">
            <li style="top: 11px;"> <security:authentication property="principal.username" /> </li>
            <li>  <a href="<c:url value="/logout"/>"> <img width="20px" src="<c:url value="img/exit.png"/>">Выйти</a>  </li>
            
        </ul>
    </div>   


