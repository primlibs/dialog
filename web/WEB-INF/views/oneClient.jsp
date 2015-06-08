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
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <div style="float: right;width: 49%;">
            <h4>Диалог: </h4>
            <%@include file="/WEB-INF/views/dialog.jsp" %></div>
        <div class="row" style="width: 49%;">

            <table>
                <tr><td>Наименование: </td><td>${client.nameCompany}</td></tr>
                <tr><td>Уникальный ИД: </td><td>${client.uniqueId}</td></tr>
                <tr><td>Контактное лицо: </td><td>${client.nameSecretary}</td></tr>
                <tr><td>Телефон: </td><td>${client.phoneSecretary}</td></tr>
                <tr><td>Принимает решения: </td><td>${client.nameLpr}</td></tr>
                <tr><td>Телефон: </td><td>${client.phoneLpr}</td></tr>
                <tr><td>Адрес: </td><td>${client.address}</td></tr>
                <tr><td>Инфо: </td><td>${client.comment}</td></tr>
                <tr><td>Тэги: </td><td>
                        <c:forEach var="ctl" items="${client.tagLinks}">
                            <div title='Удалить' class="tag" onclick="location = '<c:url value="/Client/deleteTag?ctlId=${ctl.linkId}&eventId=${event.eventId}&clientId=${client.clientId}"/>'">${ctl.tag.name}</div>
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
                                <td><div style="cursor: pointer;display: inline-block;" onClick="location = '<c:url value="/Event/eventClient?campaignId=${event.campaign.campaignId}"/>'">${event.campaign.name}</div></td>
                                <c:if test="${not empty event.user}">
                                    <td>${event.user.surname}</td>
                                </c:if>
                                <c:if test="${empty event.user}">
                                    <td>Не назначен</td>
                                </c:if>
                                <td>Не завершено</td>
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
                                <td><div style="cursor: pointer;display: inline-block;" onClick="location = '<c:url value="/Event/eventClient?campaignId=${event.campaign.campaignId}"/>'">${event.campaign.name}</div></td>
                                <td>${event.user.surname}</td>
                                <td><div style="cursor: pointer;display: inline-block;" onclick="location = '<c:url value="/Client/oneClient?clientId=${client.clientId}&eventId=${event.eventId}"/>'">${event.finalComment}</div></td>
                            </tr>
                        </c:forEach>
                    </table>

                </div>
            </c:if>  
        </div>
    </body>
</html>