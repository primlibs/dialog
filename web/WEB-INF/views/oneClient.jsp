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
            <h3>ИД: ${client.uniqueId}</h3>
            <h4>Контактное лицо: ${client.nameSecretary}, телефон: ${client.phoneSecretary}</h4>
            <h4>Принимает решения: ${client.nameLpr}, телефон: ${client.phoneLpr}</h4>
            <h4>Адрес: ${client.address}</h4>
            <h4>Инфо: ${client.comment}</h4>
            
            <h3>Запланированные контакты</h3>
            <c:if test="${empty unfinishedEvents}">
                Нет запланированных контактов
            </c:if>
            <c:if test="${not empty unfinishedEvents}">
                <c:forEach var="event"  items="${unfinishedEvents}" >
                    ${event.campaign.name}
                </c:forEach>
            </c:if>
            <h3>История диалогов</h3>
            <c:if test="${empty finishedEvents}">
                Нет диалогов
            </c:if>
                <c:if test="${not empty finishedEvents}">
                <c:forEach var="event"  items="${finishedEvents}" >
                    ${event.campaign.name}
                </c:forEach>
            </c:if>
        </div>
            </body>
</html>