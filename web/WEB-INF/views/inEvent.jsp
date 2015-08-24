<%-- 
    Document   : event
    Created on : 18.05.2015, 18:09:46
    Author     : Юрий
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container">
        <script src="<c:url value="/js/myJsOnViews/event.js" />"></script>
        <script type="text/javascript">
            $(function () {
                //Установим для виджета русскую локаль с помощью параметра language и значения ru
                $('#datetimepicker2').datetimepicker(
                        {language: 'ru'}
                );
                $('#datetimepicker1').datetimepicker(
                        {language: 'ru'}
                );
            });
        </script>

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row form-group">
            <span style="font-size: 18px;">   Сценарий: ${strategy.strategyName}</span>
        </div>
            <div class="row">
                            
                    <div class="row">
                        <div class="col-md-7" style="float:left;" id="moduleShow"></div>

                        <!--<div class="col-md-5" style="float:right;">
                            <div class="col-md-7"  data-spy="scroll" style="height: 100vh ;">-->
                        <!--<div class="col-md-3" >
                                <c:forEach var="entry"  items="${аctiveMap}" >
                                    <ul> ${entry.key.groupName} 
                                        <c:forEach var="module" items="${entry.value}" >
                                            <li style="cursor: pointer;" class="showableModule" id="${module.moduleId}"> <ins>
                                                    ${module.moduleName}
                                                </ins></li>
                                            </c:forEach>
                                    </ul>
                                </c:forEach>
                            </div>-->

                        <div class="col-md-3" >
                            <table style="width: 100%;">
                                <c:forEach var="entry"  items="${аctiveMap}" >
                                    <tr style="background: moccasin;cursor: pointer;" class="groupwithmodules" data-groupid="${entry.key.groupId}"><td style="text-align: center;">Группа ${entry.key.groupName}:</td> </tr>
                                        <c:forEach var="module" items="${entry.value}" >
                                            <tr><td><span style="display: block;cursor: pointer;color: slategrey" class="showableModule hidingModule checkIn" data-groupid="${entry.key.groupId}" id="${module.moduleId}">
                                                    ${module.moduleName}
                                                    </span></td></tr>
                                            </c:forEach>
                                </c:forEach>
                                </table>
                        </div>


                           
                        </div>

                    </div>
                    <div hidden="1" id="moduleBufer" ">

                        <c:forEach var="entry"  items="${аctiveMap}" >
                            <c:forEach var="module"  items="${entry.value}" >
                                <div id="${module.moduleId}" class="hiddenModule">
                                    <span style="display: block;cursor: pointer;font-size:18px;color: slategrey" class="showableModule" id="${module.moduleId}"><ins><b>
                                                    ${module.moduleName}
                                                    </b></ins></span>
                                    <br><br>
                                    ${module.bodyText}
                                </div>
                            </c:forEach>
                        </c:forEach>
                    </div>
            
    </body>
</html>
