<%-- 
    Document   : event
    Created on : 18.05.2015, 18:09:46
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
            <a href="<c:url value="/Event/campaign"/>" class="btn btn-large btn-primary" role="button">Выбрать кампанию...</a>

            <table class="table table-bordered table-hover">
                <tr>
                    <td colspan="2">Клиент, Адрес, Секретарь, ЛПР, Телефон секретаря, Телефон ЛПР, Комментарий </td>
                </tr>
                <tr>
                    <td>Модуль ответа(название - ссылка) </td>
                    <td>Группа 1</td>
                </tr>
            </table>
        </div>
    </body>
</html>
