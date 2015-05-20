<%-- 
    Document   : eventTask
    Created on : 30.04.2015, 13:15:22
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
    <body class="container" >

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
            <h4>  Евент:  ${campaign.name} &nbsp Стратегия: ${campaign.strategy.strategyName} </h4>

            <a href="<c:url value="/Event/getShapeExcel"/>" class="btn btn-large btn-primary" role="button">Получить форму excel</a>


            <form enctype="multipart/form-data" action="<c:url value="/Event/setXls" />" method="post">
                <div style="position:relative;">
                    <a class='btn btn-primary' href='javascript:;'>
                        Загрузить файл...
                        <input type="file" style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:transparent;color:transparent;' name="fileXls" size="40"  onchange='$("#upload-file-info").html($(this).val());'>
                    </a>
                    &nbsp;
                    <span class='label label-info' id="upload-file-info"></span>
                </div>
                Обновлять клиентов: <input  type="checkbox" name="checkbox" value="agree"> 
                <input type="hidden" name="campaignId" value=${param.campaignId}>
                <input class="btn btn-primary" type="submit" value="Отправить">
            </form>

            <table class="table table-bordered table-hover">

                <tr>
                    <td rowspan="2">Менеджеры </td>
                    <td rowspan="2" >Клиенты </td>
                    <td rowspan="2">Назначено </td>
                    <td rowspan="2">Не назначено </td>
                    <td colspan="3">Обработано</td>
                    <td rowspan="2">Не обработано </td>
                </tr>
                <tr>
                    <td>Успешно </td>
                    <td>Не успешно </td>
                    <td>Всего</td>
                </tr>
                <c:set var="number" value="1" />
                <c:set var="assignedEventsCount" value="0" />
                <c:forEach var="cabinetUser"  items="${cabinetUserList}" >
                    <tr>
                        <td >${cabinetUser.user.surname} ${cabinetUser.user.name} </td>
                        <c:if test="${number== 1}">
                            <td rowspan="${cabinetUserList.size()}" onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}"/>'" > ${eventList.size()} </td>
                        </c:if>
                        <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}"/>'"> ${userAssignedClient.get(cabinetUser.getUser().getUserId())} </td>
                        <c:set var="assignedEventsCount" value="${assignedEventsCount+userAssignedClient.get(cabinetUser.getUser().getUserId())}" />
                        <c:if test="${number== 1}">
                            <td rowspan="${cabinetUserList.size()}" onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-1"/>'" >${unassignedEventList.size()}  </td>
                        </c:if>
                        <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-2"/>'"> ${userAssignedClientProcessedSuccess.get(cabinetUser.getUser().getUserId())} </td>
                        <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-3"/>'" > ${userAssignedClientProcessedFails.get(cabinetUser.getUser().getUserId())} </td>
                        <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-4"/>'"> ${userAssignedClientProcessed.get(cabinetUser.getUser().getUserId())} </td>
                        <td  onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=${cabinetUser.getUser().getUserId()}&processed=-1"/>'" > ${userAssignedClientNotProcessed.get(cabinetUser.getUser().getUserId())} </td>

                    </tr>
                    <c:set var="number" value="${number+1}" />
                </c:forEach>

                <tr>
                    <td > Итого:</td>
                    <td >${eventList.size()}  </td>
                    <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-2"/>'">  ${assignedEventsCount} </td>
                    <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&assigned=-1"/>'">  ${unassignedEventList.size()}  </td>

                    <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-2"/>'">  </td>
                    <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-3"/>'">  </td>
                    <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-4"/>'"> </td>
                    <td onClick="location = '<c:url value="/Event/eventClient?campaignId=${campaign.campaignId}&processed=-1"/>'"> </td>
                </tr>
            </table>
    </body>
</html>
