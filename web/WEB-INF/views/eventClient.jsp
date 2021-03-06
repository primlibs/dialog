<%-- 
    Document   : eventClient
    Created on : 08.05.2015, 16:04:44
    Author     : Юрий
--%>

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
            <span style="font-size: 18px;font-weight: 500;"><b>Кампания: ${campaign.name}; Сценарий: ${campaign.strategy.strategyName};</b></span>
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
                    <form class="form-inline btn-group" action="<c:url value="/Event/eventClientXLS" />" >
          <input type="hidden"  name="assigned" value="${param['assigned']}">
          <input type="hidden"  name="processed" value="${param['processed']}">
          <input type="hidden" name="campaignId" value=${param['campaignId']}>
          <input type="submit" name="submit" class="btn btn-primary" value="Скачать excel" >
        </form>
            </div>
            <table class="table table-bordered table-hover">
                <tr>
                    <th style="max-width: 3%;">№</th>
                    <th>Уникальный номер</th>
                    <th>Клиент наименование</th>
                    <th style="max-width: 10%;">Контактное лицо</th>
                    <th style="max-width: 10%;">Телефон К.Л.</th>
                    <th style="max-width: 10%;">Л.П.Р.</th>
                    <th style="max-width: 10%;">Телефон Л.П.Р.</th>
                    <th style="max-width: 10%;">Адрес</th>
                    <th style="max-width: 6%;">Пользователь</th>
                    <!--<th style="max-width: 4%;">Работать с эвентом</th>-->
                    <th style="max-width: 7%;">Дата установки статуса</th>
                    <th style="max-width: 5%;">Статус</th>
                </tr>
                <c:forEach var="event" items="${events}" varStatus="myIndex">
                    <c:set var="trstyle" value="danger" />
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
                            
                    <tr class="${trstyle}">
                        <td style="word-wrap: break-word;max-width: 3%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${myIndex.count}</a></td>
                        <td style="word-wrap: break-word;max-width: 50px !important;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.uniqueId}</a></td>
                        <!--<td style="cursor: pointer;" onclick="location = '<c:url value="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}"/>'">${myIndex.count}</td>
                        <td style="cursor: pointer;" onclick="location = '<c:url value="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}"/>'">${event.client.uniqueId}</td> -->
                        <td style="word-wrap: break-word;max-width: 50px !important;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.nameCompany}</a></td>
                        <td style="word-wrap: break-word;max-width: 10%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.nameSecretary}</a></td>   
                        <td style="word-wrap: break-word;max-width: 10%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.getFormattedPhoneSec()}</a></td>  
                        <td style="word-wrap: break-word;max-width: 10%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.nameLpr}</a></td> 
                        <td style="word-wrap: break-word;max-width: 10%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.getFormattedPhoneLpr()}</a></td>  
                        <td style="word-wrap: break-word;max-width: 10%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.client.address}</a></td>
                        <td style="word-wrap: break-word;max-width: 6%;"><div style="cursor: pointer;display: inline-block" ondblclick="location = '<c:url value="/Event/showAssigningOneEvent?eventId=${event.eventId}&campaignId=${campaign.campaignId}"/>'">${event.user.surname}  ${event.user.name}</div></td>
                       <!-- <c:if test="${event.isClosed()}">
                            <td style="word-wrap: break-word;max-width: 4%;"><span class="glyphicon glyphicon-circle-arrow-right" style="font-size:24pt;"></span></td>
                        </c:if>
                        <c:if test="${!event.isClosed()}">
                            <td style="word-wrap: break-word;max-width: 4%;"><a href="/Event/event?campaignId=${event.campaign.campaignId}&eventId=${event.eventId}"><span style="font-size:24pt;" class="glyphicon glyphicon-circle-arrow-right"></span></a></td>
                        </c:if>-->
                        <td style="word-wrap: break-word;max-width: 7%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}"><fmt:formatDate type="both" value="${event.getSetStatusDate()}"/></a></td>
                        <td style="word-wrap: break-word;max-width: 5%;"><a class="arow" href="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}">${event.getRusStatus()}</a></td>
                    </tr>
                </c:forEach>
            </table>
                    
                    <script>$('.arow').hover(function(){$(this).closest('tr').find('.arow').toggleClass('underlined')})</script>
    </body>
</html>
