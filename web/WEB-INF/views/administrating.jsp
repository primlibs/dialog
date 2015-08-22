<%-- 
    Document   : administrating
    Created on : 22.08.2015, 13:16:29
    Author     : bezdatiuzer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title>Управление</title>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <table class="table table-bordered table-hover">
            <tr>
                <th> ИД </th>
                <th> Организация </th>
                <th> email </th>
                <th> Телефон </th>
            </tr>
            <c:forEach var="pk" items="${pkList}" varStatus="myIndex">
                <tr>
                    
                    <td> ${pk.pkId} </td>
                    <td> ${pk.company} </td>
                    <td> ${pk.email} </td>
                    <td> ${pk.phone} </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
