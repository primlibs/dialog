<%-- 
    Document   : administrating
    Created on : 23.08.2015, 9:54:10
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
        
        <h3>Организация: ${pk.company}</h3>
        
        <h4>${tarifInfo}</h4>
        
        <c:if test="${empty tarifs}">
            <h4>Не найдено доступных тарифов для назначения.</h4>
        </c:if>
        <c:if test="${not empty tarifs}">
        <form class="form form-inline" action="<c:url value="/Lk/setTarif" />" method="post">
            <p> Тариф: <select name="tarifId">
                    <c:forEach var="t" items="${tarifs}">
                        <option value="${t.tarifId}">${t.getData()}</option>
                    </c:forEach> 
                </select>
                <input type="hidden" name="pkId" value=${pk.pkId}>
                 <input class="btn btn-primary" type="submit" value="Установить">
        </form>
        </c:if>
    </body>
</html>
