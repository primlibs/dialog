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
        <script type="text/javascript">
            $(function() {
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
        <div class="row form-group">
            <form style="float: left;" class="form-inline" enctype="multipart/form-data" action="<c:url value="/Event/setXls" />" method="post">
                <div class="form-group"> 
                    <a href="<c:url value="/Event/getShapeExcel"/>" class="btn btn-large btn-primary" role="button">Получить форму excel</a>
                </div>
                <div class="form-group">
                    <input type="file"  name="fileXls" size="40"  onchange='$("#upload-file-info").html($(this).val());'>
                    <span class='label label-info' id="upload-file-info"></span>
                </div>
                <c:if test="${not empty tags}">
                    <c:set var="size" value="5"/>
                    <c:if test="${tags.size()<5}">
                    <c:set var="size" value="${tags.size()}"/>
                    </c:if>
                <div class="form-group">
                    Тэги:
                    <select multiple name="tagIds" size="${size}" class="form-control">
                        <c:forEach var="tag" items="${tags}">
                        <option value="${tag.tagId}">${tag.name}</option>
                        </c:forEach>
                    </select>
                </div>
                </c:if>
                <div class="form-group">
                    <input  type="checkbox" name="checkbox" value="agree"> &nbsp;Обновлять клиентов 
                </div>
                <div class="form-group">
                    <input type="hidden" name="campaignId" value=${param.campaignId}>
                    <input class="btn btn-primary" type="submit" value="Добавить">
                </div>
            </form>
            
            
        </div>
            <div class="row form-group">
            <div class="checkbox">
        <label>
            <input id="showModulesWithText" type="checkbox" data-campaignid="${campaign.campaignId}" value="${campaign.getShowModulesWithTextCheck()}" ${campaign.getShowModulesWithTextCheck()}> Показывать полный текст модулей
        </label>
            </div>
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

        <h5 id="moduleReportTumblr" style="cursor: pointer;">Отчет по модулям</h5> 
        <c:if test="${empty moduleReportData}">
            <span id="moduleReport">Нет данных для отчета</span>
        </c:if>
        <c:if test="${not empty moduleReportData}">
            <table id="moduleReport" class="table table-bordered table-hover hidden" style="margin-top: 20px;">
                <tr>
                    <th> Модуль </th>
                    <th> Отрицательные исходы(%*) </th></tr>
                        <c:forEach var="entry" items="${moduleReportData.entrySet()}">
                            
                    <tr style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/moduleReportDetalisation?campaignId=${campaign.campaignId}&moduleId=${entry.getKey().moduleId}"/>'"><td>${entry.getKey().getModuleName()}</td>
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
                        <c:forEach var="entry" items="${workReportData.entrySet()}">
                        <c:if test="${not empty entry.getKey()}">
                        <tr >
                            <td>${entry.getKey().getShortName()}</td>
                            <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/workReportDetalisation?campaignId=${campaign.campaignId}&userId=${entry.getKey().getId()}&dateFrom=${dateFrom}&dateTo=${dateTo}&status=1"/>'">${entry.getValue().get("postponed")}</td>
                            <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/workReportDetalisation?campaignId=${campaign.campaignId}&userId=${entry.getKey().getId()}&dateFrom=${dateFrom}&dateTo=${dateTo}&status=4"/>'">${entry.getValue().get("failed")}</td>
                            <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/workReportDetalisation?campaignId=${campaign.campaignId}&userId=${entry.getKey().getId()}&dateFrom=${dateFrom}&dateTo=${dateTo}&status=5"/>'">${entry.getValue().get("successful")}</td></tr>
                        </c:if>
                        </c:forEach>
                        <tr >
                        <td>Итого:</td>
                            <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/workReportDetalisation?campaignId=${campaign.campaignId}&userId=&dateFrom=${dateFrom}&dateTo=${dateTo}&status=1"/>'">${workReportData.get(null).get("postponed")}</td>
                            <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/workReportDetalisation?campaignId=${campaign.campaignId}&userId=&dateFrom=${dateFrom}&dateTo=${dateTo}&status=4"/>'">${workReportData.get(null).get("failed")}</td>
                            <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/workReportDetalisation?campaignId=${campaign.campaignId}&userId=&dateFrom=${dateFrom}&dateTo=${dateTo}&status=5"/>'">${workReportData.get(null).get("successful")}</td></tr>
                </table>
            </div>
        </c:if>
    </body>
</html>
