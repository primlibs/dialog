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

        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 


        <div class="form-group" size="16">  
            <a href="<c:url value="/Strategy/show"/>" class="btn btn-large btn-primary" role="button">Список стратегий</a> &nbsp &nbsp  ${strategyName} 
        </div>
        <div class="row">
            <div class="col-md-6"> 
                <div>
                    ${moduleName} 
                </div>
                <br>
                

            </div>

            <div class="col-md-6">

                <div class=" input-append pull-right btn-group">

                    <form action="<c:url value="/Strategy/addGroup" />"  method="post"> 
                        <input type="hidden" name="strategyId" value=${strategyId}>
                        <input class="span5" id="appendedInputButton" name="groupName" style="width: 376px " size="16" type="text">
                        <button type="submit" name="submit" class="btn btn-default"> <img src="/CallCentr/img/add.png" height="20px"></button>
                    </form>


                </div>

                <br>
                <table class="table table-bordered table-hover">
                    <c:forEach var="group" items="${GroupList}" varStatus="myIndex">
                        <tr>
                            <%--  <td>${myIndex.count}</td>   --%>

                            <td style="padding:0px;"> ${group.groupName} 
                                <form action="<c:url value="/Strategy/addModule" />"  method="post"> 
                                    <div class=" input-append pull-right ">
                                        <input type="hidden" name="groupId" value=${group.groupId}>
                                        <input type="hidden" name="strategyId" value=${group.strategy.strategyId}>
                                        <input class="span5" id="appendedInputButton" name="moduleName" style="width: 276px " size="16" type="text">
                                        <button type="submit" name="submit" class="btn btn-default"> <img src="/CallCentr/img/add.png" height="20px"></button>
                                    </div>
                                </form>
                            <td onClick="location = '<c:url value="/Group/deleteGroup?groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'"> <img style="padding:0px;" src="/CallCentr/img/minus.png" height="20px"> </td> 

                        </tr>

                        <c:forEach var="module" items="${group.getModuleList()}" varStatus="myIndex">
                            <tr style="padding:0px;" > 
                                <td style="padding:0px;" onClick="location = '<c:url value="/Strategy/showModule?moduleId=${module.moduleId}&strategyId=${group.strategy.strategyId}"/>'">
                                    ${module.moduleName} 
                                </td>
                                <td onClick="location = '<c:url value="/Group/deleteModule?moduleId=${module.moduleId}&groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'"> <img style="padding:0px;" src="/CallCentr/img/minus.png" height="15px"> </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </table>
            </div>
        </div>

       

    </body>
</html>
