<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Проэкт для Call центров</title>
    </head>

    <body>
        <h1> Главная </h1>
        
        <BR>
        <a href=" http://62.76.41.244/CallCentr/adduser">Добавить Usera</a>
              <br>
        <table class="header">
            <tr>

                <td align="right"> <a href="<c:url value="/logout"/>">Выйти</a></td>
            </tr>
        </table>
    </body>
</html>
