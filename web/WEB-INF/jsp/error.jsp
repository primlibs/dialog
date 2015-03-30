<%-- 
    Document   : error
    Created on : 30.03.2015, 16:20:58
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:if test="${! empty error}">
    <div class="alert alert-danger">
        <c:forEach items="${error}" var="err">
            ${err}<br/>
        </c:forEach>
        <form:errors path="*" />
    </div>    
</c:if>
