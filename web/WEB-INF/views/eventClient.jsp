<%-- 
    Document   : eventClient
    Created on : 08.05.2015, 16:04:44
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> </title>
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <div class="row">
            <h4>  Евент:  ${event.name} &nbsp Стратегия: ${event.strategy.strategyName} </h4>
            <input type="hidden" name="eventId" value=${param['eventId']}>
            <div class="btn-group" role="group" >
                <a href="<c:url value="/Event/eventTask?eventId=${param['eventId']}"/>" class="btn btn-primary" role="button">Евент</a>
                <a href="<c:url value="/Event/eventShowAllAppoint?eventId=${param['eventId']}"/>" class="btn btn-primary" role="button">Назначить всем</a>
            </div>
            <form enctype="multipart/form-data" class="form-inline btn-group" action="<c:url value="/Event/eventClient" />" method="post">
                <div class="bootstrap-select">
                    <select class="form-control" name="assigned" data-style="btn-primary">
                        <c:forEach var="assignedUser"  items="${assignedMap}">
                            <c:choose>
                                <c:when test="${assignedUser.key eq param.assigned}">
                                    <option value="${assignedUser.key}" selected>${assignedUser.value}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${assignedUser.key}" >${assignedUser.value}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>

                    <select class="form-control" name="processed" data-style="btn-primary" title='Не выбрано...'>
                        <option value="0">Не выбрано </option>
                        <option value="-1">Не обработано </option>
                        <option value="-2">Успешно </option>
                        <option value="-3">Не успешно </option>
                        <option value="-4">Обработано </option>
                    </select>
                    <input type="hidden" name="eventId" value=${param['eventId']}>
                    <input type="submit" name="submit"  class="btn btn-primary" value="Выбрать">
                </div>
            </form>
            <table class="table table-bordered table-hover">
                <tr>
                    <td>${myIndex.index}номер по порядку</td>
                    <td>Уникальный номер</td>
                    <td>Название компании</td>
                    <td>Имя серетаря</td>
                    <td>Имя лица принимающего решение</td>
                    <td>Телефон секретаря</td>
                    <td>Телефон лица принимающего решение</td>
                    <td>Адрес</td>
                    <td>Пользователь</td>
                    <td>Статус</td>
                </tr>
                <c:forEach var="eventClient" items="${eventClientLink}" varStatus="myIndex">

                    <tr>
                        <td>${myIndex.count}</td>
                        <td>${eventClient.client.uniqueId}</td> 
                        <td>${eventClient.client.nameCompany}</td>
                        <td>${eventClient.client.nameSecretary}</td>  
                        <td>${eventClient.client.nameLpr} </td>  
                        <td>${eventClient.client.phoneSecretary} </td>  
                        <td>${eventClient.client.phoneLpr} </td>  
                        <td>${eventClient.client.address} </td>
                        <td> ${eventClient.user.surname}  ${eventClient.user.name}  </td>
                        <td>${eventClient.status}   </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </body>
</html>
