<%-- 
    Document   : tarif
    Created on : 23.08.2015, 2:06:28
    Author     : bezdatiuzer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title>Тарифы</title>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <h3>Тарифы</h3>
        <div class="form-group">
                    <form role="form" class="form-inline" action="<c:url value="/Tarif/create" />"  method="post"> 
                        <input class="span5" name="name" style="width: 150px " size="16" type="text" value="${name}" placeholder="Название">
                        <input class="span5" name="price" type="text" value="${price}" placeholder="Цена">
                        <input class="span5" name="days" type="text" value="${days}" placeholder="Дней">
                        <input class="span5" name="users" type="text" value="${users}" placeholder="Пользователей">
                        <input class="span5" name="campaigns" type="text" value="${campaigns}" placeholder="Кампаний">
                        <input class="span5" name="clients" type="text" value="${clients}" placeholder="Клиентов">
                        <button type="submit" name="submit" class="btn btn-primary">  Добавить  </button>
                    </form>
                </div>
        
        
        <c:if test="${empty tarifs}">
                        <h5> Нет добавленных тарифов </h5>
                    </c:if>
        <c:if test="${!empty tarifs}">
        <table class="table table-bordered table-hover">
            <tr>
                <th> № </th>
                <th> Тариф </th>
                <th> Цена </th>
                <th> Продолжительность </th>
                <th> Пользователей </th>
                <th> Кампаний </th>
                <th> Клиентов </th>
                <th></th>
            </tr>
            <c:forEach var="t" items="${tarifs}" varStatus="myIndex">
                <tr>
                    <td> ${myIndex.count}</td>
                    <td> ${t.name} </td>
                    <td> ${t.price} </td>
                    <td> ${t.dayLength} </td>
                    <td> ${t.userCount} </td>
                    <td> ${t.campaignCount} </td>
                    <td> ${t.clientCount} </td>
                    <td> <a href="#" id="${t.tarifId}" class="btn btn-danger btn-xs deletinghref"
                               data-toggle="modal"
                               data-target="#deleteWindow">Удалить</a></td>
                </tr>
            </c:forEach>
        </table>
        </c:if>
                        <div class="modal fade" id="deleteWindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Удалить тариф?</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="deleteTarifForm" action="<c:url value="/Tarif/delete"/>" method="post">
                                                    
                                                    
                                                        <input type="hidden" name="tarifIdToDelete" value="">
                                                    <p>     <input class="btn btn-danger" type="submit" value="Удалить">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                                <script>
            $('.deletinghref').click(function() {
                var tuid = $(this).attr('id');
                $('[name = tarifIdToDelete]').val(tuid);
            });
        </script>
    </body>
</html>
