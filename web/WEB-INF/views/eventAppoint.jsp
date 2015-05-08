<%-- 
    Document   : eventAppoint
    Created on : 07.05.2015, 11:41:44
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
        <div class="row">
            <h4>  Евент:  ${event.name} &nbsp Стратегия: ${event.strategy.strategyName} </h4>
            <a href="<c:url value="/Event/#"/>" class="btn btn-primary" role="button">Назначить всем</a>

            <table class="table table-bordered table-hover">
                <tr>
                    <td>${myIndex.index}номер по порядку</td>
                    <td>Уникальный номер</td>
                    <td>Название компании</td>
                    <td>Имя серетаря</td>
                    <td>Имя лица принимающего решение</td>
                    <td>Телефон секретаря</td>
                    <td>Телефон лица принимающего решение</td>
                    <td>Адрес</td>
                    <td>Комментарии</td>
                </tr>
                <c:forEach var="client" items="${ClientList}" varStatus="myIndex">

                    <tr>
                        <td>${myIndex.count}</td>
                        <td>${cabinetUser.user.name}</td> 
                        <td>${cabinetUser.user.surname}</td>
                        <td>${cabinetUser.user_role}</td>  
                        <td> </td>  
                        <td> </td>  
                        <td> </td>  
                        <td > ${strategy.strategyName} </td>
                        <td> </td>  
                    </tr>
                </c:forEach>
            </table>

        </div>


    </body>
</html>
