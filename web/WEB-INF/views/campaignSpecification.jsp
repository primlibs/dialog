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
<script src="<c:url value="/js/myJsOnViews/campaignSpecification.js" />"></script>
        <div class="row form-group">
            <span style="font-size: 18px;font-weight: 500;"><b>Кампания: ${campaign.name}; Сценарий ${campaign.strategy.strategyName};</b></span>
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
                    <th rowspan="2">Менеджеры </th>
                    <th rowspan="2">Клиенты </th>
                    <th rowspan="2">Назначено</th>
                    <th rowspan="2">Не назначено</th>
                    <th colspan="3">Завершено</th>
                    <th rowspan="2">Не завершено</th>
                    <th rowspan="2">Изменить назначение</th>
                </tr>
                <tr>
                    <th>Успешно </th>
                    <th>Не успешно </th>
                    <th>Всего</th>
                </tr>
                <c:set var="number" value="1" />
                <c:set var="assignedEventsCount" value="0" />
                <c:set var="assignedNotProcessedEventsCount" value="0" />
                <c:set var="assignedProcessedEventsCount" value="0" />
                <c:set var="assignedProcessedSuccessEventsCount" value="0" />
                <c:set var="assignedProcessedFailedEventsCount" value="0" />
                <c:forEach var="cabinetUser"  items="${participatedCUsers}" >
                    <tr>
                        <td>${cabinetUser.user.surname} ${cabinetUser.user.name} </td>
                        <c:if test="${number== 1}">
                            <td class ="for" rowspan="${participatedCUsers.size()}"><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}"/>'" > ${eventList.size()} </div></td>
                        </c:if>
                            <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}"/>'"> ${userAssignedClient.get(cabinetUser.getUser().getUserId())}</div></td>
                        <c:if test="${number== 1}">
                            <td rowspan="${participatedCUsers.size()}"><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-1"/>'" >${unassignedEventList.size()}</div></td>
                        </c:if>
                            <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-2"/>'"> ${userAssignedClientProcessedSuccess.get(cabinetUser.getUser().getUserId())}</div></td>
                            <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-3"/>'" > ${userAssignedClientProcessedFails.get(cabinetUser.getUser().getUserId())}</div></td>
                        <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-4"/>'"> ${userAssignedClientProcessed.get(cabinetUser.getUser().getUserId())}</div></td>
                        <td><div style="cursor: pointer;display: inline-block;" ondblclick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-1"/>'" > ${userAssignedClientNotProcessed.get(cabinetUser.getUser().getUserId())}</div></td>
                        <td><a href="#" class="btn btn-large btn-warning changeAssignFromSpec" id="changeAssignFromSpec" 
                                        data-userid="${cabinetUser.user.userId}"
                                        data-toggle="modal"
                                        data-target="#basicModalChangeAssign">Изменить</a></td>
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
                    <td><a href="#" class="btn btn-large btn-warning changeAssignFromSpec" id="changeAssignFromSpec" 
                                        data-userid=""
                                        data-toggle="modal"
                                        data-target="#basicModalChangeAssign">Изменить</a></td>
                </tr>
            </table>
                    
                    
                    <div class="modal fade" id="basicModalChangeAssign" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Назначение</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="reassignEvents" action="<c:url value="/Event/changeUserCampaignAssignation" />" method="post">
                                                    <select name="userToId">
                                                        <option value="">Снять назначения с удалением информации о переносах</option>
                                                        <c:forEach var="cabinetUser"  items="${cabinetUserList}" >
                                                            <option value="${cabinetUser.user.userId}">${cabinetUser.user.surname} ${cabinetUser.user.name} - ${cabinetUser.user.email}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="hidden" name="campaignId" value=${campaign.campaignId}>
                                                    <input type="hidden" id="userFromId" name="userFromId" value="">
                                                    <p>    <input class="btn btn-primary" type="submit" value="Изменить">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
        
    </body>
</html>
