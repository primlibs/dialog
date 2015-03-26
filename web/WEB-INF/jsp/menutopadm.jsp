<%-- 
    Document   : menutopadm
    Created on : 26.03.2015, 10:20:45
    Author     : Юрий
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>




<div class="navbar">
    <ul class="nav navbar-nav">
        <li><a href="<c:url value="/index"/>" class="dropdown-toggle">Главная</a></li>
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Меню <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="/adduser"/>">Пользователи</a></li>
                <li><a href="#">Клиенты</a></li>
                <li><a href="#">Компании</a></li>
                <li><a href="#">Клиент</a></li>
                <li><a href="#">Отчет по компании</a></li>
                <li><a href="#">Отчет по операторам</a></li>
                <li><a href="#">Стратегии</a></li>
            </ul>                    
        </li>

    </ul> 
    <ul class="nav navbar-nav  pull-right">
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle"> Меню 2 <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>

            </ul>    
        <li style="top: 15px;">  </li>    
        <li style="top: 15px;"> <security:authentication property="principal.username" /> </li>
        <li>  <a href="<c:url value="/logout"/>"> <img width="20px" src="<c:url value="img/exit.png"/>">Выйти</a>  </li>

    </ul>
</div>  
