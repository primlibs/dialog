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
    <body class="container">

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %>
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <script src="<c:url value="/js/myJsOnViews/campaignSpecification.js" />"></script>
        <script type="text/javascript">
            $(function () {
                //Установим для виджета русскую локаль с помощью параметра language и значения ru
                $('#datetimepicker2').datetimepicker(
                        {language: 'ru',
                            viewMode: 'days',
                            pickTime: false}
                );
                $('#datetimepicker1').datetimepicker(
                        {language: 'ru',
                            viewMode: 'days',
                            pickTime: false}
                );
            });
        </script>
        <div class="row form-group">
            <span style="font-size: 18px;font-weight: 500;"><b>Кампания: ${campaign.name}; Сценарий ${campaign.strategy.strategyName};</b></span>
        </div>
        

        <table class="table table-bordered table-hover" style="margin-top: 20px;">

            <tr>
                <th rowspan="2">Менеджеры </th>
                <th rowspan="2">Клиенты </th>
                <th rowspan="2">Назначено</th>
                <th rowspan="2">Не назначено</th>
                <th colspan="3">Завершено</th>
                <th rowspan="2">Не завершено</th>
            </tr>
            <tr>
                <th>Успешно</th>
                <th>Не успешно</th>
                <th>Всего</th>
            </tr>
            <c:set var="number" value="1" />
            <c:set var="assignedEventsCount" value="0" />
            <c:set var="assignedNotProcessedEventsCount" value="0" />
            <c:set var="assignedProcessedEventsCount" value="0" />
            <c:set var="assignedProcessedSuccessEventsCount" value="0" />
            <c:set var="assignedProcessedFailedEventsCount" value="0" />
            <c:forEach var="user"  items="${participatedUsers}" >

                <tr>
                    <td>${user.surname} ${user.name} </td>
                    <c:if test="${number== 1}">
                        <td class ="for" rowspan="${participatedUsers.size()}"><div style="cursor: pointer;display: inline-block;"  > ${eventList.size()} </div></td>
                    </c:if>
                    <td><div style="cursor: pointer;display: inline-block;" > ${userAssignedClient.get(user.getUserId())}</div></td>
                    <c:if test="${number== 1}">
                        <td rowspan="${participatedUsers.size()}"><div style="cursor: pointer;display: inline-block;"  >${unassignedEventList.size()}</div></td>
                        </c:if>
                    <td><div style="cursor: pointer;display: inline-block;" > ${userAssignedClientProcessedSuccess.get(user.getUserId())}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;"  > ${userAssignedClientProcessedFails.get(user.getUserId())}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" > ${userAssignedClientProcessed.get(user.getUserId())}</div></td>
                    <td><div style="cursor: pointer;display: inline-block;" > ${userAssignedClientNotProcessed.get(user.getUserId())}</div></td>
                        <c:set var="assignedEventsCount" value="${assignedEventsCount+userAssignedClient.get(user.getUserId())}" />
                        <c:set var="assignedNotProcessedEventsCount" value="${assignedNotProcessedEventsCount+userAssignedClientNotProcessed.get(user.getUserId())}" />
                        <c:set var="assignedProcessedEventsCount" value="${assignedProcessedEventsCount+userAssignedClientProcessed.get(user.getUserId())}" />
                        <c:set var="assignedProcessedSuccessEventsCount" value="${assignedProcessedSuccessEventsCount+userAssignedClientProcessedSuccess.get(user.getUserId())}" />
                        <c:set var="assignedProcessedFailedEventsCount" value="${assignedProcessedFailedEventsCount+userAssignedClientProcessedFails.get(user.getUserId())}" />

                </tr>
                <c:set var="number" value="${number+1}" />
            </c:forEach>

            <tr>
                <td> Итого:</td>
                <td><div style="cursor: pointer;display: inline-block;" >${eventList.size()}</div></td>
                <td><div style="cursor: pointer;display: inline-block;" >  ${assignedEventsCount}</div></td>
                <td><div style="cursor: pointer;display: inline-block;" >  ${unassignedEventList.size()}</div></td>

                <td><div style="cursor: pointer;display: inline-block;" > ${assignedProcessedSuccessEventsCount}</div></td>
                <td><div style="cursor: pointer;display: inline-block;" > ${assignedProcessedFailedEventsCount}</div></td>
                <td><div style="cursor: pointer;display: inline-block;" > ${assignedProcessedEventsCount}</div></td>
                <td><div style="cursor: pointer;display: inline-block;" > ${assignedNotProcessedEventsCount}</div></td>
               
            </tr>
        </table>


        <h5 id="moduleReportTumblr" style="cursor: pointer;">Отчет по модулям</h5> 
        <c:if test="${empty moduleReportData}">
            <span id="moduleReport" class="hidden">Нет данных для отчета</span>
        </c:if>
        <c:if test="${not empty moduleReportData}">
            <table id="moduleReport" class="table table-bordered table-hover hidden" style="margin-top: 20px;">
                <tr>
                    <th> Модуль </th>
                    <th> Отрицательные исходы(%*) </th></tr>
                        <c:forEach var="entry" items="${moduleReportData.entrySet()}">

                    <tr style="cursor: pointer;" ><td>${entry.getKey().getModuleName()}</td>
                        <td>${entry.getValue()}</td></tr>

                </c:forEach>
            </table>
        </c:if>
        <h5 id="workReportTumblr" style="cursor: pointer;">Отчет по работе</h5> 
        <c:if test="${empty workReportData}">
            <span id="workReport">Нет данных для отчета</span>
        </c:if>
        <c:if test="${not empty workReportData}">
            <c:set var="hidingclass" value="hidden"></c:set>
            <c:if test="${wropen==1}">
                <c:set var="hidingclass" value=""></c:set>
            </c:if>
            <div id="workReport" class="${hidingclass}">
                <form class="form form-group form-inline">
                    <div class="form-group">
                        С <div class="input-group date">
                            <input type="text" name="dateFrom" id="datetimepicker1" class="form-control" placeholder="дата с " value="${dateFrom}"/>
                        </div>
                        ПО <div class="input-group date">
                            <input type="text" name="dateTo" id="datetimepicker2" class="form-control" placeholder="дата по " value="${dateTo}"/>
                        </div>

                    </div> 
                    <input type="hidden" name="campaignId" value=${campaign.campaignId}>
                    <input type="hidden" name="wropen" value="0">
                    <input class="btn btn-primary" type="submit" value="Применить">
                </form>
                <table class="table table-bordered table-hover" style="margin-top: 20px;">
                    <th>Менеджеры</th>
                    <!--<th>Назначено</th>-->
                    <th>Перенесено</th>
                    <th>Не успешно</th>
                    <th>Успешно</th>
                    <th>Все</th>
                        <c:forEach var="entry" items="${workReportData.entrySet()}">
                            <c:if test="${not empty entry.getKey()}">
                            <tr>
                                <td>${entry.getKey().getShortName()}</td>
                                <td style="cursor: pointer;" >${entry.getValue().get("postponed")}</td>
                                <td style="cursor: pointer;" >${entry.getValue().get("failed")}</td>
                                <td style="cursor: pointer;" >${entry.getValue().get("successful")}</td>
                                <td style="cursor: pointer;" >${entry.getValue().get("all")}</td></tr>
                            </c:if>
                        </c:forEach>
                    <tr>
                        <td>Итого:</td>
                        <td style="cursor: pointer;" >${workReportData.get(null).get("postponed")}</td>
                        <td style="cursor: pointer;" >${workReportData.get(null).get("failed")}</td>
                        <td style="cursor: pointer;" >${workReportData.get(null).get("successful")}</td>
                        <td style="cursor: pointer;" >${workReportData.get(null).get("all")}</td></tr>
                </table>
            </div>
        </c:if>
        <h5 id="failReasonReportTumblr" style="cursor: pointer;">Отчет по причинам</h5> 
        <c:if test="${empty failReasonReportData}">
            <span id="failReasonReport" class="hidden">Нет данных для отчета</span>
        </c:if>
        <c:if test="${not empty failReasonReportData}">
            <table id="failReasonReport" class="table table-bordered table-hover hidden" style="margin-top: 20px;">
                <tr><th>Причина</th>
                    <th>Количество</th></tr>
                        <c:forEach var="entry" items="${failReasonReportData.entrySet()}">
                            <c:if test="${not empty entry.getKey()}">
                        <tr style="cursor: pointer;" ><td>${entry.getKey().getName()}</td>
                            <td>${entry.getValue()}</td></tr>
                        </c:if>
                    </c:forEach>
                <tr style="cursor: pointer;" >
                    <td>Всего:</td>
                    <td>${failReasonReportData.get(null)}</td></tr>
            </table>
        </c:if>
    </body>


</html>
