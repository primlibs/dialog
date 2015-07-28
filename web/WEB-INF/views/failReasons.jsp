<%-- 
    Document   : failReason
    Created on : 27.05.2015, 1:11:51
    Author     : NameLostInAges
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        
        

        <div class="row ">
            <h4>Причины отказа</h4>
            
                <!--<div class="form-group btn-group">
                    <a href="<c:url value="/Strategy/strategy?strategyId=${param.strategyId}"/>" class="btn btn-large" role="button"><= стратегия</a>
                    <input type="hidden" name="strategyId" value=${strategy.strategyId}>

                </div>-->

                <div class="form-group input-append btn-group">
                    <form class="form-inline" action="<c:url value="/Strategy/createFailReason" />"  method="post"> 
                        <input class="span5" id="appendedInputButton" name="failReasonName" style="width: 256px " size="16" type="text">
                        <input type="hidden" name="strategyId" value=${param.strategyId}>
                        <button type="submit" name="submit" class="btn btn-primary">  Добавить  </button>
                    </form>
                </div>
                <table class="table table-bordered table-hover">
                    <tr>
                        <td>${myIndex.index}#</td>
                        <td>Причина</td>
                        <td>Удалить</td>
                    </tr>
                    <c:forEach var="failReason" items="${actualReasons}" varStatus="myIndex">

                        <tr>
                            <td>${myIndex.count}</td>
                            <td>${failReason.name}</td>
                            <td><a href="#" id="${failReason.failReasonId}" class="btn btn-danger btn-xs deletinghref"
                               data-toggle="modal"
                               data-target="#deleteWindow">Удалить</a></td>
                            <!--<td><div style="cursor: pointer;display: inline-block" ondblclick="location = '<c:url value="/Strategy/deleteFailReason?failReasonId=${failReason.failReasonId}"/>'">удалить</div></td>-->
                        </tr>
                    </c:forEach>
                </table>


        </div>
                        <div class="modal fade" id="deleteWindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title" id="myModalLabel">Удалить стратегию?</h4>
                    </div>
                    <div class="modal-body">
                        <form id="deleteTagForm" action="<c:url value="/Strategy/deleteFailReason"/>" method="post">
                                <input type="hidden" name="failReasonIdtoDelete" value="">
                                <input type="hidden" name="strategyId" value="${strategyId}">
                            <p><input class="btn btn-danger" type="submit" value="Удалить">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('.deletinghref').click(function() {
                var cuid = $(this).attr('id');
                $('[name = failReasonIdtoDelete]').val(cuid);
            });
        </script>
    </body>
</html>
