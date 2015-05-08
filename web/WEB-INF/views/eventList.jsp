<%-- 
    Document   : EventList
    Created on : 29.04.2015, 13:01:41
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
            <a href="<c:url value="/Event/eventAdd"/>" class="btn btn-primary" role="button">Создать эвент</a>
            
             <table class="table table-bordered table-hover">

                <tr>
                    <td>Название </td>
                    <td>Стратегия </td>
                    <td>Дата создания </td>
                    <td>Дата окончания </td>
                  
                </tr>
                <c:forEach var="event" items="${eventList}" varStatus="myIndex">

                    <tr>
                        <td onClick="location = '<c:url value="/Event/eventTask?eventId=${event.eventId}"/>'" >${event.name}</td>
                        <td onClick="location = '<c:url value="/Event/eventTask?eventId=${event.eventId}"/>'" >${event.strategy.strategyName}</td>
                        <td>${event.creationDate}</td>
                        <td>${event.endDate}</td>
               
                        <%--
                        <td onClick="location = '<c:url value="/User/deleteUser?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>'" >Удалить</td>
                        --%>
                    </tr>
                </c:forEach>
            </table>
        </div>




    </body>
</html>
