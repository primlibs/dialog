<%-- 
    Document   : error
    Created on : 30.03.2015, 16:20:58
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:if test="${! empty error}">
    <div class="error" >
    <c:forEach items="${error}" var="error" >
        <p>${error}</p>
    </c:forEach>
    </div>   
</c:if>
