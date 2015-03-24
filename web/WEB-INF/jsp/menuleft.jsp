<%-- 
    Document   : menuleft
    Created on : 24.03.2015, 15:42:31
    Author     : Юрий
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/css_js.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
        <title></title>
    </head>
    <body>
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
    </body>
</html>
