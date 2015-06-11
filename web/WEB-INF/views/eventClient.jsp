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
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        
        <div class="row form-group">
            <span style="font-size: 18px;font-weight: 500;"><b>Кампания: ${campaign.name}; Стратегия: ${campaign.strategy.strategyName};</b></span>
        </div>    
            <!--<div class="form-group" role="group" >
                <a href="<c:url value="/Event/campaignSpecification?campaignId=${param['campaignId']}"/>" class="btn btn-large" role="button"><= Кампания</a>
            </div>-->
            <div class="form-group">
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
                        <c:forEach var="processedEvent"  items="${proceededMap}">
                            <c:choose>
                                <c:when test="${processedEvent.key eq param.processed}">
                                    <option value="${processedEvent.key}" selected>${processedEvent.value}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${processedEvent.key}" >${processedEvent.value}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="campaignId" value=${param['campaignId']}>
                    <input type="submit" name="submit"  class="btn btn-primary" value="Выбрать">
                </div>
            </form>
            </div>
            <table class="table table-bordered table-hover">
                <tr>
                    <td>${myIndex.index}номер по порядку</td>
                    <td>Уникальный номер</td>
                    <td>Название компании</td>
                    <td>Контактное лицо</td>
                    <td>Телефон К.Л.</td>
                    <td>Л.П.Р.</td>
                    <td>Телефон Л.П.Р.</td>
                    <td>Адрес</td>
                    <td>Пользователь</td>
                    <td>Статус</td>
                </tr>
                <c:forEach var="event" items="${events}" varStatus="myIndex">

                    <tr>
                        <td>${myIndex.count}</td>
                        <td>${event.client.uniqueId}</td> 
                        <td>${event.client.nameCompany}</td>
                        <td>${event.client.nameSecretary}</td>   
                        <td>${event.client.getFormattedPhoneSec()} </td>  
                        <td>${event.client.nameLpr} </td> 
                        <td>${event.client.getFormattedPhoneLpr()} </td>  
                        <td>${event.client.address} </td>
                        <td><div style="cursor: pointer;display: inline-block" ondblclick="location = '<c:url value="/Event/showAssigningOneEvent?eventId=${event.eventId}&campaignId=${campaign.campaignId}"/>'">${event.user.surname}  ${event.user.name}</div></td>
                        <td>${event.status} </td>
                    </tr>
                </c:forEach>
            </table>
                    
        
    </body>
</html>
