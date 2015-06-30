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
        <script type="text/javascript">
            $(function () {
                //Установим для виджета русскую локаль с помощью параметра language и значения ru
                $('#datetimepicker3').datetimepicker(
                        {language: 'ru'}
                );
                 $('#datetimepicker4').datetimepicker(
                        {language: 'ru'}
                );
            });
        </script>
        <div class="row">
            <h4> Отчет по модулям </h4>
        </div>
                <div class="row">
                    <table class="table table-bordered table-hover">
                <tr>
                    <th> Модуль </th>
                    <th> Отрицательные исходы(%*) </th>
                    <c:forEach var="user" items="${users}">
                        <th> ${user.getShortName()} </th>
                    </c:forEach>
                    
                </tr>
                <c:forEach var="moduleEntry" items="${reportData.entrySet()}">
                    <tr>
                        <td>${moduleEntry.getKey().getModuleName()}</td>
                        <c:forEach var="userEntry" items="${moduleEntry.getValue().entrySet()}">
                            <td>${userEntry.getValue()}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                    </table>
                </div>
        * - % указан от числа контактов в которых модуль использовался хотя бы один раз.
                
    </body>
</html>