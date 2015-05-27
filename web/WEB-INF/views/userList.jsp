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
            <a href="<c:url value="/User/userAdd"/>" class="btn btn-primary" role="button">Добавить пользователя</a>
        </div> 

        <table class="table table-bordered table-hover">

            <tr>
                <td>${myIndex.index}</td>
                
                <td>Фамилия</td>
                <td>Имя</td>
                <td>Отчество</td>
                <td>Роль</td>
                <td>Участие в кампаниях</td>
                <td>Удалить</td>
            </tr>
            <c:forEach var="cabinetUser" items="${cabinetUserList}" varStatus="myIndex">

                <tr>
                    <td>${myIndex.count}</td>
                    <td>${cabinetUser.user.surname}</td>
                    <td>${cabinetUser.user.name}</td>
                    <td>${cabinetUser.user.patronymic}</td>
                    <c:if test="${cabinetUser.userRole=='admin'}"><c:set var="rusRole" value="Администратор" /></c:if>
                    <c:if test="${cabinetUser.userRole=='user'}"><c:set var="rusRole" value="Пользователь" /></c:if>
                    <td>${rusRole}</td>
                    <c:if test="${cabinetUser.makesCalls==null}"><c:set var="makesCalls" value="Да" /></c:if>
                    <c:if test="${cabinetUser.makesCalls==null}"><c:set var="makesCalls" value="Нет" /></c:if>
                    <td>${makesCalls}</td>
                    <td onClick="location = '<c:url value="/User/deleteUser?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>'" >Удалить</td>
                </tr>
            </c:forEach>
        </table>



    </body>
</html>
