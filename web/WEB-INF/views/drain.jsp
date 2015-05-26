<%-- 
    Document   : drain
    Created on : 27.05.2015, 1:11:51
    Author     : Ra
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
            <div class="col-md-6"> 
                <div class=" btn-group">
                    <a href="<c:url value="/Strategy/show"/>" class="btn btn-large btn-primary" role="button">Список стратегий</a>
                    <a href="<c:url value="/Strategy/strategy?strategyId=${param.strategyId}"/>" class="btn btn-large btn-primary" role="button">вернуться к стратегии</a>
                    <input type="hidden" name="strategyId" value=${strategy.strategyId}>
                 
                </div>
            </div>
            <div class="col-md-6"> 

                <div class=" input-append pull-right btn-group">
                    <form action="<c:url value="/Strategy/newDrain" />"  method="post"> 
                        <input class="span5" id="appendedInputButton" name="drainName" style="width: 376px " size="16" type="text">
                           <input type="hidden" name="strategyId" value=${param.strategyId}>
                        <button type="submit" name="submit" class="btn btn-default">  Добавить  </button>
                    </form>
                </div>
                <table class="table table-bordered table-hover">
                    <tr>
                        <td>${myIndex.index}#</td>
                        <td>имя</td>
                        <td>удалить</td>
                    </tr>
                    <c:forEach var="drain" items="${drainActiveList}" varStatus="myIndex">

                        <tr>
                            <td>${myIndex.count}</td>
                            <td > ${drain.name} </td>
                            <td onClick="location = '<c:url value="/Strategy/#?drainId=${drain.drainId}"/>'"> удалить </td>
                        </tr>
                    </c:forEach>
                </table>

            </div>


        </div>
    </body>
</html>
