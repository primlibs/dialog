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
     
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="form-group">
            <a href="<c:url value="/User/userList"/>" class="btn btn-primary" role="button">Список пользователей</a>
            <a href="<c:url value="/User/userAdd"/>" class="btn btn-primary" role="button">Добавить пользователя</a>
        </div> 

        <table class="table table-bordered table-hover">

            <tr>
                <td>${myIndex.index}</td>
                <td>имя</td>
                <td>Фамилия</td>
                <td>роль</td>
                <td>изменить</td>
                <td>удалить</td>
            </tr>
            <c:forEach var="cabinetUser" items="${cabinetUserList}" varStatus="myIndex">

                <tr>
                    <td>${myIndex.count}</td>
                    <td>${cabinetUser.user.name}</td>
                    <td>${cabinetUser.user.surname}</td>
                    <td>${cabinetUser.user_role}</td>
                    <td>изменить</td>
                    <td onClick="location = '<c:url value="/User/deleteUser?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>'" >Удалить</td>
                </tr>
            </c:forEach>
        </table>



    </body>
</html>
