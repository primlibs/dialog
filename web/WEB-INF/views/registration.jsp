<%-- 
    Document   : registration
    Created on : 13.03.2015, 16:43:58
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
        <title>Регистрация</title>
    </head>
    <body class="container" >

    <center><h2>Регистрация</h2></center> 
    <%@include file="/WEB-INF/jsp/error.jsp" %> 
    <br>
    <br>
    <br>
    <!--<h3>Уважаемый пользователь! В данный момент прямая регистрация в нашем сервисе закрыта. Если у Вас есть интерес к нашему продукту, просим обращаться к нам по телефону: +7(342) 259-56-57, либо по email: primsoft59@gmail.ru</h3>-->
    <form class="form-horizontal" action="<c:url value="/Registration/registration" />" method="post">
       
        <div class="form-group">
            <label class="control-label col-xs-3"  for="inputEmail">Email:</label>
            <div class="col-xs-9">
                <input type="email" class="form-control ntSaveForms" name="email" id="inputEmail" placeholder="Email" value="${param['email']}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="inputPassword">Пароль:</label>
            <div class="col-xs-9">
                <input type="password" class="form-control" name="password" id="inputPassword" placeholder="Введите пароль" value="${param['password']}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="confirmPassword">Подтверждение пароля:</label>
            <div class="col-xs-9">
                <input type="password" class="form-control" name="confirmPassword"  id="confirmPassword" placeholder="Введите пароль ещё раз" value="${param['confirmPassword']}">
            </div>
        </div>
        <input type="hidden" name="checkbox" value="agree">
        <br />
        <div class="form-group">
            <div class="col-xs-offset-3 col-xs-9">
                <input type="submit" name="submit"  class="btn btn-primary" value="Регистрация">

            </div>
        </div>
    </form>
    <script type='text/javascript' src='/ntsaveforms.js'></script>
</body>
</html>
