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
        <!--<script src="<c:url value="/js/myJsOnViews/updateParam.js" />"></script>-->
        <div class="row form-group"><span class="standart_text" style="font-size: 18px;vertical-align: middle;"><b>Сценарий: <span class="changebleStrategyParam changebleParam"  name="strategyName" id="${strategy.strategyId}" style="cursor: pointer;display: inline-block;">${strategy.strategyName}</span></b></span> <span class="form-group">    
                <c:if test="${strategy.getIsin()eq null}">
                    <a style="float: right;" href="<c:url value="/Strategy/failReasonEditor?strategyId=${strategy.strategyId} "/>" class="btn btn-large btn-danger" role="button">Словарь отказов</a> 
                </c:if>
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
                <div id="sortableGroupsDiv">
                    <c:set var="groupPosition" value="1"></c:set>
                    <c:forEach var="group" items="${groupMap.keySet()}" varStatus="myIndex">
                        <table class="table table-hover table-striped sortableGroup" data-position="${groupPosition}" data-groupid="${group.groupId}">
                            <c:set var="groupPosition" value="${groupPosition+1}"></c:set>

                                <tr style="background: moccasin;cursor: pointer;" class="groupHeader"><td data-groupid="${group.groupId}">Группа: <ins><span data-method="changegroupname" name="groupname_${group.groupId}" data-groupid="${group.groupId}" id="${group.groupId}" data-method="changegroupname">${group.groupName}</span></ins></td> 
                                <td><a class="btn btn-warning btn-xs changingbtn" id="groupname_${group.groupId}" href="#">Изменить</a></td> 
                                <td><a href="#" id="${group.groupId}" class="btn btn-primary btn-xs deletinggrouphref"
                               data-toggle="modal"
                               data-target="#deleteWindow">Удалить</a></td>
                                <!--<td><a class="btn btn-primary btn-xs" role="button" href='<c:url value="/Group/deleteGroup?groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'>Удалить</a></td>-->
                            </tr>

                            <tr style="cursor: pointer;" class="groupHeader"><td><form class="form-inline" id="addingModuleForm_${myIndex.count}" action='<c:url value="/Strategy/addModule" />'  method="post"></form>
                                    <input class="span5" form="addingModuleForm_${myIndex.count}" id="appendedInputButton" name="moduleName" style="width: 128px " size="16" type="text" placeholder="Новый модуль"></td>
                                <td></td>
                                <td><input type="hidden" form="addingModuleForm_${myIndex.count}" name="groupId" value=${group.groupId}>
                                    <input type="hidden" form="addingModuleForm_${myIndex.count}" name="strategyId" value=${group.strategy.strategyId}>
                                    <button type="submit" form="addingModuleForm_${myIndex.count}" name="submit" class="btn btn-primary btn-xs">Добавить модуль</button></td></tr>
                                    <c:set var="modulePosition" value="1"></c:set>
                                <c:forEach var="module" items="${groupMap.get(group)}">
                                    <tr class="sortableModule${group.getId()}" id="${group.getId()}_${module.position}" data-position="${modulePosition}" data-moduleid="${module.moduleId}" style="cursor: pointer;">
                                            <c:set var="modulePosition" value="${modulePosition+1}"></c:set>
                                        <td><span style="cursor: pointer;" ondblclick="location = '<c:url value="/Strategy/showModule?moduleId=${module.moduleId}&strategyId=${group.strategy.strategyId}"/>'">
                                                <span data-method="changemodulename" data-moduleid="${module.moduleId}" name="modulename_${module.moduleId}" id="${module.moduleId}" style="color:#${module.hexcolor}">${module.moduleName}</span>
                                            </span></td>
                                        <td><input type="text" class="pick-a-color form-control" moduleId="${module.moduleId}"></td>     
                                        <td><a class="btn btn-warning btn-xs changingbtn" id="modulename_${module.moduleId}" href="#">Изменить</a></td> 
                                        <td><a href="#" id="${module.moduleId}" class="btn btn-primary btn-xs deletingmodulehref"
                                    data-toggle="modal"
                                    data-target="#deleteWindow1">Удалить</a></td>
                                        <!--<td><a class="btn btn-primary btn-xs" role="button" href='<c:url value="/Group/deleteModule?moduleId=${module.moduleId}&groupId=${group.groupId}&strategyId=${group.strategy.strategyId}"/>'>Удалить</a></td>-->
                                    </tr>
                                </c:forEach>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="modal fade" id="deleteWindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title" id="myModalLabel">Удалить групу?</h4>
                    </div>
                    <div class="modal-body">
                        <form id="deleteForm" action="<c:url value="/Group/deleteGroup"/>" method="post">
                                <input type="hidden" name="strategyId" value="${strategy.strategyId}">
                                <input type="hidden" name="groupIdtoDelete" value="">
                            <p><input class="btn btn-danger" type="submit" value="Удалить">
                        </form>

                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="deleteWindow1" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title" id="myModalLabel">Удалить модуль?</h4>
                    </div>
                    <div class="modal-body">
                        <form id="deleteForm1" action="<c:url value="/Group/deleteModule"/>" method="post">
                                <input type="hidden" name="strategyId" value="${strategy.strategyId}">
                                <input type="hidden" name="groupId" value="${group.groupId}">
                                <input type="hidden" name="moduleIdtoDelete" value="">
                            <p>     <input class="btn btn-danger" type="submit" value="Удалить">
                        </form>

                    </div>
                </div>
            </div>
        </div>
        <script>$('.deletingmodulehref').click(function(){var cuid = $(this).attr('id');$('[name = moduleIdtoDelete]').val(cuid);});</script>
        <script>$('.deletinggrouphref').click(function(){var cuid = $(this).attr('id');$('[name = groupIdtoDelete]').val(cuid);});</script>
        <script type="text/javascript">
            $(document).ready(function () {
             $(".pick-a-color").pickAColor({
                 showHexInput            : false
             });
            });

        </script>
        <script type="text/javascript">
            $(".pick-a-color").on("change", function () {
              $('#'+$(this).attr('moduleId')).css('color','#'+$(this).val());
              var moduleId=$(this).attr('moduleId');
              var newColor=$(this).val();
              $.ajax({
                    url: "../Strategy/updateModuleHexcolor?moduleId=" + moduleId + "&newColor=" + newColor,
                    dataType: "json",
                    cache: false,
                    success: function (json) {
                    },
                    error: function (json) {
                        alert("Что-то пошло не так: " + json);
                    }
                });
            });
        </script>    
        <script type="text/javascript">
            tinymce.init({
                selector: "textarea",
                theme: "modern",
                height: '400px',
                menubar: false,
                toolbar_items_size: 'small',
                language: 'ru',
                plugins: [
                    " textcolor colorpicker textpattern"
                ],
                toolbar: " styleselect | bold italic underline strikethrough | fontselect fontsizeselect | alignleft aligncenter alignright alignjustify | forecolor backcolor",
            <%-- width: 800 --%>
            });
        </script>
    </body>
</html>
