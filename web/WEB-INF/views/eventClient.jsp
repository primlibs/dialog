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
            <form enctype="multipart/form-data" action="<c:url value="/Event/#" />" method="post">
                <div class="btn-group bootstrap-select">
                    <select class="selectpicker" data-style="btn-primary" title='Не выбрано...'>
                        <option value="">Не назначено </option>
                        <option value="">Назначено </option>
                        <c:forEach var="cabinetUser"  items="${cabinetUserList}" varStatus="myIndex">
                            <option value="${cabinetUser.user.userId}">${cabinetUser.user.surname} &nbsp ${cabinetUser.user.name}</option>
                        </c:forEach>
                    </select>

                    <select class="selectpicker" data-style="btn-primary" title='Не выбрано...'>
                        <option value="">Не обработано </option>
                        <option value="">Успешно </option>
                        <option value="">Не успешно </option>
                        <option value="">Обработано </option>
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
                <c:forEach var="client" items="${clientList}" varStatus="myIndex">

                    <tr>
                        <td>${myIndex.count}</td>
                        <td>${client.uniqueId}</td> 
                        <td>${client.nameCompany}</td>
                        <td>${client.nameSecretary}</td>  
                        <td>${client.nameLpr} </td>  
                        <td>${client.phoneSecretary} </td>  
                        <td>${client.phoneLpr} </td>  
                        <td >${client.address} </td>
                         <td> </td>
                    <td> </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </body>
</html>
