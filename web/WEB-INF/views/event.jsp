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

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
          
             <input type="hidden" name="userId" value=${paraqm.userId}>
              <input type="hidden" name="cabinetId" value=${param.cabinetId}>

            <a href="<c:url value="/Event/campaign"/>" class="btn btn-large btn-primary" role="button">Выбрать кампанию...</a>

            <div class="row">
                <div class="col-md-9">
                       <input type="hidden" name="eventId" value=${param.eventId}>
                       <input type="hidden" name="eventId" value=${event.eventId}>
                Клиент: Адрес: ${event.client.address}, Секретарь: ${event.client.nameSecretary}, ЛПР: ${event.client.nameLpr}, 
                  Телефон секретаря: ${event.client.phoneSecretary}, Телефон ЛПР: ${event.client.phoneLpr}, Комментарий: ${event.client.comment}
                    

                    <div class="row">
                        <div class="col-md-8">
                            Модуль ответа(название - ссылка) 
                        </div>
                        <div class="col-md-4"  data-spy="scroll" style="height: 100vh ; line-height: 0.3em;">
                            <c:forEach var="аctiveM"  items="${аctiveMap}" >
                                <p> <h5>  ${аctiveM.key.groupName} { </h5>
                                <c:forEach var="moduleL"  items="${аctiveM.value}" >
                                    <p> <a href="<c:url value="/Event/eventProcessing?groupId=${аctiveM.key.groupId}&moduleId=${moduleL.moduleId}&eventId=${event.eventId}"/>">${moduleL.moduleName}</a>
                                    </c:forEach>
                                <h5> } </h5>
                            </c:forEach>
                            <div >
                                <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-danger" role="button">Слив звонка</a>
                                <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-success" role="button">Положительный результат</a>
                                <p>    <a href="<c:url value="/Event/event?campaignId=${param.campaignId}&strategyId=${campaign.strategy.strategyId}"/>" class="btn btn-large btn-primary" role="button">следующий клиент</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
