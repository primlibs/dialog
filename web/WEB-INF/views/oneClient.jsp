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
        <div><%@include file="/WEB-INF/views/dialog.jsp" %></div>
        <div class="row" style="width: 50%;">
            
            <table>
                <tr><td>Наименование: </td><td>${client.nameCompany}</td></tr>
                <tr><td>Уникальный ИД: </td><td>${client.nameSecretary}</td></tr>
                <tr><td>Контактное лицо: </td><td>${client.nameLpr}</td></tr>
                <tr><td>Телефон: </td><td>${client.phoneSecretary}</td></tr>
                <tr><td>Принимает решения: </td><td>${client.nameCompany}</td></tr>
                <tr><td>Телефон: </td><td>${client.phoneLpr}</td></tr>
                <tr><td>Адрес: </td><td>${client.address}</td></tr>
                <tr><td>Инфо: </td><td>${client.comment}</td></tr>
                
                
            </table>
            
                <div>
            <h3>Контакты в рамках кампаний</h3>
            <c:if test="${empty unfinishedEvents && empty finishedEvents}">
                Нет контактов
            </c:if>
            <c:if test="${not empty unfinishedEvents}">
                <table class="table table-bordered table-hover">
                    <tr><td>Кампания</td><td>Оператор</td><td>Инфо</td></tr>
                <c:forEach var="event"  items="${unfinishedEvents}" >
                    <c:set var="assigned" value="-1" />
                    <c:if test="${ not empty event.user.userId}">
                        <c:set var="assigned" value="${event.user.userId}" />
                    </c:if>
                    <tr style="cursor: pointer;" onClick="location = '<c:url value="/Event/eventClient?campaignId=${event.campaign.campaignId}&assigned=${assigned}"/>'">
                        <td>${event.campaign.name}</td>
                        <c:if test="${not empty event.user}">
                        <td>${event.user.surname}</td>
                        </c:if>
                        <c:if test="${empty event.user}">
                        <td>Не назначен</td>
                        </c:if>
                        <td>${event.comment}</td>
                        </tr>
                </c:forEach>
                        
                        <c:forEach var="event"  items="${finishedEvents}" >
                    <tr style="cursor: pointer;"><td>${event.campaign.name}</td>
                        <c:if test="${not empty event.user}">
                        <td>${event.user.surname}</td>
                        </c:if>
                        <c:if test="${empty event.user}">
                        <td>Не назначен</td>
                        </c:if>
                        <td>${event.comment}</td>
                        </tr>
                </c:forEach>
                </table>
            </c:if>
                </div>
            
        </div>
            </body>
</html>