<%-- 
    Document   : event
    Created on : 18.05.2015, 18:09:46
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> </title>
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >

        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 

        <div class="row ">
             <h4>  Евент:  ${campaign.name} &nbsp Стратегия: ${campaign.strategy.strategyName} </h4>
               <input type="hidden" name="campaignId" value=${param.campaignId}>
               
            <a href="<c:url value="/Event/campaign"/>" class="btn btn-large btn-primary" role="button">Выбрать кампанию...</a>

            Клиент, Адрес, Секретарь, ЛПР, Телефон секретаря, Телефон ЛПР, Комментарий
            
             <div class="col-md-6"> 
                 Модуль ответа(название - ссылка) 
                 
             </div>
            
             <div class="col-md-6"> 
                  
                 Группа 1
             </div>
            
        </div>
    </body>
</html>
