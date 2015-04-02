<%-- 
   Document   : changePassword
   Created on : 02.04.2015, 14:55:18
   Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title> Смена пароля</title>
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <br>
        ${error}

        <form action="<c:url value="/changePassword" />" style="margin-top: 58px;" method="post">

            <div class="form-group">
                <label class="control-label col-xs-3" for="inputPassword">Старый пароль:</label>
                <div class="col-xs-9">
                    <input type="password" class="form-control" name="oldPassword" id="inputPassword" placeholder="Введите пароль">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="inputPassword">новый пароль:</label>
                <div class="col-xs-9">
                    <input type="password" class="form-control" name="password" id="inputPassword" placeholder="Введите пароль">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="confirmPassword">Подтвердите новый пароль:</label>
                <div class="col-xs-9">
                    <input type="password" class="form-control" name="confirmPassword"  id="confirmPassword" placeholder="Введите пароль ещё раз">
                </div>
            </div>
                 
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <input type="submit" name="submit"  class="btn btn-primary" value="Добавить">
                    <input type="reset" class="btn btn-default" value="Очистить форму">
                </div>
            </div>
        </form>




    </body>
</html>
