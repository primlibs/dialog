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
            <span style="font-size: 18px;font-weight: 500;">Детализация отчета по входящим обращениям по модулю: <c:if test="${not empty module}">${module.moduleName}</c:if></span>
        </div>

        <div class="form-group">

        </div>
        
            
        
        <c:if test="${not empty inCallList}">
            <div class="form-group">
                <form class="form-inline btn-group" action="/Event/inCallReportDetail" method="post">
                    <div class="bootstrap-select">
                        <div class="input-group date" id="datetimepicker1">
                            <input type="text" name="from" class="form-control" value="<fmt:formatDate pattern="dd.MM.yyyy" value="${dateFrom}"/>" />
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                            </span>
                        </div>
                        <div class="input-group date" id="datetimepicker2">
                            <input type="text" name="to" class="form-control" value="<fmt:formatDate pattern="dd.MM.yyyy" value="${dateTo}"/>"/>
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                            </span>
                        </div>
                        <input type="hidden" name="strategyId" value="${param.strategyId}">
                        <input type="hidden" name="moduleId" value="${param.moduleId}">   
                        <input type="submit" name="submit" class="btn btn-primary" value="Выбрать">
                    </div>
                </form>
            </div>
                        


            <div class="row ">   <table class="table table-bordered table-hover">
                    <tr><th>Пользователь</th><th>Дата</th></tr>
                            <c:forEach var="inCall"  items="${inCallList}">
                        <tr><td>${inCall.user.surname} ${inCall.user.name}</td><td ><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${inCall.addDate}"/></td></tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>

</html>
