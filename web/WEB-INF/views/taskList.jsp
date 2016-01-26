
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <div class="row ">
            <h3>Задачи</h3>
        </div>

        <div class="form-group">
            <a href="<c:url value="#"/>" id="newTask" class="btn btn-primary" role="button">Добавить задачу</a>
        </div> 

        <table class="table table-bordered table-hover">

            <tr>
                <th>Название</th>
                <th>Дата</th>
                <th>Исполнитель</th>
            </tr>
            <c:forEach var="task" items="${taskList}">
                <tr>
                    <td>${task.name}</td>
                    <td>${task.name}</td>
                    <td>${task.performer.surname} ${task.performer.name}</td>
                </tr>
            </c:forEach>
        </table>
        <div class="modal fade" id="newTaskWindow">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">Новая задача</h4>
                    </div>
                    <div class="modal-body content" style="min-height:400px;" pos="0">
                        <div class="col-md-12">
                            <form class="form-control" action="">
                                <input class="form-control" type="text" name="name"/>
                                <input class="form-control" type="text" name="taskDate"/>
                                <select class="form-control" name="performerId">
                                    <c:forEach var="entity" items="${userMap}">
                                        <option value="${entity.key}">${entity.value.surname} ${entity.value.name}</option>
                                    </c:forEach>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#newTask").click(function () {
                    $("#newTaskWindow").modal('show');
                    return false;
                });
            });
        </script>

    </body>
</html>
