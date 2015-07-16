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
    <%@include file="/WEB-INF/jsp/css_js.jsp" %>
  </head>
  <body class="container" >
    <%@include file="/WEB-INF/jsp/menu.jsp" %>
    <%@include file="/WEB-INF/jsp/error.jsp" %> 
    <%@include file="/WEB-INF/jsp/message.jsp" %> 

    <div class="row "><h3>Клиенты</h3>

      <c:set var="selectSize" value="${tags.size()}"/>
      <c:if test="${tags.size()>5}">
          <c:set var="selectSize" value="5"/>
      </c:if>
      <c:if test="${tags.size()==0}">
          <c:set var="selectSize" value="1"/>
      </c:if>

      <div class="form-group"> 
        <form role="form" class="form-inline form-group" action="<c:url value="/Client/clientList" />">
          <input type="text" class="form-control" style="width: 170px;" name="uid" id="uidInput" placeholder="Уникальный ИД" value="${uid}">
          <input type="text" class="form-control" style="width: 170px;" name="nameCompany" id="nameCompanyInput" placeholder="Компания" value="${nameCompany}">
          <input type="text" class="form-control" style="width: 170px;" name="adress" id="adressInput" placeholder="Адрес" value="${adress}">
          <input type="text" class="form-control" style="width: 170px;" name="name" id="nameInput" placeholder="Контактное лицо" value="${name}">
          <input type="text" class="form-control" style="width: 170px;" name="phone" id="phoneInput" placeholder="Телефон" value="${phone}">
          <c:if test="${not empty tags}">
              <select style="vertical-align: middle;width: 170px;" size="${selectSize}" multiple="multiple" class="form-control" name="tagIds">
                <c:forEach var="tag" items="${tags}">
                    <option value="${tag.tagId}">${tag.name}</option>
                </c:forEach>
              </select>
          </c:if>
          <input type="submit" name="submit"  class="btn btn-primary" value="Поиск">
        </form>

        <form action="<c:url value="/Client/getXls" />" >
          <input type="hidden"  name="uid"  value="${uid}">
          <input type="hidden"  name="nameCompany"  value="${nameCompany}">
          <input type="hidden"  name="adress"  value="${adress}">
          <input type="hidden"  name="name"  value="${name}">
          <input type="hidden"  name="phone" value="${phone}">
          <c:if test="${not empty selectedTags}">
                <c:forEach var="tagId" items="${selectedTags}">
                    <input type="hidden" name="tags" value="${tagId}" >
                </c:forEach>
          </c:if>
          <input type="submit" name="submit" class="btn btn-primary" value="Скачать excel" >
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
              <td>${client.getFormattedPhoneSec()}</td>
              <td>${client.nameLpr}</td>
              <td>${client.getFormattedPhoneLpr()}</td>
            </c:forEach>
      </table>
    </div>

  </body>
</html>
