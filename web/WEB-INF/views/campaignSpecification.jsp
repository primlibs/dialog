<%-- 
    Document   : campaignSpecification
    Created on : 30.04.2015, 13:15:22
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
            <div class="row form-group">
                <form style="float: left;" class="form-inline" enctype="multipart/form-data" action="<c:url value="/Event/setXls" />" method="post">
                <div class="form-group"> 
                    <a href="<c:url value="/Event/getShapeExcel"/>" class="btn btn-large btn-primary" role="button">Получить форму excel</a>
                </div>
                    <div class="form-group">
                        <input type="file"  name="fileXls" size="40"  onchange='$("#upload-file-info").html($(this).val());'>
                        <span class='label label-info' id="upload-file-info"></span>
                    </div>
                    <div class="form-group">
                       <input  type="checkbox" name="checkbox" value="agree"> &nbsp;обновлять клиентов 
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="campaignId" value=${param.campaignId}>
                        <input class="btn btn-primary" type="submit" value="Добавить">
                    </div>
            </form>
                        <a style="margin-left: 10px;" href="<c:url value="/Event/eventShowAllAppoint?campaignId=${campaign.campaignId}"/>" class="btn btn-primary" role="button">Распределить клиентов</a>
                        <c:if test="${deleteble==true}">
                            <a style="float: right;" href="<c:url value="/Event/deleteCampaign?campaignId=${campaign.campaignId}"/>" class="btn btn-large btn-danger" role="button">Удалить кампанию</a>           
                        </c:if>
                        </div>

            <table class="table table-bordered table-hover" style="margin-top: 20px;">

                <tr>
                    <td rowspan="2">Менеджеры </td>
                    <td rowspan="2">Клиенты </td>
                    <td rowspan="2">Назначено</td>
                    <td rowspan="2">Не назначено</td>
                    <td colspan="3">Обработано</td>
                    <td rowspan="2">Не обработано</td>
                </tr>
                <tr>
                    <td>Успешно </td>
                    <td>Не успешно </td>
                    <td>Всего</td>
                </tr>
                <c:set var="number" value="1" />
                <c:set var="assignedEventsCount" value="0" />
                <c:set var="assignedNotProcessedEventsCount" value="0" />
                <c:set var="assignedProcessedEventsCount" value="0" />
                <c:set var="assignedProcessedSuccessEventsCount" value="0" />
                <c:set var="assignedProcessedFailedEventsCount" value="0" />
                <c:forEach var="cabinetUser"  items="${cabinetUserList}" >
                    <tr>
                        <td>${cabinetUser.user.surname} ${cabinetUser.user.name} </td>
                        <c:if test="${number== 1}">
                            <td><div style="cursor: pointer;display: inline-block;" rowspan="${cabinetUserList.size()}" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}"/>'" > ${eventList.size()} </div></td>
                        </c:if>
                            <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}"/>'"> ${userAssignedClient.get(cabinetUser.getUser().getUserId())}</div></td>
                        <c:if test="${number== 1}">
                            <td rowspan="${cabinetUserList.size()}" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-1"/>'" >${unassignedEventList.size()}  </td>
                        </c:if>
                            <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-2"/>'"> ${userAssignedClientProcessedSuccess.get(cabinetUser.getUser().getUserId())}</div></td>
                            <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-3"/>'" > ${userAssignedClientProcessedFails.get(cabinetUser.getUser().getUserId())}</div></td>
                        <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-4"/>'"> ${userAssignedClientProcessed.get(cabinetUser.getUser().getUserId())}</div></td>
                        <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-1"/>'" > ${userAssignedClientNotProcessed.get(cabinetUser.getUser().getUserId())}</div></td>
                        <c:set var="assignedEventsCount" value="${assignedEventsCount+userAssignedClient.get(cabinetUser.getUser().getUserId())}" />
                        <c:set var="assignedNotProcessedEventsCount" value="${assignedNotProcessedEventsCount+userAssignedClientNotProcessed.get(cabinetUser.getUser().getUserId())}" />
                        <c:set var="assignedProcessedEventsCount" value="${assignedProcessedEventsCount+userAssignedClientProcessed.get(cabinetUser.getUser().getUserId())}" />
                        <c:set var="assignedProcessedSuccessEventsCount" value="${assignedProcessedSuccessEventsCount+userAssignedClientProcessedSuccess.get(cabinetUser.getUser().getUserId())}" />
                        <c:set var="assignedProcessedFailedEventsCount" value="${assignedProcessedFailedEventsCount+userAssignedClientProcessedFails.get(cabinetUser.getUser().getUserId())}" />

                    </tr>
                    <c:set var="number" value="${number+1}" />
                </c:forEach>

                <tr>
                    <td> Итого:</td>
                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=0"/>'">${eventList.size()}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-2"/>'">  ${assignedEventsCount}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-1"/>'">  ${unassignedEventList.size()}</div></td>

                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-2"/>'"> ${assignedProcessedSuccessEventsCount}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-3"/>'"> ${assignedProcessedFailedEventsCount}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-4"/>'"> ${assignedProcessedEventsCount}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-1"/>'"> ${assignedNotProcessedEventsCount}</div></td>
                </tr>
            </table>
        
    </body>
</html>
