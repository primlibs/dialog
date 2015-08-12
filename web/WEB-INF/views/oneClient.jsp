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
        <!--<script src="<c:url value="/js/myJsOnViews/updateParam.js" />"></script>-->
        <div style="float: right;width: 49%;">
            
            <c:if test="${not empty dialogEvent}">
                    <br>
                    <b>РЕЗУЛЬТАТ:</b> ${dialogEvent.getRusStatus()}; 
                    <br>
                    <c:if test="${not empty dialogEvent.successDate}"><b>ДАТА НАЗНАЧЕНИЯ:</b> <fmt:formatDate type="both" value="${dialogEvent.successDate}"/>
                        <br></c:if>
                    <c:if test="${not empty dialogEvent.finalComment}"><b>ИТОГОВЫЙ КОММЕНТАРИЙ:</b> ${dialogEvent.finalComment}
                        <br></c:if>
                    <!--<b>ПоследнКомментарий: </b> ${dialogEvent.comment}
                    <br>-->
                    
            </c:if>
            <h4>Диалог: </h4>
            <c:set var="num" value="0"/>
    
            <c:if test="${not empty messages}">
            <table class="table table-hover table-striped">
                <c:forEach var="message" items="${messages}">
                    <c:set var="num" value="${num+1}"/>
                    <c:set var="trstyle" value="background-color: #dff0d8;"/>
                    <c:if test="${num%2 eq 0}">
                        <c:set var="trstyle" value="background-color: ivory;"/>
                    </c:if>
                    <tr style="${trstyle}"><td>${num}.</td><td>${message[0]}</td></tr>
                </c:forEach>
                
                <!--<c:forEach var="module" items="${dialog}">
                    <c:set var="num" value="${num+1}"/>
                    <tr style="background-color: #dff0d8;"><td>${num}.</td><td>Клиент: ${module.moduleName}</td></tr>
                    <tr style="background-color: ivory;"><td>${num}.</td><td>Оператор: ${module.bodyText}</td></tr>
                </c:forEach>-->
            </table>
            </c:if>
        </div>
        <div class="row" style="width: 49%;">

            <table>
                <tr><td>Наименование: </td><td class="changebleParam" data-method="updateclient" data-clientid="${client.clientId}" name="companyname_${client.clientId}" data-parametr="nameCompany">${client.nameCompany}</td></tr>
                <tr><td>Уникальный ИД: </td><td>${client.uniqueId}</td></tr>
                <tr><td>Контактное лицо: </td><td class="changebleParam" data-method="updateclient" data-clientid="${client.clientId}" name="nameSecretary_${client.clientId}" data-parametr="nameSecretary">${client.nameSecretary}</td></tr>
                <tr><td>Телефон: </td><td class="changebleParam" data-method="updateclient" data-clientid="${client.clientId}" name="phoneSecretary_${client.clientId}" data-parametr="phoneSecretary">${client.getFormattedPhoneSec()}</td></tr>
                <tr><td>Принимает решения: </td><td class="changebleParam" data-method="updateclient" data-clientid="${client.clientId}" name="nameLpr_${client.clientId}" data-parametr="nameLpr">${client.nameLpr}</td></tr>
                <tr><td>Телефон: </td><td class="changebleParam" data-method="updateclient" data-clientid="${client.clientId}" name="phoneLpr_${client.clientId}" data-parametr="phoneLpr">${client.getFormattedPhoneLpr()}</td></tr>
                <tr><td>Адрес: </td><td class="changebleParam" data-method="updateclient" data-clientid="${client.clientId}" name="address_${client.clientId}" data-parametr="address">${client.address}</td></tr>
                <tr><td>Тэги: </td><td>
                        <c:forEach var="tag" items="${client.tags}">
                            <div title='Удалить' class="tag" ondblclick="location = '<c:url value="/Client/deleteTag?tagId=${tag.tagId}&eventId=${event.eventId}&clientId=${client.clientId}"/>'">${tag.name}</div>
                        </c:forEach>
                    </td></tr>
            </table>


            <div style="margin-bottom: 10px;margin-top: 10px;">
                <form role="form" class="form-inline" action="<c:url value="/Client/addTag" />"  method="post"> 
                    <c:set var="selectSize" value="${possibleTagsToAdd.size()}"/>
                    <c:if test="${possibleTagsToAdd.size()>5}">
                        <c:set var="selectSize" value="5"/>
                    </c:if>
                    <c:if test="${possibleTagsToAdd.size()==0}">
                        <c:set var="selectSize" value="1"/>
                    </c:if>

                    <select size="${selectSize}" name="tags" multiple="multiple" style="vertical-align: middle;">

                        <c:if test="${not empty possibleTagsToAdd}">
                            <c:forEach var="tag" items="${possibleTagsToAdd}">
                                <option value="${tag.tagId}">${tag.name}</option>
                            </c:forEach>
                        </c:if>

                        <c:if test="${empty possibleTagsToAdd}">
                            <option value="0">Нет доступных тэгов</option>
                        </c:if>
                    </select>
                    <input type="hidden" name="clientId" value=${client.clientId}>
                    <button type="submit" name="submit" class="btn btn-primary"> Добавить тэг </button>
                </form>
            </div>


            <div>
                <h3>Контакты в рамках кампаний</h3>
                <c:if test="${empty unfinishedEvents && empty finishedEvents}">
                    Нет контактов
                </c:if>
                <c:if test="${not empty unfinishedEvents || not empty finishedEvents}">
                    <table class="table table-bordered table-hover">
                        <tr><td>Кампания</td><td>Оператор</td><td>Инфо</td></tr>
                        <c:forEach var="event"  items="${unfinishedEvents}" >
                            <c:set var="assigned" value="-1" />
                            <c:if test="${ not empty event.user.userId}">
                                <c:set var="assigned" value="${event.user.userId}" />
                            </c:if>
                            <tr class="active">
                                <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${event.campaign.campaignId}"/>'">${event.campaign.name}</div></td>
                                <c:if test="${not empty event.user}">
                                    <td>${event.user.surname} ${event.user.name} - ${event.user.email}</td>
                                </c:if>
                                <c:if test="${empty event.user}">
                                    <td>Не назначен</td>
                                </c:if>
                                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Client/oneClient?clientId=${client.clientId}&eventId=${event.eventId}"/>'">Не завершено</div></td>
                            </tr>
                        </c:forEach>

                        <c:forEach var="event"  items="${finishedEvents}" >
                            <c:set var="trstyle" value="active" />
                            <c:if test="${ not empty event.failReason}">
                                <c:set var="trstyle" value="danger" />
                            </c:if>
                            <c:if test="${ not empty event.successDate}">
                                <c:set var="trstyle" value="success" />
                            </c:if>
                            <tr class="${trstyle}">
                                <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${event.campaign.campaignId}"/>'">${event.campaign.name}</div></td>
                                <td>${event.user.surname}</td>
                                <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Client/oneClient?clientId=${client.clientId}&eventId=${event.eventId}"/>'">${event.getRusStatus()}</div></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if> 
            </div>
        </div>
    </body>
</html>