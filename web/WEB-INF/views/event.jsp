<%-- 
    Document   : event
    Created on : 18.05.2015, 18:09:46
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> </title>
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <script src="<c:url value="/js/myJsOnViews/event.js" />"></script>
    </head>
    <body class="container">

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
            <h4>   Стратегия: ${strategy.strategyName}</h4>
            <input type="hidden" name="userId" value=${paraqm.userId}>
            <input type="hidden" name="cabinetId" value=${param.cabinetId}>


            <div class="row">
                <div class="col-md-12">
                    <input type="hidden" name="eventId" value=${param.eventId}>
                    <input type="hidden" name="eventId" value=${event.eventId}>
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th>Компания: </th>
                            <th>Адрес: </th>
                            <th>Секретарь: </th>
                            <th>ЛПР:</th>
                            <th>Телефон секретаря:  </th>
                            <th>Телефон ЛПР: </th>
                            <th>Комментарий: </th>
                        </tr>
                        <tr>
                            <td>${event.client.nameCompany} </td>
                            <td>${event.client.address} </td>
                            <td>${event.client.nameSecretary} </td>
                            <td>${event.client.nameLpr}</td>
                            <td>${event.client.phoneSecretary} </td>
                            <td>${event.client.phoneLpr} </td>
                            <td>${event.client.comment} </td>
                        </tr>
                    </table>

                    <div class="row">
                        <div id="moduleShow"></div>
                        <!--<div class="col-md-6">
                            ${module.moduleName}
                            <br>
                            <br>
                            ${module.bodyText}
                        </div>-->
                        <div class="col-md-4"  data-spy="scroll" style="height: 100vh ; line-height: 1em;">
                            <c:forEach var="entry"  items="${аctiveMap}" >
                                <ul> ${entry.key.groupName} 
                                    <c:forEach var="module"  items="${entry.value}" >
                                        <li style="cursor: pointer;" onclick="return showModule(${module.moduleId})"> <ins>
                                            ${module.moduleName}<!--<a href="<c:url value="/Event/eventProcessing?campaignId=${param.campaignId}&groupId=${entry.key.groupId}&moduleId=${module.moduleId}&eventId=${event.eventId}"/>">${module.moduleName}</a>-->
                                            </ins></li>
                                    </c:forEach>
                                </ul>
                            </c:forEach>
                        </div>
                        <div hidden="1" id="moduleBufer">
                            <c:forEach var="entry"  items="${аctiveMap}" >
                                <c:forEach var="module"  items="${entry.value}" >
                                <div id=${module.moduleId} class="hiddenModule">
                                    ${module.moduleName}
                                    <br><br>
                                    ${module.bodyText}
                                </div>
                                </c:forEach>
                            </c:forEach>
                        </div>
                        <div class="col-md-2"  >
                            <div class="carousel-search hidden-sm">
                                <div class="btn-group"> <a class="btn btn-danger dropdown-toggle btn-select" data-toggle="dropdown" href="#">Справочник сливов <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <c:forEach var="drain"  items="${drainList}" >
                                            <li><a href="<c:url value="/Event/#?drainId=${drain.drainId}&campaignId=${param.campaignId}&eventId=${event.eventId}"/>">${drain.name}</a></li>
                                            </c:forEach>
                                    </ul>
                                </div>

                                <p>    <a href="<c:url value="/Event/#?eventId=${event.eventId}"/>" class="btn btn-large btn-success" role="button">Положительный результат</a>
                                <p>    <a href="<c:url value="/Event/event?campaignId=${param.campaignId}&strategyId=${campaign.strategy.strategyId}"/>" class="btn btn-large btn-primary" role="button">следующий клиент</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
