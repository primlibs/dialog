<%-- 
    Document   : eventAppointAll
    Created on : 12.05.2015, 14:30:44
    Author     : Юрий 
--%>
<%--        <c:if test="${eventAllAppoint!=null && !eventAllAppoint.isEmpty()}">    --%>
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

                <a href="<c:url value="/Event/eventClient?eventId=${param['eventId']}"/>" class="btn btn-primary" role="button">Клиенты </a>  
            </div>

            <form  action="<c:url value="/Event/eventAppointSaveAll" />" method="post">
                <table class="table table-bordered table-hover">

                    <c:forEach var="cabinetUser"  items="${cabinetUserList}" varStatus="myIndex">
                        <c:if test="${eventAllAppoint.get(cabinetUser.getUser().getUserId())!=null && eventAllAppoint.get(cabinetUser.getUser().getUserId())!=0}">
                            <tr>
                                <td >${cabinetUser.user.surname} &nbsp ${cabinetUser.user.name} </td>
                                <td> 
                                    <input type="text"  name="clientNum"  value="${eventAllAppoint.get(cabinetUser.getUser().getUserId())}" placeholder="Количество заданий">
                                    <input type="hidden" name="userId" value=${cabinetUser.user.userId}>
                                </td>
                            </tr>

                        </c:if>
                    </c:forEach>

                </table>
                <input type="hidden" name="eventId" value=${param['eventId']}>
                <input class="btn btn-primary" type="submit" value="Отправить">
            </form>
        </div>
    </body>
</html>
