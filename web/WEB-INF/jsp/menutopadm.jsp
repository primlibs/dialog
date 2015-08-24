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
        <!--<li><a href="<c:url value="/UserReport/summarizedReport"/>">Отчет по операторам</a></li>-->
        <li><a href="<c:url value="/Client/clientList"/>">Клиенты</a></li>
        <!--<li><a href="<c:url value="/Event/campaign"/>">Мои звонки</a></li>  -->
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Мои звонки<span class="caret"></span></a>
        <ul class="dropdown-menu">
                <li><a href="<c:url value="/Event/campaign"/>">Текущие</a></li>
                <li><a href="<c:url value="/Event/postponedEvents"/>">Перенесенные</a></li>
            </ul></li>
        <li><a href="<c:url value="/Event/inCampaign"/>">Входящие</a></li>    
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Настройки <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="/User/userList"/>">Пользователи</a></li>
                <li><a href="<c:url value="/Strategy/show"/>">Сценарии</a></li>
                <li><a href="<c:url value="/Tag/show"/>">Тэги</a></li>
            </ul>                    
        </li>
        
        <c:if test="${superadmin eq 'superadmin'}">
            <li class="dropdown">  <a href="#" data-toggle="dropdown"  class="dropdown-toggle">Управление <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="/Tarif/list"/>">Тарифы</a></li>
                <li><a href="<c:url value="/Lk/cabinets"/>">Кабинеты</a></li>
            </ul></li>
        </c:if>
        
    </ul> 
    <%@include file="/WEB-INF/jsp/menuright.jsp" %>
</div>  
