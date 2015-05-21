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
                Клиент: Адрес: ${eventClient.client.address}, Секретарь: ${eventClient.client.nameSecretary}, ЛПР: ${eventClient.client.nameLpr}, 
                  Телефон секретаря: ${eventClient.client.phoneSecretary}, Телефон ЛПР: ${eventClient.client.phoneLpr}, Комментарий: ${eventClient.client.comment}
                    

                    <div class="row">
                        <div class="col-md-8">
                            Модуль ответа(название - ссылка) 
                        </div>
                        <div class="col-md-4"  data-spy="scroll" style="height: 100vh ; line-height: 0.3em;">
                            <c:forEach var="аctiveM"  items="${аctiveMap}" >
                                <p> <h5>  ${аctiveM.key.groupName} { </h5>
                                <c:forEach var="moduleL"  items="${аctiveM.value}" >
                                    <p> <a href="<c:url value="/Event/#?groupId=${аctiveM.key.groupId}&moduleId=${moduleL.moduleId}"/>">${moduleL.moduleName}</a>
                                    </c:forEach>
                                <h5> } </h5>
                            </c:forEach>
                            <div >
                                <p>    <a href="<c:url value="/Event/#"/>" class="btn btn-large btn-warning" role="button">Слив звонка</a>
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
