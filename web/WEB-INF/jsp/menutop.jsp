<%-- 
    Document   : menutop
    Created on : 24.03.2015, 15:44:32
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>


   
     <div class="navbar">
        <ul class="nav navbar-nav">
            <li><a href="<c:url value="/index"/>" class="dropdown-toggle">Главная</a></li>
            
           
            <li><a href="#">Эвент</a></li>
            <li><a href="#">Компании</a></li>  
        </ul> 
        <ul class="nav navbar-nav  pull-right">
            <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Меню 2 <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                </ul>    
                
            <li style="top: 15px;"> <security:authentication property="principal.username" /> </li>
            <li>  <a href="<c:url value="/logout"/>"> <img width="20px" src="<c:url value="img/exit.png"/>">Выйти</a>  </li>

        </ul>
    </div>   


