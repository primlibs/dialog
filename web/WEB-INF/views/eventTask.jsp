<%-- 
    Document   : eventTask
    Created on : 30.04.2015, 13:15:22
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> </title>
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
            <h4>  Евент:  ${event.name} &nbsp Стратегия: ${event.strategy.strategyName} </h4>
         <div class="btn-group" role="group" aria-label="...">
            <a href="<c:url value="/Event/getShapeExcel"/>" class="btn btn-large btn-primary" role="button">Получить форму excel</a>
            <a href="<c:url value="/Event/appointAll"/>" class="btn btn-large btn-primary" role="button">Назначить всем</a>
         </div>
            <form enctype="multipart/form-data" action="<c:url value="/Event/setXls" />" method="post">
                Загрузить файл: <input name="fileXls" type="file">
                Обновлять клиентов: <input class="btn btn-primary" type="checkbox" name="checkbox" value="agree"> 
                <input type="hidden" name="eventId" value=${param['eventId']}>
                <input class="btn btn-primary" type="submit" value="Отправить">
            </form>

            <table class="table table-bordered table-hover">

                <tr>
                    <td rowspan="2">Менеджеры </td>
                    <td rowspan="2" >Клиенты </td>
                    <td rowspan="2">Назначено </td>
                    <td rowspan="2">Не назначено </td>
                    <td colspan="3" >обработано</td>
                </tr>
                <tr>
                    <td>Успешно </td>
                    <td>Не успешно </td>
                    <td>Всего</td>
                </tr>
                <c:set var="number" value="1" />
                <c:forEach var="user"  items="${listUser}" varStatus="myIndex">
                    <tr>

                        <td >${user.user.surname} &nbsp ${user.user.name} </td>

                        <c:if test="${number== 1}">
                            <td rowspan="${listUser.size()}" > ${eventClientLinkList.size()} </td>
                        </c:if>

                        <td > </td>

                        <c:if test="${number== 1}">
                            <td rowspan="${listUser.size()}" >${unassignedEventClientLinkList.size()}  </td>
                        </c:if>
                        <td>  </td>
                        <td>  </td>
                        <td> </td>
                    </tr>

                    <c:set var="number" value="${number+1}" />
                </c:forEach>
                <tr>
                    <td > Итого:</td>
                    <td >${eventClientLinkList.size()}  </td>
                    <td >  </td>
                    <td >  ${unassignedEventClientLinkList.size()}  </td>

                    <td>  </td>
                    <td>  </td>
                    <td> </td>
                </tr>
            </table>
    </body>
</html>
