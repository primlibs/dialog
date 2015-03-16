<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
        тут будет проверка авторизации, если Вы не авторизированы будет перенаправление на авторизацию !!
        <br>
        На странице авторизации будет ссылка на регистрирацию.
        <br>
        <a href="http://62.76.41.244/CallCentr/registration">перейти на регистрацию</a>

        <table class="header">
            <tr>
                <td><a href="<c:url value="/welcome.do" />"> Home</a></td>
                <td align="right"><a href="<c:url value="/j_spring_security_logout"/>">Logout</a></td>
            </tr>
        </table>
    </body>
</html>
