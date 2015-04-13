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
                    <div class="input-append">
                        <input class="span2" id="appendedInputButton" name="strategyName" size="16" type="text">
                        <button type="submit" class="btn">добавить</button>
                    </div>
                    <table class="table table-bordered table-hover">
                        <tr>
                            <td>${myIndex.index}#</td>
                            <td>имя</td>

                        </tr>
                        <c:forEach var="cabinetUser" items="${cabinetUserList}" varStatus="myIndex">

                            <tr>
                                <td>${myIndex.count}</td>
                                <%--      <td>${cabinetUser.user.name}</td>
                                 <td>${cabinetUser.user.surname}</td>
                                 <td>${cabinetUser.user_role}</td>  --%>

                                <td>${cabinetUser.strategy.name}</td>
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
