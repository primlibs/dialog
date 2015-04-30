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


            <table class="table table-bordered table-hover">

                <tr>
                    <td rowspan="2">Менеджеры </td>
                    <td rowspan="2">Клиенты </td>
                    <td rowspan="2">Назначено </td>
                    <td rowspan="2">Не назначено </td>
                    <td colspan="3" >обработано</td>
                </tr>
                <tr>
                    <td>Успешно </td>
                    <td>Не успешно </td>
                    <td>Всего</td>
                </tr>
                <c:forEach var="user" items="${userList}" varStatus="myIndex">
                    <tr>
                        <td >${user.name} </td>
                        <td >${user.name} </td>
                        <td >${user.name} </td>
                        <td >  </td>
                   
                        <td>  </td>
                        <td>  </td>
                        <td> </td>
                    </tr>
                </c:forEach>
                     <tr>
                        <td > Итого:</td>
                        <td >  </td>
                        <td >  </td>
                        <td >   </td>
                   
                        <td>  </td>
                        <td>  </td>
                        <td> </td>
                    </tr>
            </table>
    </body>
</html>
