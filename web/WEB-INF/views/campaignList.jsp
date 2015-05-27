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
            <a href="<c:url value="/Event/createCampaign"/>" class="btn btn-primary" role="button">Создать кампанию</a>
            
             <table class="table table-bordered table-hover">

                <tr>
                    <td> Название </td>
                    <td> Стратегия </td>
                    <td> Назначенных клиентов </td>
                    <td> Дата создания </td>
                    <td> Дата окончания </td>
                    <td> Статус </td>
                </tr>
                <c:forEach var="campaign" items="${campaigns}" varStatus="myIndex">

                    <tr>
                        <td onClick="location = '<c:url value="/Event/campaignSpecification?campaignId=${campaign.campaignId}"/>'" >${campaign.name}</td>
                        <td onClick="location = '<c:url value="/Event/campaignSpecification?campaignId=${campaign.campaignId}"/>'" >${campaign.strategy.strategyName}</td>
                        <td>${campaign.events.size()}</td>
                        <td>${campaign.creationDate}</td>
                        <td>${campaign.endDate}</td>
                        <c:if test="${campaign.status== 1}">
                            <td>Закрыто</td>
                        </c:if>
                        <c:if test="${campaign.status== 0}">
                            <td>Активна</td>
                        </c:if>
                        <%--
                        <td onClick="location = '<c:url value="/User/deleteUser?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>'" >Удалить</td>
                        --%>
                    </tr>
                </c:forEach>
            </table>
        </div>




    </body>
</html>
