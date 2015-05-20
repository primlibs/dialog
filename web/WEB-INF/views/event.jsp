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
    <body class="container" >

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
            <h4>  Евент:  ${campaign.name} &nbsp Стратегия: ${campaign.strategy.strategyName} </h4>
            <input type="hidden" name="campaignId" value=${param.campaignId}>
            <input type="hidden" name="strategyId" value=${campaign.strategy.strategyId}>

            <a href="<c:url value="/Event/campaign"/>" class="btn btn-large btn-primary" role="button">Выбрать кампанию...</a>

            <div class="row">
                <div class="col-md-9">
                    Клиент, Адрес, Секретарь, ЛПР, Телефон секретаря, Телефон ЛПР, Комментарий

                    <div class="row">
                        <div class="col-md-6">
                            Модуль ответа(название - ссылка) 
                        </div>
                        <div class="col-md-6">
                            <c:forEach var="groupL"  items="${groupList}" >
                                <p>   <a href="<c:url value="/Event/#?groupId=${groupL.groupId}"/>">${groupL.groupName}</a>
                                    <c:forEach var="moduleL"  items="${groupL.moduleList}" >
                                    <p> <a href="<c:url value="/Event/#?groupId=${moduleL.groupId}&moduleId=${moduleL.moduleId}"/>">${moduleL.moduleName}</a>

                                    </c:forEach>
                                </c:forEach>
                            <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-warning" role="button">Слив звонка</a>
                            <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-success" role="button">Положительный результат</a>
                            <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-primary" role="button">следующий клиент</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
