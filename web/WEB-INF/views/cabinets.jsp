<%-- 
    Document   : cabinets
    Created on : 22.08.2015, 13:16:29
    Author     : bezdatiuzer
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        
        <h3>Кабинеты</h3>
        <table class="table table-bordered table-hover">
            <tr>
                <th> ИД </th>
                <th> Дата регистрации </th>
                <th> Организация </th>
                <th> Тариф </th>
                <th> Дата окончания </th>
                <th> email </th>
                <th> Телефон </th>
                <th> Пользователи </th>
                <th>  </th>
            </tr>
            <c:forEach var="pk" items="${pkList}" varStatus="myIndex">
                <tr>
                <c:if test="${pk.inTwoWeek()eq true}">
                    <td style="cursor: pointer;" class="danger" ondblclick="location = '<c:url value="/Lk/administrating?pkId=${pk.pkId}"/>'">${pk.pkId}</td>>
                </c:if>  
                <c:if test="${pk.inTwoWeek()eq false}">
                    <td style="cursor: pointer;" ondblclick="location = '<c:url value="/Lk/administrating?pkId=${pk.pkId}"/>'"> ${pk.pkId}</td>>
                </c:if>         
                    <td><fmt:formatDate type="date" value="${pk.beginDate}"/></td>
                    <td> ${pk.company} </td>
                    <td> ${pk.getTarifName()} </td>
                    <td><fmt:formatDate type="date" value="${pk.endDate}"/></td>
                    <td> ${pk.email} </td>
                    <td> ${pk.phone} </td>
                    <c:if test="${pk.getCabinetUserList()!= null}">
                        <td> ${pk.getCabinetUserList().size()} </td>
                    </c:if>
                    <c:if test="${pk.getCabinetUserList() eq null}">
                        <td> 0</td>
                    </c:if>
                     <td><a class="btn btn-danger" href="<c:url  value="/Lk/setEndDate?pkId=${pk.pkId}"/>">Удалить</a> </td>    
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
