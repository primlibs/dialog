<%-- 
    Document   : menutop
    Created on : 24.03.2015, 15:44:32
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>



<div class="navbar">
    <ul class="nav navbar-nav">
               <li><a href="<c:url value="/Event/campaign"/>">Кампании</a></li>  
    </ul> 
    <%@include file="/WEB-INF/jsp/menuright.jsp" %>
</div>   


