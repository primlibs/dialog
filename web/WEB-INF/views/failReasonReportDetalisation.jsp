<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        
        <div class="row form-group">
            <h2>Отчет по причинам.</h2>
            <c:set var="managerInfo" value="Все менеджеры"/>
            <span style="font-size: 18px;font-weight: 500;"><b>Кампания: ${campaign.name};</b></span>
            <form class="form-inline btn-group" action="<c:url value="/Event/failReasonReportDetalisationXLS" />" >
          <input type="hidden" name="campaignId" value=${campaign.campaignId}>
          <input type="hidden"  name="failReasonId" value="${failReasonId}">
          <input type="submit" name="submit" class="btn btn-primary" value="Скачать excel" >
        </form>
        </div>    
            <table class="table table-bordered table-hover">
                <tr class="active">
                    <th></th>
                    <th>Уникальный номер</th>
                    <th>Клиент</th>
                    <th>Контактное лицо</th>
                    <th>Телефон К.Л.</th>
                    <th>Л.П.Р.</th>
                    <th>Телефон Л.П.Р.</th>
                    <th>Адрес</th>
                    <th>Пользователь</th>
                    <th>Дата</th>
                    <th>Статус</th>
                </tr>
                <c:forEach var="event" items="${events}" varStatus="myIndex">
                    <c:if test="${0==event.status}">
                                <c:set var="trstyle" value="" />
                            </c:if>
                            <c:if test="${1==event.status}">
                                <c:set var="trstyle" value="active" />
                            </c:if>
                            <c:if test="${2==event.status}">
                                <c:set var="trstyle" value="info" />
                            </c:if>
                            <c:if test="${3==event.status}">
                                <c:set var="trstyle" value="success" />
                            </c:if>
                            <c:if test="${4==event.status}">
                                <c:set var="trstyle" value="danger" />
                            </c:if>
                    <tr style="cursor: pointer;" ondblclick="location = '<c:url value="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}"/>'" class="${trstyle}">
                        <td>${myIndex.count}</td>
                        <td>${event.client.uniqueId}</td> 
                        <td>${event.client.nameCompany}</td>
                        <td>${event.client.nameSecretary}</td>   
                        <td>${event.client.getFormattedPhoneSec()}</td>  
                        <td>${event.client.nameLpr}</td> 
                        <td>${event.client.getFormattedPhoneLpr()}</td>  
                        <td>${event.client.address}</td>
                        <td>${event.user.getShortName()}</td>
                        <td><fmt:formatDate type="both" value="${event.getSetStatusDate()}"/></td>
                        <td>${event.getRusStatus()}</td>
                    </tr>
                </c:forEach>
            </table>
    </body>
</html>
