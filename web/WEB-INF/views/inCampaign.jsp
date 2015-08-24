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
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        
        <div class="row ">
            <h3>Мои звонки</h3>
        </div>

        <div class="row ">
            <table class="table table-bordered table-hover">
                <tr>
                    <th>ПП</th>
                    <th>Сценарии</th>
                </tr>
                <c:forEach var="camp" items="${campaigns}" varStatus="myIndex">
                    <tr style="cursor: pointer;" ondblclick="location = '<c:url value="/Event/in?strategyId=${camp.getStrategyId()}"/>'">
                        <td>${myIndex.count}</td>
                        <td> ${camp.getStrategyName()} </td>

                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
