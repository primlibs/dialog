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

            <a href="<c:url value="/Event/getShapeExcel"/>" class="btn btn-large btn-primary" role="button">Получить форму excel</a>


            <form enctype="multipart/form-data" action="<c:url value="/Event/setXls" />" method="post">
                <div style="position:relative;">
                    <a class='btn btn-primary' href='javascript:;'>
                          Загрузить файл...
                        <input type="file" style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:transparent;color:transparent;' name="fileXls" size="40"  onchange='$("#upload-file-info").html($(this).val());'>
                    </a>
                    &nbsp;
                    <span class='label label-info' id="upload-file-info"></span>
                </div>
                Обновлять клиентов: <input  type="checkbox" name="checkbox" value="agree"> 
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
                <c:forEach var="cabinetUser"  items="${cabinetUserList}" varStatus="myIndex">
                    <tr>

                        <td >${cabinetUser.user.surname} &nbsp ${cabinetUser.user.name} </td>

                        <c:if test="${number== 1}">
                            <td rowspan="${cabinetUserList.size()}" onClick="location = '<c:url value="/Event/eventClient?eventId=${event.eventId}"/>'" > ${eventClientLinkList.size()} </td>
                        </c:if>

                        <td > </td>

                        <c:if test="${number== 1}">
                            <td rowspan="${cabinetUserList.size()}" onClick="location = '<c:url value="/Event/eventAppoint?eventId=${event.eventId}"/>'" >${unassignedEventClientLinkList.size()}  </td>
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
