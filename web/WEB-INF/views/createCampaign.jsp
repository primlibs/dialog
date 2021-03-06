<%-- 
    Document   : createCampaign
    Created on : 29.04.2015, 15:39:22
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row">
            <form class="form-horizontal" action="<c:url value="/Event/createCampaign" />" method="post">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="firstName">Название кампании:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" name="name" id="firstName" value="${numericName}" placeholder="Название кампании">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="selectRole">Выбрать сценарий</label>
                    <div class="col-xs-9">
                        <select name="strategyId">
                            <c:forEach var="strategy" items="${strategies}" varStatus="myIndex">
                                <option value="${strategy.strategyId}">${strategy.strategyName}</option>

                            </c:forEach>
                        </select>
                    </div>
                </div>   
             

                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <input type="submit" name="submit"  class="btn btn-primary" value="Добавить">
                    </div>
                </div>

            </form>
        </div>

    </body>
</html>
