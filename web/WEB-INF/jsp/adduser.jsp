<%-- 
    Document   : AddUser
    Created on : 23.03.2015, 11:54:39
    Author     : Юрий
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Добавление пользователя</title>
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menutopadm.jsp" %>
        <br>
        
        
        ${error}
        
        <form action="<c:url value="/adduser" />" style="margin-top: 59px;" method="post">

            <div class="form-group">
                <label class="control-label col-xs-3" for="lastName">Фамилия:</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" name="surname" id="lastName" placeholder="Введите фамилию">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="firstName">Имя:</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" name="name" id="firstName" placeholder="Введите имя">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="firstName">Отчество:</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" name="patronymic" id="firstName" placeholder="Введите отчество">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3"  for="inputEmail">Email:</label>
                <div class="col-xs-9">
                    <input type="email" class="form-control" name="email" id="inputEmail" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="inputPassword">Пароль:</label>
                <div class="col-xs-9">
                    <input type="password" class="form-control" name="password" id="inputPassword" placeholder="Введите пароль">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="confirmPassword">Подтвердите пароль:</label>
                <div class="col-xs-9">
                    <input type="password" class="form-control" name="confirmPassword"  id="confirmPassword" placeholder="Введите пароль ещё раз">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="selectRole">Выбрать роль:</label>
                <div class="col-xs-9">
                    <select name="role">
                        <option>admin</option>
                        <option>user</option>
                    </select>
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
