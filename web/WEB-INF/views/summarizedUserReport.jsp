<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta content="text/html; charset=UTF-8">
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %>
        <%@include file="/WEB-INF/jsp/message.jsp" %> 
        <div class="row ">
            <h4> Отчет по операторам </h4>
        </div>
            <div>
            <form role="form" class="form-inline" action="<c:url value="/Client/clientList" />">
                <div class="form-group">
                    <div class="input-group date" id="datetimepicker2">
                        <label for="dateCampaignFrom">Дата начала периода</label>
                        <input type="text" name="dateCampaignFrom" id="dateCampaignFrom" class="form-control" />
                        <span class="input-group-addon">
                           <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                        </span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group date" id="datetimepicker2">
                        <label for="dateCampaignTo">Дата окончания периода</label>
                        <input type="text" name="dateCampaignTo" id="dateCampaignTo" class="form-control" />
                        <span class="input-group-addon">
                           <i class="glyphicon glyphicon-calendar glyphicon-nonescaped"></i>
                        </span>
                    </div>
                </div> 
            </form>
            </div>
        
    </body>
</html>