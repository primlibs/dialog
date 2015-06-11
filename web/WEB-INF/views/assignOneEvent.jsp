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
        
        <form  action="<c:url value="/Event/assignOneEvent" />" method="post">
            <p> Менеджер:   <select name="userId">
                    <option value="">Снять назначение</option>
                    <c:if test="${not empty calingUsers}">
                    <c:forEach var="cu" items="${calingUsers}">
                        <option value="${cu.user.userId}">${cu.user.email} - ${cu.user.surname} ${cu.user.name}</option>
                    </c:forEach>
                    </c:if>
                    <c:if test="${empty calingUsers}"> 
                        <option value="">нет доступных пользователей</option>
                    </c:if>   
                </select>
                <input type="hidden" name="eventId" value=${eventId}>
                <input type="hidden" name="campaignId" value=${campaignId}>
            <p>     <input class="btn btn-primary" type="submit" value="Назначить">
        </form>
        
    </body>
</html>