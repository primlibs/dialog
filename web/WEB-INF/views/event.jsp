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
            <h4>  Евент:  ${campaign.name} &nbsp Стратегия: ${campaign.strategy.strategyName} </h4>
            <input type="hidden" name="campaignId" value=${param.campaignId}>
            <input type="hidden" name="strategyId" value=${campaign.strategy.strategyId}>
             <input type="hidden" name="userId" value=${paraqm.userId}>
              <input type="hidden" name="cabinetId" value=${param.cabinetId}>

            <a href="<c:url value="/Event/campaign"/>" class="btn btn-large btn-primary" role="button">Выбрать кампанию...</a>

            <div class="row">
                <div class="col-md-9">
                Клиент: Адрес: ${eventClient.client.address}, Секретарь: ${eventClient.client.nameSecretary}, ЛПР: ${eventClient.client.nameLpr}, 
                  Телефон секретаря: ${eventClient.client.phoneSecretary}, Телефон ЛПР: ${eventClient.client.phoneLpr}, Комментарий: ${eventClient.client.comment}
                    

                    <div class="row">
                        <div class="col-md-8">
                            Модуль ответа(название - ссылка) 
                        </div>
                        <div class="col-md-4"  data-spy="scroll" style="height: 100vh ; line-height: 0.5em;">
                            <c:forEach var="groupL"  items="${campaign.strategy.groupList}" >
                                <p> <h4>  ${groupL.groupName} { </h4>
                                <c:forEach var="moduleL"  items="${groupL.moduleList}" >
                                    <p> <a href="<c:url value="/Event/#?groupId=${groupL.groupId}&moduleId=${moduleL.moduleId}"/>">${moduleL.moduleName}</a>
                                    </c:forEach>
                                <h4> } </h4>
                            </c:forEach>
                            <div >
                                <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-warning" role="button">Слив звонка</a>
                                <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-success" role="button">Положительный результат</a>
                                <p>    <a href="<c:url value="/Event/event"/>" class="btn btn-large btn-primary" role="button">следующий клиент</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
