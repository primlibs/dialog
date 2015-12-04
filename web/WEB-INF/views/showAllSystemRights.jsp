<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title>Права - модерация структуры</title>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <h3>Права - модерация структуры</h3>
            
        <table class="table table-bordered table-hover">
                <tr>
                    <th> Описание</th>
                    <th> Object </th>
                    <th> Action </th>
                    <th> Дата создания </th>
                    <th> Изменяемое </th>
                </tr>
                
                
                <c:forEach var="right" items="${listRight}">
                    <tr>
                        <td> ${right.actionDescription}</td>
                        <td> ${right.object} </td>
                        <td> ${right.action} </td>
                        <td> <fmt:formatDate type="date" value="${right.addDate}"/> </td>
                        <td> <c:if test="${right.changeable eq false}">НЕТ</c:if>
                            <c:if test="${right.changeable eq true}">ДА</c:if>
                            <a href="<c:url value="/Right/change?rightId=${right.rightId}"/>" class="glyphicon glyphicon-refresh"></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
    </body>
</html>
