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
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <script src="<c:url value="/js/myJsOnViews/userList.js" />"></script>
        <div class="row ">
            <h3>Пользователи</h3>
        </div>

        <div class="form-group">
            <a href="<c:url value="/User/userAdd"/>" class="btn btn-primary" role="button">Добавить пользователя</a>
        </div> 

        <table class="table table-bordered table-hover">

            <tr>
                <th>${myIndex.index}</th>
                <th>Логин</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Отчество</th>
                <th>Роль</th>
                <th>Участие в кампаниях</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="cabinetUser" items="${cabinetUserList}" varStatus="myIndex">

                <tr id="${cabinetUser.cabinetUserId}">
                    <td>${myIndex.count}</td>
                    <td>${cabinetUser.user.email}</td>
                    <td class="changebleUserListParam" name="surname" data-cabinetUserId="${cabinetUser.cabinetUserId}">${cabinetUser.user.surname}</td>
                    <td class="changebleUserListParam" name="name" data-cabinetUserId="${cabinetUser.cabinetUserId}">${cabinetUser.user.name}</td>
                    <td class="changebleUserListParam" name="patronymic" data-cabinetUserId="${cabinetUser.cabinetUserId}">${cabinetUser.user.patronymic}</td>
                    <c:if test="${cabinetUser.userRole=='admin'}"><c:set var="rusRole" value="Администратор" /><c:set var="updatebleClass" value="changebleUserListParam" /></c:if>
                    <c:if test="${cabinetUser.userRole=='user'}"><c:set var="rusRole" value="Пользователь" /><c:set var="updatebleClass" value="" /></c:if>
                    <c:if test="${cabinetUser.userRole=='observer'}"><c:set var="rusRole" value="Наблюдатель" /><c:set var="updatebleClass" value="" /></c:if>
                    <td class="changebleUserListParam" name="userRole" data-cabinetUserId="${cabinetUser.cabinetUserId}">${rusRole}</td>
                    <c:if test="${cabinetUser.makesCalls==1}"><c:set var="makesCalls" value="Да" /></c:if>
                    <c:if test="${cabinetUser.makesCalls==null}"><c:set var="makesCalls" value="Нет" /></c:if>
                    <td class="${updatebleClass}" name="makingCalls" data-cabinetUserId="${cabinetUser.cabinetUserId}">${makesCalls}</td>
                    <!--<td><div style="cursor: pointer;display: inline;" ondblclick="location = '<c:url value="/User/deleteUser?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>'" >Удалить</div></td>-->
                    
                    <td><a class="btn btn-danger" href="<c:url  value="/User/nullPass?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>">Скинуть пароль</a> </td>
                    <td><a href="#" id="${cabinetUser.cabinetUserId}" class="btn btn-danger deletinghref"
                                        data-cuid="${cabinetUser.cabinetUserId}"
                                        data-toggle="modal"
                                        data-target="#deleteWindow">Удалить</a></td>
                </tr>
            </c:forEach>
        </table>
<div class="modal fade" id="deleteWindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Удаление пользователя</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="deleteUserWindow" action="<c:url value="/User/deleteUser"/>" method="get">
                                                    <p>Что сделать с назначенными клиентами?
                                                    <p><select name="cabinetUserIdtoAssign">
                                                        <option value="0">Снять назначения и удалить информацию о переносах</option>
                                                    <c:forEach var="cabinetUser" items="${cabinetUserList}" varStatus="myIndex">
                                                        <option value="${cabinetUser.cabinetUserId}">Назначить на ${cabinetUser.user.surname} ${cabinetUser.user.name} - ${cabinetUser.user.email}</option>
                                                    </c:forEach>
                                                        </select>
                                                        <input type="hidden" name="cabinetUserIdtoDelete" value="">
                                                    <p>     <input class="btn btn-danger" type="submit" value="Удалить">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>


    </body>
</html>
