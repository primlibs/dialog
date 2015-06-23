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

        <div class="row ">
            <h4>Перенесенные контакты</h4>
        </div>
            <div class="row">
                <table class="table table-bordered table-hover">
                    <tr>
                        <th></th>
                        <th>Дата</th>
                        <th>Кампания</th>
                        <th>Сценарий</th>
                    </tr>
                    <c:forEach var="event" items="${postponedEvents}" varStatus="myIndex">
                        <tr>
                            <td>${myIndex.count}</td>
                            <td>${event.postponedDate}</td>
                            <td>${event.campaign.name}</td>
                            <td>${event.campaign.strategy.strategyName}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
    </body>
</html>