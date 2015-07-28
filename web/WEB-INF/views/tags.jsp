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
        <script src="<c:url value="/js/myJsOnViews/tagList.js" />"></script>
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
                    <td class="changebleParam" data-method="changename" data-tagid="${tag.tagId}" name="companyname_${tag.tagId}" data-parametr="tagname">${tag.name}</td>
                    <td><a href="#" id="${tag.tagId}" class="btn btn-danger btn-xs deletinghref"
                                        data-toggle="modal"
                                        data-target="#deleteWindow">Удалить</a></td>
                    </tr>
        </c:forEach>
        </table>
        <div class="modal fade" id="deleteWindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                <h4 class="modal-title" id="myModalLabel">Удалить тэг?</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form id="deleteTagForm" action="<c:url value="/Tag/delete"/>" method="post">
                                                    
                                                    <p><label><input type="checkbox" name="deleteLinks">Стереть тэг из всех клиентов</label>
                                                        <input type="hidden" name="tagIdtoDelete" value="">
                                                    <p>     <input class="btn btn-danger" type="submit" value="Удалить">
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </c:if>
        </div>
    </body>
</html>