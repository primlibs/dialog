<%-- 
    Document   : campaign
    Created on : 18.05.2015, 18:23:37
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
            <table class="table table-bordered table-hover">
                <tr>
                    <td>Кампания</td>
                    <td>Стратегия</td>
                    <td>Количество клиентов</td>
                </tr>
                <c:forEach var="camp" items="${campaign}" varStatus="myIndex">
                    <tr>
                        <td onClick="location = '<c:url value="/Event/event?campaignId=${camp.key.getCampaignId()}"/>'"> ${camp.key.getName()}</td>
                        <td onClick="location = '<c:url value="/Event/event?campaignId=${camp.key.getCampaignId()}"/>'" > ${camp.key.getStrategy().getStrategyName()} </td>
                        <td> ${assignedUser.value}  </td>
                    </tr>
                </c:forEach>
            </table>


        </div>
    </body>
</html>
