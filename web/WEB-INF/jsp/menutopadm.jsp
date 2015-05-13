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
                <li><a href="<c:url value="/User/userList"/>">Пользователи</a></li>
                <li><a href="<c:url value="/Client/clientList"/>">Клиенты</a></li>
                <li><a href="<c:url value="/Event/eventList"/>">Кампания(Event)</a></li>
                <li><a href="#">Отчет по кампании(Event'у)</a></li>
                <li><a href="#">Отчет по операторам</a></li>
                <li><a href="<c:url value="/Strategy/show"/>">Стратегии</a></li>
            </ul>                    
        </li>
    </ul> 
    <%@include file="/WEB-INF/jsp/menuright.jsp" %>
</div>  
