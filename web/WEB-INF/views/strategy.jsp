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


        <div class="form-group">  
            <h3> стратегия сейчас </h3> <a href="<c:url value="/Strategy/show"/>" class="btn btn-large btn-primary" role="button">Список стратегий</a>
        </div>
        <div class="row">
            <div class="col-md-6"> 
                <div>
                    Реплика клиента(название модуля)
                </div>

                <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor">
                    <a class="btn btn-large" data-edit="bold"><i class="icon-bold"></i></a>
                </div>
            </div>

            <div class="col-md-6">
                <form action="<c:url value="/Strategy/strategy" />"  method="post"> 

                    <div class="input-append pull-right">
                        <input type="hidden" name="strategyId" value=${strategyId}>
                        <input class="span5" id="appendedInputButton" name="groupName" style="width: 376px " size="16" type="text">
                        <button type="submit" name="submit" class="btn">добавить</button>
                    </div>



                    <c:forEach var="group" items="${groupList}" varStatus="myIndex">
                        <li>${groupList}   </li>
                        <div class="input-append pull-right">
                            <input class="span5" id="appendedInputButton" name="moduleName" style="width: 276px " size="16" type="text">
                            <button type="submit" name="submit" class="btn">добавить</button>
                        </div>
                        <div>
                            <c:forEach var="module" items="${moduleList}" varStatus="myIndex">
                                <li>${moduleList}   </li>
                                </c:forEach>
                        </div>    
                    </c:forEach>


                </form>

            </div>
        </div>



    </body>
</html>
