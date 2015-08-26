<%-- 
    Document   : campaignSpecification
    Created on : 30.04.2015, 13:15:22
    Author     : Юрий
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container">

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %>
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <script type="text/javascript">
            $(function () {
                //Установим для виджета русскую локаль с помощью параметра language и значения ru
                $('#datetimepicker2').datetimepicker(
                        {language: 'ru',
                            viewMode: 'days',
                            pickTime: false}
                );
                $('#datetimepicker1').datetimepicker(
                        {language: 'ru',
                            viewMode: 'days',
                            pickTime: false}
                );
            });
        </script>
        <div class="row form-group">
            <span style="font-size: 18px;font-weight: 500;">Отчет по входящим обращениям</span>
        </div>

        <div class="form-group">
            <c:if test="${not empty strategyList}">
                <form class="form-inline btn-group" action="/Event/inCallReport" method="post">
                    <div class="bootstrap-select">
                        <select class="form-control" name="strategyId" data-style="btn-primary">
                            <c:forEach var="strategy" items="${strategyList}">
                                <c:choose>
                                    <c:when test="${strategy.strategyId eq param.strategyId}">
                                        <option value="$${strategy.strategyId}" selected>${strategy.strategyName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${strategy.strategyId}">${strategy.strategyName}</option>
                                    </c:otherwise>
                                </c:choose>    

                            </c:forEach>
                        </select>
                        <input type="submit" name="submit" class="btn btn-primary" value="Выбрать">
                    </div>
                </form>
            </c:if>
        </div>
        
            
        
        <c:if test="${not empty reportMap}">
            <div class="form-group">
                <form class="form-inline btn-group" action="/Event/inCallReport" method="post">
                    <div class="bootstrap-select">
                        <div class="input-group date" id="datetimepicker1">
                            <input type="text" name="from" class="form-control" value="<c:if test="${not empty param.from}"><fmt:formatDate type="HH.mm.yyyy" value="${param.from}"/></c:if>" />
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                            </span>
                        </div>
                        <div class="input-group date" id="datetimepicker2">
                            <input type="text" name="to" class="form-control" value="<c:if test="${not empty param.to}"><fmt:formatDate type="HH.mm.yyyy" value="${param.to}"/></c:if>"/>
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                            </span>
                        </div>
                        <input type="hidden" name="strategyId" value="${param.strategyId}">    
                        <input type="submit" name="submit" class="btn btn-primary" value="Выбрать">
                    </div>
                </form>
            </div>



            <div class="row ">   <table class="table table-bordered table-hover">
                    <tr><th>Модуль</th><th>Количество</th></tr>
                            <c:forEach var="entry"  items="${reportMap}">
                        <tr><td>${entry.key.moduleName}</td><td>${entry.value}</td></tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>

</html>
