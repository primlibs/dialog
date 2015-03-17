<%-- 
    Document   : login
    Created on : 16.03.2015, 19:17:08
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Вход в систему</title>
    </head>
    <body>
        Текущая дата: <%= new java.util.Date()%>
        <h1>Введите логин и пароль</h1>
        <form action="j_spring_security_check" method="POST">
            <div id="loginBox">
                <div>
                 <input placeholder="Введите логин" type="text" size="20" name="j_username"></div>
                <div>
                 <input placeholder="Введите пароль" type="password" size="20" name="j_password"></div>
             <div><input type="submit" value="Авторизоваться"></div>
            </div>
        </form>
        
        <br>
             <br>          
        <a href="<c:url value="/registration" />">Регистрация!!</a>
        
    </body>
</html>
