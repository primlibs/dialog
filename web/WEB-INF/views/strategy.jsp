<%-- 
    Document   : strategy
    Created on : 13.04.2015, 14:21:07
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


        <div class="form-group" size="16">  
            <a href="<c:url value="/Strategy/show"/>" class="btn btn-large btn-primary" role="button">Список стратегий</a> &nbsp &nbsp  ${strategyName} 
        </div>
        <div class="row">
            <div class="col-md-6"> 
                <div>
                    Реплика клиента(название модуля)
                </div>
                <br>
                <div>
                    А тут будет текстовый редактор
                </div>

            </div>

            <div class="col-md-6">
                <form action="<c:url value="/Strategy/addGroup" />"  method="post"> 

                    <div class="input-append pull-right">
                        <input type="hidden" name="strategyId" value=${strategyId}>
                        <input class="span5" id="appendedInputButton" name="groupName" style="width: 376px " size="16" type="text">
                        <button type="submit" name="submit" class="btn"> <img src="/CallCentr/img/plus.png" height="20px"></button>
                    </div>  
                </form>
                <br>
                <table class="table table-bordered table-hover">
                    <c:forEach var="group" items="${GroupList}" varStatus="myIndex">
                        <tr>
                            <%--  <td>${myIndex.count}</td>   --%>
                            <td> ${group.groupName} 
                                <table class="table table-bordered table-hover">
                                    <form action="<c:url value="/Strategy/addModul" />"  method="post"> 
                                        <input class="span5" id="appendedInputButton" name="moduleName" style="width: 276px " size="16" type="text">
                                        <button type="submit" name="submit" class="btn"> <img src="/CallCentr/img/plus.png" height="20px"></button>
                                    </form>
                                    <c:forEach var="module" items="${ModuleList}" varStatus="myIndex">
                                        <tr>   

                                            <td>
                                                ${module} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </table>




            </div>
        </div>



    </body>
</html>
