<%-- 
    Document   : registration
    Created on : 13.03.2015, 16:43:58
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Регистрация</title>
    </head>
    <body>
        Текущая дата: <%= new java.util.Date()%>
        <h1>Регистрация</h1>
        <% request.setCharacterEncoding("UTF-8");%>

        <%
            if (request.getParameter("error") != null) {
                out.println(request.getParameter("error"));
            }
        %>


        <form action="<c:url value="/registration" />" method="post">
            <h2> Данные компании : </h2>
            <br>
            Название компании: <input type="text" name="company" size="10" maxlength=30>
          
            <br>
            Email компании: <input type="text" name="emailCompany" maxlength=60>
            <br>
            Телефон компании: <input type="text" name="phone" maxlength=12>
            
            <br>
            <h2>   Личные данные:</h2>
            <br>
            Email личный: <input type="text" name="email" maxlength=60>
            <br>
            Пароль: <input type="password" name="password" size="10" maxlength=20>
            <br>
            фамилия: <input type="text" name="surname" size="10" maxlength=30> <br>
            Имя: <input type="text" name="name" size="10" maxlength=30> <br>
            Отчество: <input type="text" name="patronymic" size="10" maxlength=30> <br>

            <p>
            <table>
                <tr>
                    <th><small>
                            <input type="submit" name="submit" value="Сохранить">
                        </small>
            </table>
        </form>


        <br>
        <a href="http://62.76.41.244/CallCentr/index">Начальная страница</a>
    </body>
</html>
