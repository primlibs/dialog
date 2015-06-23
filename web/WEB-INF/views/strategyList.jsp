<%-- 
    Document   : strategyList
    Created on : 13.04.2015, 17:54:02
    Author     : Юрий
--%>

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


        <div class="row">
                <h3>Сценарий</h3>
        </div>            
                <div class="form-group">
                    <form action="<c:url value="/Strategy/show" />"  method="post"> 
                        <input class="span5" id="appendedInputButton" name="strategyName" style="width: 256px " size="16" type="text" value="${strategyName}">
                        <button type="submit" name="submit" class="btn btn-primary">Добавить</button>
                    </form>
                </div>
                <div class="row">
                <table class="table table-bordered table-hover">
                    <tr>
                        <th>${myIndex.index}#</th>
                        <th>Наименование</th>
                        <th></th>
                    </tr>
                    <c:forEach var="strategy" items="${StrategyList}" varStatus="myIndex">

                        <tr>
                            <td>${myIndex.count}</td>
                            <td><div style="cursor: pointer;display: inline;" ondblclick="location = '<c:url value="/Strategy/strategy?strategyId=${strategy.strategyId}"/>'"> ${strategy.strategyName} </div></td>
                            <td><div style="cursor: pointer;display: inline;" ondblclick="location = '<c:url value="/Strategy/deleteStrategy?strategyId=${strategy.strategyId}"/>'">Удалить</div></td>
                        </tr>
                    </c:forEach>
                </table>
                </div>


    </body>
</html>
