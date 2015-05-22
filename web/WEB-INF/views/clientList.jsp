<%-- 
    Document   : clientList
    Created on : 29.04.2015, 14:23:10
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
        
        <div class="row "><h3>Клиенты</h3>
            
            <div style="margin-bottom: 10px;"> 
                <form role="form" class="form-inline" action="<c:url value="/Client/clientList" />">
                    <input type="text" class="form-control" name="uid" id="uidInput" placeholder="Уникальный ИД">
                    <input type="text" class="form-control" name="nameCompany" id="nameCompanyInput" placeholder="Компания">
                    <input type="text" class="form-control" name="adress" id="adressInput" placeholder="Адрес">
                    <input type="text" class="form-control" name="name" id="nameInput" placeholder="Контактное лицо">
                    <input type="text" class="form-control" name="phone" id="phoneInput" placeholder="Телефон">
                    <input type="submit" name="submit"  class="btn btn-primary" value="Поиск">
                </form>
            </div>
            
            <table class="table table-bordered table-hover">
                <tr>
                    <th> Уникальный ИД </th>
                    <th> Наименование </th>
                    <th> Адрес </th>
                    <th> Контактное лицо </th>
                    <th> Телефон К.Л. </th>
                    <th> Лицо принимающее решения </th>
                    <th> Телефон Л.П.Р. </th>
                    <th> Комментарий </th>
                </tr>
                <c:forEach var="client"  items="${clients}" >
                    <tr style="cursor: pointer;" onclick="location = '<c:url value="/Client/oneClient?clientId=${client.clientId}"/>'"><td>${client.uniqueId}</td>
                        <td>${client.nameCompany}</td>
                    <td>${client.address}</td>
                    <td>${client.nameSecretary}</td>
                    <td>${client.phoneSecretary}</td>
                    <td>${client.nameLpr}</td>
                    <td>${client.phoneLpr}</td>
                    <td>${client.comment}</td></tr>
                </c:forEach>
            </table>
        </div>
        
    </body>
</html>
