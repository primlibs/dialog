<%-- 
    Document   : eventAppoint
    Created on : 07.05.2015, 11:41:44
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
        <div class="row">
            <h4>  Евент:  ${event.name} &nbsp Стратегия: ${event.strategy.strategyName} </h4>
            <input type="hidden" name="eventId" value=${param['eventId']}>

            <div class="btn-group" role="group" >
                <a href="<c:url value="/Event/eventTask?eventId=${param['eventId']}"/>" class="btn btn-primary" role="button">Евент</a>
                <c:if test="${clientList!=null && !clientList.isEmpty()}">
                    <a href="<c:url value="/Event/eventShowAllAppoint?eventId=${param['eventId']}"/>" class="btn btn-primary" role="button">Назначить всем</a>
                </c:if>
                    <a href="<c:url value="/Event/eventClient?eventId=${param['eventId']}"/>" class="btn btn-primary" role="button">Клиенты </a>   
            </div>

            <form  action="<c:url value="/Event/eventAppointSave" />" method="post">
                <table class="table table-bordered table-hover">
                    <tr>
                        <td>${myIndex.index}номер по порядку</td>
                        <td>Уникальный номер</td>
                        <td>Название компании</td>
                        <td>Имя серетаря</td>
                        <td>Имя лица принимающего решение</td>
                        <td>Телефон секретаря</td>
                        <td>Телефон лица принимающего решение</td>
                        <td>Адрес</td>
                        <td>Комментарии</td>
                        <td>Назначить юзера</td>
                    </tr>
                    <c:forEach var="client" items="${clientList}" varStatus="myIndex">

                        <tr>
                            <td>${myIndex.count}</td>
                            <td>${client.uniqueId}</td> 
                            <td>${client.nameCompany}</td>
                            <td>${client.nameSecretary}</td>  
                            <td>${client.nameLpr} </td>  
                            <td>${client.phoneSecretary} </td>  
                            <td>${client.phoneLpr} </td>  
                            <td >${client.address} </td>
                            <td>${client.comment} </td>  
                            <td>
                                <select name="arrayClientIdUserId">
                                    <option value=""> &nbsp  </option>
                                    <c:forEach var="cabinetUser" items="${cabinetUserList}" varStatus="myIndex">

                                        <option value="${client.clientId}_${cabinetUser.user.userId}">${cabinetUser.user.surname} &nbsp ${cabinetUser.user.name} </option>

                                    </c:forEach>
                                </select> 
                            </td> 
                        </tr>
                    </c:forEach>
                </table>
                <input type="hidden" name="eventId" value=${param['eventId']}>
                <c:if test="${clientList!=null && !clientList.isEmpty()}">
                    <input class="btn btn-primary" type="submit" value="Отправить">
                </c:if>
            </form>
        </div>


    </body>
</html>
