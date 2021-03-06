<%-- 
    Document   : event
    Created on : 18.05.2015, 18:09:46
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
    <body class="container">
        <script src="<c:url value="/js/myJsOnViews/event.js" />"></script>
        <script type="text/javascript">
            $(function () {
                //Установим для виджета русскую локаль с помощью параметра language и значения ru
                $('#datetimepicker2').datetimepicker(
                        {language: 'ru'}
                );
                $('#datetimepicker1').datetimepicker(
                        {language: 'ru'}
                );
            });
        </script>

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row form-group">
            <span style="font-size: 18px;">   Сценарий: ${strategy.strategyName}</span><c:if test="${not empty event.postponedDate}">
                <span> Звонок был перенесен на <fmt:formatDate type="both" value="${event.postponedDate}"/></span>
                            </c:if>
        </div>
            <div class="row">
                    <!--<input type="hidden" name="eventId" id="eventId" value=${event.eventId}>-->
                    <table class="table table-striped table-hover">
                        <tr>
                            <th>Клиент </th>
                            <th>Адрес </th>
                            <th>Контактное лицо </th>
                            <th>Телефон К.Л. </th>
                            <th>Л.П.Р. </th>
                            <th>Телефон Л.П.Р. </th>
                            <th>Комментарий </th>
                        </tr>
                        <tr>
                            <td>${event.client.nameCompany} </td>
                            <td class="changebleEventParam" id="address" data-eventId="${event.eventId}">${event.client.address} </td>
                            <td class="changebleEventParam" id="nameSecretary" data-eventId="${event.eventId}">${event.client.nameSecretary} </td>
                            <td class="changebleEventParam" id="phoneSecretary" data-eventId="${event.eventId}">${event.client.getFormattedPhoneSec()} </td>
                            <td class="changebleEventParam" id="nameLpr" data-eventId="${event.eventId}">${event.client.nameLpr} </td>
                            <td class="changebleEventParam" id="phoneLpr" data-eventId="${event.eventId}">${event.client.getFormattedPhoneLpr()} </td>
                            <td class="changebleEventParam" id="comment" data-eventId="${event.eventId}">${event.comment} </td>
                        </tr>
                    </table>
                            
                    <div class="row">
                        <c:if test="${not empty campaign.showModulesWithText}">
                            <div class="col-md-10" style="float:left;">
                                <table style="width: 100%;">
                                <c:forEach var="entry"  items="${аctiveMap}" >
                                    <tr style="background: moccasin;cursor:pointer;" class="fullshowgroupwithmodules" data-fullshowgroupid="${entry.key.groupId}"><td style="text-align: center;"> ${entry.key.groupName}:</td> </tr>
                                        <c:forEach var="module" items="${entry.value}" >
                                            <tr><td><span style="display: block;cursor: pointer;color: slategrey" class="showableModule hidingModule" data-fullshowgroupid="${entry.key.groupId}" id="${module.moduleId}" style="color:${module.hexcolor}">
                                                   ${module.moduleName}<!--<a href="<c:url value="/Event/eventProcessing?campaignId=${param.campaignId}&groupId=${entry.key.groupId}&moduleId=${module.moduleId}&eventId=${event.eventId}"/>">${module.moduleName}</a>-->
                                                    </span></td></tr>
                                            <tr><td style="text-align: justify;" class="hidingModule" data-fullshowgroupid="${entry.key.groupId}">${module.bodyText}</td></tr>
                                            </c:forEach>
                                    
                                </c:forEach>
                                </table>
                            </div>
                        </c:if>
                        <c:if test="${empty campaign.showModulesWithText}">
                        <div class="col-md-7" style="float:left;" id="moduleShow"></div>

                        <!--<div class="col-md-5" style="float:right;">
                            <div class="col-md-7"  data-spy="scroll" style="height: 100vh ;">-->
                        <!--<div class="col-md-3" >
                                <c:forEach var="entry"  items="${аctiveMap}" >
                                    <ul> ${entry.key.groupName} 
                                        <c:forEach var="module" items="${entry.value}" >
                                            <li style="cursor: pointer;" class="showableModule" id="${module.moduleId}"> <ins>
                                                    ${module.moduleName}
                                                </ins></li>
                                            </c:forEach>
                                    </ul>
                                </c:forEach>
                            </div>-->
                        <div class="col-md-3" style="overflow-y:auto;height: 450px;" >
                            <div class="col-md-12" >
                                <table style="width: 100%;">
                                    <c:forEach var="entry"  items="${аctiveMap}" >
                                        <tr style="background: moccasin;cursor: pointer;" class="groupwithmodules" data-groupid="${entry.key.groupId}"><td style="text-align: center;">${entry.key.groupName}:</td> </tr>
                                            <c:forEach var="module" items="${entry.value}" >
                                                <tr><td><span style="display: block;cursor: pointer;color:#${module.hexcolor};" class="showableModule hidingModule" data-groupid="${entry.key.groupId}" id="${module.moduleId}">
                                                        ${module.moduleName}
                                                        </span></td></tr>
                                                </c:forEach>
                                    </c:forEach>
                                    </table>
                            </div>
                        </div>
                            </c:if>

                            <!--<div class="col-md-5"  >-->
                            <div class="col-md-2" style="float:right;" >
                                <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Слив звонка</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="badFinishForm" action="<c:url value="/Event/badFinish" />" method="post">
                                                    <p> Причина:    <select name="failReasonId">
                                                            <c:forEach var="reason" items="${failReasons}" varStatus="myIndex">
                                                                <option value="${reason.failReasonId}">${reason.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    <p><textarea rows="5" cols="50" name="comment"> </textarea>
                                                        <input type="hidden" name="campaignId" value=${param.campaignId}>
                                                        <input type="hidden" name="eventId" value=${event.eventId}>
                                                    <p>     <input class="btn btn-primary" type="submit" value="Отправить">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="basicModalSuccess" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Положительный результат</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="goodFinishForm" action="<c:url value="/Event/goodFinish" />" method="post">
                                                    <p><textarea rows="5" cols="50" name="comment"> </textarea>

                                                    <p>    <div class="form-group">
                                                        <div class="input-group date" id="datetimepicker2">
                                                            <input type="text" name="successDate" class="form-control" />
                                                            <span class="input-group-addon">
                                                               <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                                                            </span>
                                                        </div>
                                                    </div> 

                                                    <input type="hidden" name="campaignId" value=${param.campaignId}>
                                                    <input type="hidden" name="eventId" value=${event.eventId}>
                                                    <p>    <input class="btn btn-primary" type="submit" value="Отправить">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                                    
                                <div class="modal fade" id="basicModalPostpone" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Перенос звонка</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="postponeForm" action="<c:url value="/Event/postponeEvent" />" method="post">
                                                    <p><textarea rows="5" cols="50" name="comment">${event.comment} </textarea>

                                                    <p>    <div class="form-group">
                                                        <div class="input-group date" id="datetimepicker1">
                                                            <input type="text" name="postponeDate" class="form-control" />
                                                            <span class="input-group-addon">
                                                               <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                                                            </span>
                                                        </div>
                                                    </div> 

                                                    <input type="hidden" name="campaignId" value=${param.campaignId}>
                                                    <input type="hidden" name="eventId" value=${event.eventId}>
                                                    <p>    <input class="btn btn-primary" type="submit" value="Перенести">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <p>  <a href="#" class="btn btn-large btn-warning"
                                        data-toggle="modal"
                                        data-target="#basicModalPostpone">Перенести</a>
                                <p>  <a href="#" class="btn btn-large btn-danger"
                                        data-toggle="modal"
                                        data-target="#basicModal">Отрицательный исход</a>
                                <p>   <a href="#" class="btn btn-large btn-success"
                                         data-toggle="modal"
                                         data-target="#basicModalSuccess">Положительный исход</a>
                                <p>    <a href="<c:url value="/Event/event?campaignId=${param.campaignId}&strategyId=${campaign.strategy.strategyId}"/>" class="btn btn-large btn-primary" role="button">Следующий клиент</a>

                            </div>
                        </div>

                    </div>
                    <div hidden="1" id="moduleBufer" data-clientId="${event.client.clientId}" data-eventId="${event.eventId}">

                        <c:forEach var="entry"  items="${аctiveMap}" >
                            <c:forEach var="module"  items="${entry.value}" >
                                <div id="${module.moduleId}" class="hiddenModule">
                                    <span style="display: block;cursor: pointer;font-size:18px;color: slategrey" class="showableModule" id="${module.moduleId}"><ins><b>
                                                    ${module.moduleName}
                                                    </b></ins></span>
                                    <br><br>
                                    ${module.bodyText}
                                </div>
                            </c:forEach>
                        </c:forEach>
                    </div>
            
    </body>
</html>
