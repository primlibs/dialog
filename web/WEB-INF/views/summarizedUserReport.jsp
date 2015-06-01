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
            <h4> Отчет по операторам </h4>
        </div>
            <div class="row">
            <form role="form" class="form-inline" action="<c:url value="/UserReport/summarizedReport" />">
                <div class="form-group">
                    Дата начала периода
                    <div class="input-group date" id="datetimepicker3"><input type="text" name="dateCampaignFrom" id="dateCampaignFrom" class="form-control" />
                        <span class="input-group-addon">
                           <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                        </span>
                    </div>
                </div>
                <div class="form-group">
                     Дата окончания периода
                    <div class="input-group date" id="datetimepicker4">
                        <input type="text" name="dateCampaignTo" id="dateCampaignTo" class="form-control" />
                        <span class="input-group-addon">
                           <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                        </span>
                    </div>
                </div>
                <div class="form-group">
                <input type="submit" name="submit"  class="btn btn-primary" value="Выбрать">
                </div>
            </form>
            </div>
                <div class="row">
                    <table class="table table-bordered table-hover">
                <tr>
                    <th> Менеджер </th>
                    <th> Завершены отрицательно </th>
                    <th> Завершены положительно </th>
                    <th> Всего назначено </th>
                </tr>
                <c:forEach var="entry" items="${reportMap.entrySet}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value.get('successful')}</td>
                        <td>${entry.value.get('failed')}</td>
                        <td>${entry.value.get('all')}</td>
                    </tr>
                </c:forEach>
                </div>
                
    </body>
</html>