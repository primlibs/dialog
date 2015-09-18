<%-- 
    Document   : EventList
    Created on : 29.04.2015, 13:01:41
    Author     : Юрий
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

        <div class="row "><h3>Кампании</h3>

            <div class="form-group">
                <a href="<c:url value="/Event/createCampaign"/>" class="btn btn-primary" role="button">Создать кампанию</a>
            </div>
            <div class="row form-group">  

                <c:set var="checked" value=""/>
                <c:if test="${not empty closed}">
                    <c:set var="checked" value="checked"/>
                </c:if>

<!--<form class="form-inline" action="<c:url value="/Event/campaignList" />"  method="post"> 
    Старт кампании с
    <div class="input-group date">
        <input type="text" name="dateFrom" id="datetimepicker1" class="form-control" value=${dateFrom}>
    </div>
      по  
      <div class="input-group date">
        <input type="text" name="dateTo" id="datetimepicker2" class="form-control" value=${dateTo}>
      </div>
      <label>
        <input id="closed" type="checkbox" ${checked}> в том числе закрытые
    </label>
        <button type="submit" name="submit" class="btn btn-primary"> Выбрать </button>
</form>-->
            </div>
            <div class="row ">    
                <table class="table table-bordered table-hover">

                    <tr>
                        <td> Название </td>
                        <td> Сценарий </td>
                        <td> Клиенты </td>
                        <td> Не назначено </td>
                        <td> Завершено </td>
                        <td> Дата создания </td>
                        <td> Дата окончания </td>
                        <td> Статус </td>
                    </tr>
                    <c:if test="${not empty campaignsWithCountInfosMap}">
                        <c:forEach var="entry" items="${campaignsWithCountInfosMap}">

                            <c:if test="${entry.key.status== 1}">
                                <tr style="cursor: pointer;" class="table danger" ondblclick="location = '<c:url value="/Event/campaignSpecification?campaignId=${entry.key.campaignId}"/>'" >
                                </c:if>
                                <c:if test="${entry.key.status== 0}">
                                <tr style="cursor: pointer;" class="table success" ondblclick="location = '<c:url value="/Event/campaignSpecification?campaignId=${entry.key.campaignId}"/>'" >
                                </c:if>
                                <td>${entry.key.name}</td>
                                <td>${entry.key.strategy.strategyName}</td>
                                <td>${fn:length(entry.key.events)}</td>
                                <td>${entry.value.get("unassignedCount")}</td>
                                <td>${entry.value.get("finishedCount")}</td>
                                <td><fmt:formatDate type="date" value="${entry.key.creationDate}"/></td>
                                <td><fmt:formatDate type="date" value="${entry.key.endDate}"/></td>
                                <c:if test="${entry.key.status== 1}">
                                    <td>Закрыта</td>
                                </c:if>
                                <c:if test="${entry.key.status== 0}">
                                    <td>Активна</td>
                                </c:if>
                                <%--
                                <td ondblclick="location = '<c:url value="/User/deleteUser?cabinetUserId=${cabinetUser.cabinetUserId}&userId=${cabinetUser.user.userId}"/>'" >Удалить</td>
                                --%>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </div>




    </body>
</html>
