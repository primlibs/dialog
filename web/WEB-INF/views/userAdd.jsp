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
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container" >
        <%@include file="/WEB-INF/jsp/menu.jsp" %>
        <%@include file="/WEB-INF/jsp/error.jsp" %> 
        <%@include file="/WEB-INF/jsp/message.jsp" %> 


        <form role="form" action="<c:url value="/User/userAdd" />"  method="post">

            <div class="form-group">
                <a href="<c:url value="/User/userList"/>" class="btn btn-large" role="button"> <= Список пользователей</a>
            </div>
            <div class="form-group">
                <label for="lastName">Фамилия*:</label>
                    <input type="text" class="form-control" name="surname" id="lastName" placeholder="Фамилия" value="${surname}">
                
            </div>
            <div class="form-group">
                <label for="firstName">Имя*:</label>
                    <input type="text" class="form-control" name="name" id="firstName" placeholder="Имя" value="${name}">
            </div>
            <div class="form-group">
                <label for="firstName">Отчество:</label>
                    <input type="text" class="form-control" name="patronymic" id="firstName" placeholder="Отчество" value="${patronymic}">
                
            </div>
            <div class="form-group">
                <label  for="inputEmail">Email*:</label>
                    <input type="email" class="form-control" name="email" id="inputEmail" placeholder="Email" value="${email}">
                
            </div>
            <div class="form-group">
                <label for="selectRole">Роль**:</label>
                    <select name="role">
                        <option value="user">Пользователь</option>
                        <option value="admin">Администратор</option>
                    </select>
            </div>          
            <div class="form-group">
                    <input type="submit" name="submit"  class="btn btn-primary" value="Добавить ***">
            </div>

        </form>
                    
                    <div class="row" style="text-align: justify;bottom: 10px;">
                        <b> * Обязательные для заполнения поля.
                            <br><br>
                            ** <ins>Пользователь</ins> - рядовой менеджер, использующий программу для конвейерной работы со списком клиентов. Контактирует с клиентами используя речевые модули. По завершении контакта, оставляет отметку и комментарий о результате.
                          <br>
                          <ins>Администратор</ins> - пользователь программы, имеющий расширенные права для работы со списками клиентов, стратегий, кампаний, а также списком других пользователей. Распределяет запланированные в рамках кампаний контакты с клиентами между пользователями, редактирует речевые модули. Имеет доступ к мониторингу работы пользователей, может выполнять роль обычного пользователя, принимая участие в кампаниях.
                          <br><br>
                          *** Пароль назначемый по умолчанию:&nbsp 0000 </b>
                    </div>

    </body>
</html>
