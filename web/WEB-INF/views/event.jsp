<%-- 
    Document   : event
    Created on : 18.05.2015, 18:09:46
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
    <body class="container">

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
       <h3>   Стратегия: ${strategy.strategyName}</h3>
            <div class="row">
                <div class="col-md-12">
                    <input type="hidden" name="eventId" value=${event.eventId}>
                    <table>
                        <tr><td>Адрес: </td><td>${event.client.address}</td></tr>
                        <tr><td>Секретарь: </td><td>${event.client.nameSecretary}</td></tr>
                        <tr><td>Телефон секретаря:  </td><td>${event.client.phoneSecretary}</td></tr>
                        <tr><td>Принимает решения: </td><td>${event.client.nameLpr}</td></tr>
                        <tr><td>Телефон: </td><td>${event.client.phoneLpr}</td></tr>
                        <tr><td>Комментарий: </td><td>${event.client.comment}</td></tr>
                    </table>

                    <div class="row">
                        <div class="col-md-6">
                            ${module.moduleName}
                            <br>
                            <br>
                            ${module.bodyText}
                        </div>
                        <div class="col-md-4"  data-spy="scroll" style="height: 100vh ; line-height: 1em;">
                            <c:forEach var="аctiveM"  items="${аctiveMap}" >
                                <ul> ${аctiveM.key.groupName} 
                                    <c:forEach var="moduleL"  items="${аctiveM.value}" >
                                        <li> 
                                            <a href="<c:url value="/Event/eventProcessing?campaignId=${param.campaignId}&groupId=${аctiveM.key.groupId}&moduleId=${moduleL.moduleId}&eventId=${event.eventId}"/>">${moduleL.moduleName}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:forEach>
                        </div>
                        <div class="col-md-2"  >
                            <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-danger" role="button">Слив звонка</a>
                            <p>    <a href="<c:url value="/Event/#?eventId=${event.eventId}"/>" class="btn btn-large btn-success" role="button">Положительный результат</a>
                            <p>    <a href="<c:url value="/Event/event?campaignId=${param.campaignId}&strategyId=${campaign.strategy.strategyId}"/>" class="btn btn-large btn-primary" role="button">следующий клиент</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
