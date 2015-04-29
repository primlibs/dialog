<%-- 
    Document   : eventAdd
    Created on : 29.04.2015, 15:39:22
    Author     : Юрий
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

        <div class="row">
            <form class="form-horizontal" action="<c:url value="/Event/eventAdd" />" method="post">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="firstName">Название эвента:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" name="name" id="firstName" placeholder="${numericName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="selectRole">Выбрать стратегию:</label>
                    <div class="col-xs-9">
                        <select name="strategyId">
                            <c:forEach var="cabinet" items="${pk}" varStatus="myIndex">
                                <option value="${cabinet}">${cabinet}</option>

                            </c:forEach>
                        </select>
                    </div>
                </div>   
                <div class="form-group">
                    <label class="control-label col-xs-3" for="insertDate">Дата создания:</label>
                    <div class="col-xs-9">
                        <input type="date" class="form-control" name="insertDate" id="insertDate" placeholder="Дата создания">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="endDate">Дата окончания:</label>
                    <div class="col-xs-9">
                        <input type="date" class="form-control" name="endDate" id="endDate" placeholder="Дата окончания">
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
