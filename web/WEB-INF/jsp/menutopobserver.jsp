<%-- 
    Document   : menutopadm
    Created on : 26.03.2015, 10:20:45
    Author     : Юрий
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>




<div class="navbar" style="margin-bottom: 0px">
    <ul class="nav navbar-nav">
        <li><a href="<c:url value="/Event/campaignList"/>">Кампании</a></li>

        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Входящие <span class="caret"></span></a>

                <li><a href="<c:url value="/Event/inCallReport"/>">Отчетность</a></li>              
        </li>
        

        
    </ul> 
    <%@include file="/WEB-INF/jsp/menuright.jsp" %>
</div>  
