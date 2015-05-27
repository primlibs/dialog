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
    </head>
    <body class="container">
        <script src="<c:url value="/js/myJsOnViews/event.js" />"></script>
        <script type="text/javascript">
            $(function () {
                //Установим для виджета русскую локаль с помощью параметра language и значения ru
                $('#datetimepicker2').datetimepicker(
                        {language: 'ru'}
                );
            });
        </script>

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
            <h4>   Стратегия: ${strategy.strategyName}</h4>

            <div class="row">
                <div class="col-md-12">
                    <!--<input type="hidden" name="eventId" id="eventId" value=${event.eventId}>-->
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th>Компания: </th>
                            <th>Адрес: </th>
                            <th>Секретарь: </th>
                            <th>ЛПР: </th>
                            <th>Телефон секретаря: </th>
                            <th>Телефон ЛПР: </th>
                            <th>Комментарий: </th>
                        </tr>
                        <tr>
                            <td>${event.client.nameCompany} </td>
                            <td class="changebleParam" id="adress">${event.client.address} </td>
                            <td class="changebleParam" id="nameSecretary">${event.client.nameSecretary} </td>
                            <td class="changebleParam" id="nameLpr">${event.client.nameLpr} </td>
                            <td class="changebleParam" id="phoneSecretary">${event.client.phoneSecretary} </td>
                            <td class="changebleParam" id="phoneLpr">${event.client.phoneLpr} </td>
                            <td class="changebleParam" id="comment">${event.client.comment} </td>
                        </tr>
                    </table>

                    <div class="row">
                        <div style="float:left;" id="moduleShow"></div>

                        <div style="float:right;">
                            <div class="col-md-4"  data-spy="scroll" style="height: 100vh ; line-height: 1em;">
                                <c:forEach var="entry"  items="${аctiveMap}" >
                                    <ul> ${entry.key.groupName} 
                                        <c:forEach var="module" items="${entry.value}" >
                                            <li style="cursor: pointer;" class="showableModule" id="${module.moduleId}"> <ins>
                                                    ${module.moduleName}<!--<a href="<c:url value="/Event/eventProcessing?campaignId=${param.campaignId}&groupId=${entry.key.groupId}&moduleId=${module.moduleId}&eventId=${event.eventId}"/>">${module.moduleName}</a>-->
                                                </ins></li>
                                            </c:forEach>
                                    </ul>
                                </c:forEach>
                            </div>



                            <div class="col-md-2"  >
                                <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Слив звонка</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="<c:url value="/Event/badFinish" />" method="post">

                                                    

                                                    <p> Причина:    <select name="drainId">
                                                            <c:forEach var="drain" items="${drainList}" varStatus="myIndex">
                                                                <option value="${drain.drainId}">${drain.name}</option>
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
                                                <form  action="<c:url value="/Event/#" />" method="post">
                                                    <p><textarea rows="6" cols="78" name="comment"> </textarea>

                                                    <p>    <div class="form-group">
                                                        <div class="input-group date" id="datetimepicker2">
                                                            <input type="text" class="form-control" />
                                                            <span class="input-group-addon">
                                                               <span class="glyphicon glyphicon-calendar glyphicon-nonescaped"></span>
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

                                <p>  <a href="#" class="btn btn-large btn-danger"
                                        data-toggle="modal"
                                        data-target="#basicModal">Слив звонка</a>
                                <p>   <a href="#" class="btn btn-large btn-success"
                                         data-toggle="modal"
                                         data-target="#basicModalSuccess">Положительный результат</a>
                                <p>    <a href="<c:url value="/Event/event?campaignId=${param.campaignId}&strategyId=${campaign.strategy.strategyId}"/>" class="btn btn-large btn-primary" role="button">следующий клиент</a>

                            </div>
                        </div>

                    </div>
                    <div hidden="1" id="moduleBufer" data-clientId="${event.client.clientId}" data-eventId="${event.eventId}">

                        <c:forEach var="entry"  items="${аctiveMap}" >
                            <c:forEach var="module"  items="${entry.value}" >
                                <div id="${module.moduleId}" class="hiddenModule">
                                    ${module.moduleName}
                                    <br><br>
                                    ${module.bodyText}
                                </div>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
