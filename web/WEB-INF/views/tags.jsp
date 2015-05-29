<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <div class=" input-append pull-right btn-group">
                    <form action="<c:url value="/Tag/create" />"  method="post"> 
                        <input class="span5" id="appendedInputButton" name="name" style="width: 376px " size="16" type="text">
                        <button type="submit" name="submit" class="btn btn-default">  Добавить  </button>
                    </form>
                </div>
                    <c:if test="${empty tags}">
                        <h5> Нет добавленных тэгов </h5>
                    </c:if>
                        <c:if test="${!empty tags}">
        <table class="table table-bordered table-hover">
            <tr>
            <th>Тэг</th>
                <th></th>
            </tr>
        <c:forEach var="tag" items="tags">
            <tr>
                    <td>${tag.name}</td>
                        <td style="cursor: pointer;" onClick="location = '<c:url value="/Tag/delete?tagId=${tag.tagId}"/>'"> Удалить </td>
                    </tr>
        </c:forEach>
        </table>
                        </c:if>
    </body>
</html>