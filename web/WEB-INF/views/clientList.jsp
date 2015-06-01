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
            
            <c:set var="selectSize" value="${tags.size()}"/>
                    <c:if test="${tags.size()>5}">
                        <c:set var="selectSize" value="5"/>
                    </c:if>
                    <c:if test="${tags.size()==0}">
                        <c:set var="selectSize" value="1"/>
                    </c:if>
            
            <div style="margin-bottom: 10px;"> 
                <form role="form" class="form-inline" action="<c:url value="/Client/clientList" />">
                    <input type="text" class="form-control" style="width: 150px;" name="uid" id="uidInput" placeholder="Уникальный ИД">
                    <input type="text" class="form-control" style="width: 150px;" name="nameCompany" id="nameCompanyInput" placeholder="Компания">
                    <input type="text" class="form-control" style="width: 150px;" name="adress" id="adressInput" placeholder="Адрес">
                    <input type="text" class="form-control" style="width: 150px;" name="name" id="nameInput" placeholder="Контактное лицо">
                    <input type="text" class="form-control" style="width: 150px;" name="phone" id="phoneInput" placeholder="Телефон">
                    <c:if test="${not empty tags}">
                        <select style="vertical-align: middle;" size="${selectSize}" multiple="multiple" class="form-control" name="tags">
                            <c:forEach var="tag" items="${tags}">
                                <option value="${tag.tagId}">${tag.name}</option>
                            </c:forEach>
                        </select>
                    </c:if>
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
                </tr>
                <c:forEach var="client"  items="${clients}" >
                    <tr style="cursor: pointer;" onclick="location = '<c:url value="/Client/oneClient?clientId=${client.clientId}"/>'"><td>${client.uniqueId}</td>
                        <td>${client.nameCompany}</td>
                    <td>${client.address}</td>
                    <td>${client.nameSecretary}</td>
                    <td>${client.phoneSecretary}</td>
                    <td>${client.nameLpr}</td>
                    <td>${client.phoneLpr}</td>
                </c:forEach>
            </table>
        </div>
        
    </body>
</html>
