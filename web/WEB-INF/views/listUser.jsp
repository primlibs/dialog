<%-- 
    Document   : listUser
    Created on : 10.04.2015, 14:24:00
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title> </title>
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <br> 
        <%@include file="/WEB-INF/jsp/error.jsp" %> <br>

        <div class="form-group">
            <a href="<c:url value="/listUser"/>" class="btn btn-large" role="button">Список пользователей</a>
            <a href="<c:url value="/adduser"/>" class="btn btn-large" role="button">Добавить пользователя</a>
        </div> 

        <table>
            <c:forEach var="person" items="${user.people}">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.role}</td>
                </tr>
            </c:forEach>
        </table>









    </body>
</html>
