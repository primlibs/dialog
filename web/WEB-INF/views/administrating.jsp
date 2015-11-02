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
        <script type="text/javascript">
            $(function () {
                $('#datetimepicker').datetimepicker(
                        {language: 'ru',
                            viewMode: 'days',
                            pickTime: false}
                );
            });
        </script>
        <h3>Организация: ${pk.company}</h3>

        <h4>${tarifInfo}</h4>

        <c:if test="${empty tarifs}">
            <h4>Не найдено доступных тарифов для назначения.</h4>
        </c:if>
        <c:if test="${not empty tarifs}">
            <form class="form form-inline" action="<c:url value="/Lk/setTarif" />" method="post">
                <p> Новый тариф: <select name="tarifId">
                        <c:forEach var="t" items="${tarifs}">
                            <option value="${t.tarifId}">${t.getData()}</option>
                        </c:forEach> 
                    </select>
                    <input type="hidden" name="pkId" value=${pk.pkId}>
                    <input class="btn btn-primary" type="submit" value="Установить">
            </form>
        </c:if>
        <p> Установить дату окончания:
        <form class="form form-group form-inline" action="<c:url value="/Lk/setEndDate"/>" method="post">
            <div class="form-group">
                <div class="input-group date">
                    <input type="text" name="newDate" id="datetimepicker" class="form-control" placeholder="Дата окончания" value="${newDate}"/>
                </div>
            </div>
            <input type="hidden" name="pkId" value="${pk.pkId}"/>
            <input class="btn btn-primary" type="submit" value="Установить">
        </form>
    </p>

    <c:if test="${not empty cabUserList}">
        <table class="table table-bordered table-hover">
            <c:forEach var="cuser" items="${cabUserList}">
                <tr><td>${cuser.user.email}</td><td>${cuser.user.surname} ${cuser.user.name}</td><td>${cuser.userRole}</td></tr>
            </c:forEach> 
        </table>
    </c:if>
    <c:if test="${not empty campaignList}">
        <h3>Кампании</h3>
        <table class="table table-bordered table-hover">
            <c:forEach var="camp" items="${campaignList}">
                <tr><td>${camp.name}</td><td> ${camp.isClosed()}</td><td>${camp.getEvents().size()}</td></tr>
            </c:forEach> 
        </table>
    </c:if>
    <c:if test="${not empty inCall}">
        <h3>Входящие - ${inCall}</h3>
    </c:if>
</body>
</html>
