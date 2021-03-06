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
        
        <div class="row form-group">
            <h2>Отчет по звонкам. Отрицательный результат.</h2>
            <span style="font-size: 18px;font-weight: 500;"><b>Кампания: ${campaign.name}; Модуль: ${module.moduleName};</b></span>
            <form class="form-inline btn-group" action="<c:url value="/Event/moduleReportDetalisationXLS" />" >
          <input type="hidden" name="campaignId" value=${campaign.campaignId}>
          <input type="hidden"  name="moduleId" value="${module.moduleId}">
          <input type="submit" name="submit" class="btn btn-primary" value="Скачать excel" >
        </form>
        </div>    
            <table class="table table-bordered table-hover">
                <tr class="active">
                    <th></th>
                    <th>Уникальный номер</th>
                    <th>Компания</th>
                    <th>Контактное лицо</th>
                    <th>Телефон К.Л.</th>
                    <th>Л.П.Р.</th>
                    <th>Телефон Л.П.Р.</th>
                    <th>Адрес</th>
                    <th>Пользователь</th>
                    <th>Дата</th>
                    <th>Причина</th>
                </tr>
                <c:forEach var="event" items="${events}" varStatus="myIndex">
                    <tr style="cursor: pointer;" ondblclick="location = '<c:url value="/Client/oneClient?clientId=${event.client.clientId}&eventId=${event.eventId}"/>'" class="active">
                        <td>${myIndex.count}</td>
                        <td>${event.client.uniqueId}</td> 
                        <td>${event.client.nameCompany}</td>
                        <td>${event.client.nameSecretary}</td>   
                        <td>${event.client.getFormattedPhoneSec()}</td>  
                        <td>${event.client.nameLpr}</td> 
                        <td>${event.client.getFormattedPhoneLpr()}</td>  
                        <td>${event.client.address}</td>
                        <td>${event.user.getShortName()}</td>
                        <td><fmt:formatDate type="both" value="${event.getSetStatusDate()}"/></td>
                        <td>${event.failReason.name}</td>
                    </tr>
                </c:forEach>
            </table>
    </body>
</html>
