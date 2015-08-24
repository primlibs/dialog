<%-- 
    Document   : menutop
    Created on : 24.03.2015, 15:44:32
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>



<div class="navbar" style="margin-bottom: 0px">
    <ul class="nav navbar-nav">
               <li><a href="<c:url value="/Event/campaign"/>">Мои звонки</a></li>
               <li><a href="<c:url value="/Event/postponedEvents"/>">Перенесенные</a></li>
               <li><a href="<c:url value="/Event/outCampaign"/>">Входящие</a></li>   
    </ul> 
    <%@include file="/WEB-INF/jsp/menuright.jsp" %>
</div>   


