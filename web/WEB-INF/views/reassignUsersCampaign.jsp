<%-- 
Document   : eventAppointAll
Created on : 12.05.2015, 14:30:44
Author     : Юрий 
--%>
<%--        <c:if test="${eventAllAppoint!=null && !eventAllAppoint.isEmpty()}">    --%>
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
        <script type="text/javascript">
            function clearForm() {
                $(".invalue").each(function(){
                   $(this).val(0); 
                });
            }
        </script>
        <div class="row">
            <h4>  <b>Кампания: </b> ${campaign.name}; &nbsp<b> Сценарий: </b>${campaign.strategy.strategyName}; <b>Нераспределенных клиентов:</b> ${clientCount}.
            <input class="btn btn-primary" type="submit" value="Очистить" onclick="clearForm();"/> </h4>

            <form  action="<c:url value="/Event/changeAllUsersCampaignAssignation" />" method="post">
                <table class="table table-bordered table-hover">
                    <c:forEach var="cabinetUser"  items="${cabinetUserList}" varStatus="myIndex">
                        <tr>
                            <td >${cabinetUser.user.surname} &nbsp ${cabinetUser.user.name} </td>
                            <td> 
                                <input type="text"  class="invalue" name="clientNum"  value="${eventAllAppoint.get(cabinetUser.getUser().getUserId())}" placeholder="Количество клиентов">
                                <input type="hidden" name="userId" value=${cabinetUser.user.userId}>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="hidden" name="userFromId" value=${param['userFromId']}>
                <input type="hidden" name="usSubmit" value="submit">
                <input type="hidden" name="campaignId" value=${param['campaignId']}>
                <input class="btn btn-primary" type="submit" value="Отправить">
            </form>
        </div>
        
    </body>
</html>
