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
        
        <div class="row ">
            <h3>Наименование: ${client.nameCompany}</h3>
            <h4>ИД: ${client.uniqueId}</h4>
            <h4>Контактное лицо: ${client.nameSecretary}, телефон: ${client.phoneSecretary}</h4>
            <h4>Принимает решения: ${client.nameLpr}, телефон: ${client.phoneLpr}</h4>
            <h4>Адрес: ${client.address}</h4>
            <h4>Инфо: ${client.comment}</h4>
            
            <h3>Запланированные контакты в рамках кампаний</h3>
            <c:if test="${empty unfinishedEvents}">
                Нет запланированных контактов
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
                </table>
            </c:if>
            <h3>История диалогов</h3>
            <c:if test="${empty finishedEvents}">
                Диалогов пока не было
            </c:if>
                <c:if test="${not empty finishedEvents}">
                    <table class="table table-bordered table-hover">
                    <tr><td>Кампания</td><td>Оператор</td><td>Инфо</td></tr>
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
            </body>
</html>