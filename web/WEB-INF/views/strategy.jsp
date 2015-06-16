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
        <script  src="<c:url value='/js/tiny_mce/tinymce.min.js'/>" ></script>
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
<script src="<c:url value="/js/myJsOnViews/strategy.js" />"></script>
        <div class="row form-group"><span style="font-size: 18px;vertical-align: middle;"><b>Стратегия: <span class="changebleStrategyParam" name="strategyName" id="${strategy.strategyId}" style="cursor: pointer;display: inline-block;">${strategy.strategyName}</span></b></span> <span class="form-group">    
                <a href="<c:url value="/Strategy/failReasonEditor?strategyId=${strategy.strategyId} "/>" class="btn btn-large btn-primary" role="button">Словарь отказов</a> 
            </span></div>
        <div class="row">
            <div class="col-md-6">
                <c:if test="${not empty module}">
                    <div>
                        Реплика клиента:&nbsp;${module.moduleName} 
                    </div>
                </c:if>
                <form action="<c:url value="/Strategy/addBodyModule" />"  method="post"> 
                    <textarea name="bodyText"> ${module.bodyText}   </textarea>
                    <input type="hidden" name="moduleId" value=${module.moduleId}>
                    <input type="hidden" name="strategyId" value=${strategy.strategyId}>
                    <button type="submit" name="submit" class="btn btn-primary"> Сохранить </button>
                </form>

            </div>

            <div class="col-md-6">

                <div class="form-group input-append btn-group">

                    <form class="form-inline" action="<c:url value="/Strategy/addGroup" />"  method="post"> 
                        <input type="hidden" name="strategyId" value=${strategy.strategyId}>
                        <input class="span5" id="appendedInputButton" name="groupName" style="width: 256px " size="16" type="text" placeholder="Новая группа">
                        <button type="submit" name="submit" class="btn btn-primary">Создать группу</button>
                    </form>
                </div>

                <table class="table table-hover table-striped">
                    <c:forEach var="group" items="${strategy.getActiveGroupList()}" varStatus="myIndex">

                        <tr style="background: moccasin;"><td>Группа: <ins>${group.groupName}</ins></td><td><a class="btn btn-primary btn-xs" role="button" href='<c:url value="/Group/deleteGroup?groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'>Удалить</a></td></tr>

                        <tr><td><form class="form-inline" id="addingModuleForm_${myIndex.count}" action='<c:url value="/Strategy/addModule" />'  method="post"></form>
                                <input class="span5" form="addingModuleForm_${myIndex.count}" id="appendedInputButton" name="moduleName" style="width: 128px " size="16" type="text" placeholder="Новый модуль"></td>
                            <td><input type="hidden" form="addingModuleForm_${myIndex.count}" name="groupId" value=${group.groupId}>
                                <input type="hidden" form="addingModuleForm_${myIndex.count}" name="strategyId" value=${group.strategy.strategyId}>
                                <button type="submit" form="addingModuleForm_${myIndex.count}" name="submit" class="btn btn-primary btn-xs">Добавить модуль</button></td></tr>

                        <c:forEach var="module" items="${group.getActiveModuleList()}">
                            <tr><td><span style="cursor: pointer;" ondblclick="location = '<c:url value="/Strategy/showModule?moduleId=${module.moduleId}&strategyId=${group.strategy.strategyId}"/>'">${module.moduleName}</span></td>
                                <td><a class="btn btn-primary btn-xs" role="button" href='<c:url value="/Group/deleteModule?moduleId=${module.moduleId}&groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'>Удалить</a></td></tr>
                        </c:forEach>

                    </c:forEach>
                </table>

                <!--<c:forEach var="group" items="${strategy.getActiveGroupList()}" varStatus="myIndex">
                    <ul>Группа: <ins>${group.groupName}</ins> <a class="btn btn-primary btn-xs" role="button" href="'<c:url value="/Group/deleteGroup?groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'">Удалить</a>
                        <li><form class="form-inline" action="<c:url value="/Strategy/addModule" />"  method="post"> 
                        <input class="span5" id="appendedInputButton" name="moduleName" style="width: 128px " size="16" type="text" placeholder="Новый модуль">
                         <input type="hidden" name="groupId" value=${group.groupId}>
                         <input type="hidden" name="strategyId" value=${group.strategy.strategyId}>
                         <button type="submit" name="submit" class="btn btn-primary btn-xs">Добавить модуль</button>
                            </form></li>
                    <c:forEach var="module" items="${group.getActiveModuleList()}" varStatus="myIndex">
                        <li><span style="cursor: pointer;" ondblclick="location = '<c:url value="/Strategy/showModule?moduleId=${module.moduleId}&strategyId=${group.strategy.strategyId}"/>'">${module.moduleName}</span> 
                            <a class="btn btn-primary btn-xs" role="button" href="'<c:url value="/Group/deleteModule?moduleId=${module.moduleId}&groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'">Удалить</a></li>
                    </c:forEach>
            </ul>

                </c:forEach>-->

            </div>
        </div>

        <script type="text/javascript">
            tinymce.init({
                selector: "textarea",
                plugins: [
                    " autolink lists link image charmap print preview anchor",
                    " visualblocks code fullscreen",
                    "media table contextmenu paste "
                ],
                toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
            <%-- width: 800 --%>
            });
        </script>



    </body>
</html>
