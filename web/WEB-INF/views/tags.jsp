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
            <h3>Тэги</h3>
        </div>
        <div class="form-group">
                    <form role="form" class="form-inline" action="<c:url value="/Tag/create" />"  method="post"> 
                        <input class="span5" id="appendedInputButton" name="name" style="width: 256px " size="16" type="text" value="${name}">
                        <button type="submit" name="submit" class="btn btn-primary">  Добавить  </button>
                    </form>
                </div>
        <div class="row ">                
                    <c:if test="${empty tags}">
                        <h5> Нет добавленных тэгов </h5>
                    </c:if>
                        <c:if test="${not empty tags}">
        <table class="table table-bordered table-hover">
            <tr>
            <th>Тэг</th>
                <th></th>
            </tr>
        <c:forEach var="tag" items="${tags}">
            <tr>
                    <td>${tag.name}</td>
                        <td style="cursor: pointer;" onClick="location = '<c:url value="/Tag/delete?tagId=${tag.tagId}"/>'"> Удалить </td>
                    </tr>
        </c:forEach>
        </table>
                        </c:if>
        </div>
    </body>
</html>