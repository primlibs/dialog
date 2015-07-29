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

      <c:set var="selectSize" value="${tagMap.size()}"/>
      <c:if test="${tagMap.size()>5}">
          <c:set var="selectSize" value="5"/>
      </c:if>
      <c:if test="${tagMap.size()==0}">
          <c:set var="selectSize" value="1"/>
      </c:if>

      <div class="form-group"> 
        <form role="form" class="form-inline form-group" action="<c:url value="/Client/clientList" />">
          <input type="text" class="form-control" style="width: 170px;" name="uid" id="uidInput" placeholder="Уникальный ИД" value="${uid}">
          <input type="text" class="form-control" style="width: 170px;" name="nameCompany" id="nameCompanyInput" placeholder="Компания" value="${nameCompany}">
          <input type="text" class="form-control" style="width: 170px;" name="adress" id="adressInput" placeholder="Адрес" value="${adress}">
          <input type="text" class="form-control" style="width: 170px;" name="name" id="nameInput" placeholder="Контактное лицо" value="${name}">
          <input type="text" class="form-control" style="width: 170px;" name="phone" id="phoneInput" placeholder="Телефон" value="${phone}">
          <c:if test="${not empty tagMap}">
              <select style="vertical-align: middle;width: 170px;" size="${selectSize}" multiple="multiple" class="form-control" name="tagIds">
                <c:forEach var="tagEntry" items="${tagMap.entrySet()}">
                    <option value="${tagEntry.getKey()}" <c:if test="${not empty selectedTagsMap.get(tagEntry.getKey())}">selected</c:if>>${tagEntry.getValue().name}</option>
                </c:forEach>
              </select>
          </c:if>
          <input type="submit" name="submit"  class="btn btn-primary" value="Поиск"><br>
          <label  style="float: left;">
              <input id="tagCrossing" name="tagCrossing" value="tagCrossing" <c:if test="${not empty tagCrossing}">checked</c:if> type="checkbox"> Искать пересечение тэгов
        </label>
        </form>
          <br>
        <form action="<c:url value="/Client/getXls" />" >
          <input type="hidden"  name="uid"  value="${uid}">
          <input type="hidden"  name="nameCompany"  value="${nameCompany}">
          <input type="hidden"  name="adress"  value="${adress}">
          <input type="hidden"  name="name"  value="${name}">
          <input type="hidden"  name="phone" value="${phone}">
          <input type="hidden"  name="tagCrossing" value="${tagCrossing}">
          <c:if test="${not empty selectedTagsMap}">
                <c:forEach var="tagId" items="${selectedTagsMap.keySet()}">
                    <input type="hidden" name="tags" value="${tagId}" >
                </c:forEach>
          </c:if>
          <input type="submit" name="submit" class="btn btn-primary" value="Скачать excel" >
        </form>

      </div>

      <table class="table table-bordered table-hover">
        <tr>
            <th>№</th>
          <th> Уникальный ИД </th>
          <th> Наименование </th>
          <th> Адрес </th>
          <th> Контактное лицо </th>
          <th> Телефон К.Л. </th>
          <th> Лицо принимающее решения </th>
          <th> Телефон Л.П.Р. </th>
          <th></th>
        </tr>
        <c:forEach var="client"  items="${clients}" varStatus="myIndex">
            <!--<tr style="cursor: pointer;" onclick="location = '<c:url value="/Client/oneClient?clientId=${client.clientId}"/>'">-->
            <tr>
                <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${myIndex.count}</a></td>
                <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.uniqueId}</a></td>
                <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.nameCompany}</a></td>
              <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.address}</a></td>
              <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.nameSecretary}</a></td>
              <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.getFormattedPhoneSec()}</a></td>
              <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.nameLpr}</a></td>
              <td><a class="arow" href="/Client/oneClient?clientId=${client.clientId}">${client.getFormattedPhoneLpr()}</a></td>
            <td><a href="#" id="${client.clientId}" class="btn btn-danger btn-xs deletinghref"
                               data-toggle="modal"
                               data-target="#deleteWindow">Удалить</a></td></tr>
            </c:forEach>
      </table>
    </div>
          <div class="modal fade" id="deleteWindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title" id="myModalLabel">Будут удалены все назначенные звонки и вся стат. информация о клиенте.Удалить?</h4>
                    </div>
                    <div class="modal-body">
                        <form id="deleteTagForm" action="<c:url value="/Client/delete"/>" method="post">
                                <input type="hidden" name="clientIdtoDelete" value="">
                                <input type="hidden"  name="uid"  value="${uid}">
                                <input type="hidden"  name="nameCompany"  value="${nameCompany}">
                                <input type="hidden"  name="adress"  value="${adress}">
                                <input type="hidden"  name="name"  value="${name}">
                                <input type="hidden"  name="phone" value="${phone}">
                                <input type="hidden"  name="tagCrossing" value="${tagCrossing}">
                                <c:if test="${not empty selectedTagsMap}">
                                    <c:forEach var="tagId" items="${selectedTagsMap.keySet()}">
                                        <input type="hidden" name="tags" value="${tagId}" >
                                    </c:forEach>
                              </c:if>
                            <p><input class="btn btn-danger" type="submit" value="Удалить">
                        </form>
                    </div>
                </div>
            </div>
        </div>
<script>$('.arow').hover(function(){$(this).closest('tr').find('.arow').toggleClass('underlined')})</script>
<script>$('.deletinghref').click(function(){var cuid = $(this).attr('id');$('[name = clientIdtoDelete]').val(cuid);});</script>
  </body>
</html>
