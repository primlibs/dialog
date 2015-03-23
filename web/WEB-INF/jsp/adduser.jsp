<%-- 
    Document   : AddUser
    Created on : 23.03.2015, 11:54:39
    Author     : Юрий
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Добавление пользователя</title>
    </head>
    <body>
         <form action="<c:url value="/adduser" />" method="post">
           
            Email личный: <input type="text" name="email" maxlength=60>
            <br>
            Пароль: <input type="password" name="password" size="10" maxlength=20>
            <br>
            фамилия: <input type="text" name="surname" size="10" maxlength=30> <br>
            Имя: <input type="text" name="name" size="10" maxlength=30> <br>
            Отчество: <input type="text" name="patronymic" size="10" maxlength=30> <br>

            <p>
            <table>
                <tr>
                    <th><small>
                            <input type="submit" name="submit" value="Сохранить">
                        </small>
            </table>
        </form>
        
        
       
    </body>
</html>
