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
        <title> </title>
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <br> 
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> <br>


        <div class="row">
            <div class="col-md-6"> 
                <form action="<c:url value="/Strategy/show" />"  method="post"> 
                    <center><h3> Доступные стратегии </h3></center>
                    <div class="input-append pull-right">
                        <input class="span5" id="appendedInputButton" name="strategyName" size="16" type="text">
                        <button type="submit" name="submit" class="btn">добавить</button>
                    </div>
                    <table class="table table-bordered table-hover">
                        <tr>
                            <td>${myIndex.index}#</td>
                            <td>имя</td>

                        </tr>
                        <c:forEach var="strategy" items="${StrategyList}" varStatus="myIndex">

                            <tr>
                                <td>${myIndex.count}</td>
                                <%--      <td>${cabinetUser.user.name}</td> <c:url value="/Strategy/strategy" />
                                 <td>${cabinetUser.user.surname}</td>
                                 <td>${cabinetUser.user_role}</td>  
                                
                                <td><a href="<c:url value="/Strategy/strategy"/>"> ${strategy.strategyName} </a></td>  --%>

                                <td onClick="location = '<c:url value="/Strategy/strategy?strategyId=${strategy.strategyId}"/>'"> ${strategy.strategyName} </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
            <div class="col-md-6">

            </div>
        </div>


    </body>
</html>
