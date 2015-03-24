<%-- 
    Document   : lk
    Created on : 23.03.2015, 13:58:19
    Author     : Юрий
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/css_js.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     
        <title>Личный кабинет</title>
    </head>
    <body class="container">

        <div class="head"> 
            <ul class="nav nav-tabs  pull-right">
                <li>  <a href="<c:url value="/logout"/>"> <img src="<c:url value="img/exit.png"/>"> Выйти</a>  </li>
                <li>  </li>
            </ul>
        </div>   


        <div  class="menu" >
            <ul class="nav nav-tabs nav-stacked">
                <h2> Меню </h2>
               
                <li> <a href="<c:url value="/adduser"/>"> Добавить пользователя</a> </li>
               
                <li>  <a href="<c:url value=""/>"> Создать новый диалог</a> </li>
                
                <li>  <a href="<c:url value=""/>"> Показать текущие диалога</a>  </li> 
              
                <li>  <a href="<c:url value=""/>"> Справочники</a>  </li> 
             
                <li>  <a href="<c:url value=""/>"> Отчет по использованию</a>  </li> 
             
                <li>  <a href="<c:url value=""/>"> Назначить диалог</a>  </li>
            </ul>
        </div>

        <div class="centr"> 


        </div>    



    </body>
</html>
