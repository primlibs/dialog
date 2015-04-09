<%-- 
    Document   : registration
    Created on : 13.03.2015, 16:43:58
    Author     : Юрий
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/css_js.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Регистрация</title>
    </head>
    <body class="container" >
        
        <br><br>

    <center><h2>Регистрация</h2></center> <br>
    <%@include file="/WEB-INF/jsp/error.jsp" %> <br>

        <form class="form-horizontal" action="<c:url value="/registration" />" method="post">
            <h3>   Данные компании:</h3>

            <div class="form-group">
                <label class="control-label col-xs-3" for="lastName">Название компании:</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" name="company" id="lastName" placeholder="Введите наименование компании">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-xs-3"  for="inputEmail">Email:</label>
                <div class="col-xs-9">
                    <input type="email" class="form-control" name="emailCompany"  id="inputEmail" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3" for="phoneNumber">Телефон:</label>
                <div class="col-xs-9">
                    <input type="tel" class="form-control" name="phone" id="phoneNumber" placeholder="Введите номер телефона">
                </div>
            </div>
            <h3>   Личные данные:</h3>

           
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
                    <div class="col-xs-offset-3 col-xs-9">
                        <label class="checkbox-inline">
                            <input type="checkbox" value="agree">  Я согласен с <a href="#">условиями</a>.
                        </label>
                    </div>
                </div>
                <br />
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <input type="submit" name="submit"  class="btn btn-primary" value="Регистрация">
                        <input type="reset" class="btn btn-default" value="Очистить форму">
                    </div>
                </div>
            </form>
    </body>
</html>