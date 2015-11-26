<%-- 
    Document   : tarif
    Created on : 23.08.2015, 2:06:28
    Author     : bezdatiuzer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title>Тарифы</title>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <h3>Права - модерация структуры</h3>
        


        <c:if test="${empty listRight}">
            <h5> Нет структуры прав </h5>
        </c:if>
        <c:if test="${!empty listRight}">
            <table class="table table-bordered table-hover">
                <tr>
                    <th> Описание</th>
                    <th> Object </th>
                    <th> Action </th>
                    <th> Дата создания </th>
                    <th> Изменяемое </th>
                    <th></th>
                </tr>
                <c:forEach var="right" items="${listRight}">
                    <tr>
                        <td> ${right.action_description}</td>
                        <td> ${right.object} </td>
                        <td> ${right.action} </td>
                        <td> ${right.addDate} </td>
                        <td> ${right.changeable} </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
