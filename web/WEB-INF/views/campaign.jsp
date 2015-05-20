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
                    <td>#Event</td>
                    <td>#Стратегия</td>
                </tr>
                <c:forEach var="ev" items="${events}" varStatus="myIndex">
                    <tr>
                        <td onClick="location = '<c:url value="/Event/event?campaignId=${ev.campaign.campaignId}"/>'">${ev.campaign.name}</td>
                        <td onClick="location = '<c:url value="/Event/event?campaignId=${ev.campaign.campaignId}"/>'">${ev.campaign.strategy.strategyName}</td>
                    </tr>
                </c:forEach>
            </table>


        </div>
    </body>
</html>
