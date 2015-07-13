<%-- 
    Document   : recoveryPassword
    Created on : 07.04.2015, 11:21:55
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title>Восстановление пароля</title>
    </head>
    <body class="container">
        
        <%@include file="/WEB-INF/jsp/error.jsp" %> <%@include file="/WEB-INF/jsp/message.jsp" %> 
        
        <center style="margin-top: 50px;">
        <div class="">
            
                <h2>восстановление пароля</h2>
            
        </div>  
    </center>
        
        <div class="container col-sm-6 col-sm-offset-3">
        <form action="<c:url value="/User/recoveryPassword" />"  method="post" style="margin-top: 100px;">

            <div class="input-group">
                <span class="input-group-addon">e-mail</span>
                <input type="email" class="form-control" name="email" id="inputEmail" placeholder="e-mail" value="${email}">
                <span class="input-group-btn">
                    <button name="submit" class="btn btn-primary" type="input" value="Восстановить">Восстановить</button>
                  </span>
              </div>
           
            <!--<div class="form-group">
                <label class="control-label col-xs-3"  for="inputEmail">Email:</label>
                <div class="col-xs-9">
                    <input type="email" class="form-control" name="email" id="inputEmail" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <input type="submit" name="submit"  class="btn btn-primary" value="Отправить">
                  
                </div>
            </div>-->

        </form>
        </div>
    </body>

</html>