<%-- 
    Document   : successRegistration
    Created on : 10.03.2015, 10:43:41
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>

    </head>

    <body class="container">


    <center style="margin-top: 114px;">
        <form method="post" action="j_spring_security_check" class="login">
            <h2 >Регистрация посетителя успешно завершена</h2> <br>
            <input type="hidden" name="j_username" value="${username}">
            <input type="hidden" name="j_password" value="${password}">
            <button type="submit" class="btn btn-primary">Войти</button>
        </form>
    </center>


</html>
