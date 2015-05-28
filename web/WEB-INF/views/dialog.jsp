<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
     <link rel="stylesheet" type="text/css" href=<c:url value='/css/dialog.css'/> >
</head>
<body>
    <c:set var="num" value="0"/>
    <c:forEach var="module" items="${dialog}">
        <c:set var="num" value="${num+1}"/>
        <div class="sms_bubble">${num} ${module.moduleName}</div>
        <div class="sms_bubble blue-right">${module.bodyText}</div>
    </c:forEach>
	<!--<div id="one" class="sms_bubble">
        Hey bro, what's up? Long time no see.
    </div>
    
    <div id="two" class="sms_bubble blue-right">
    Not much, man. Just writting some CSS3 SMS talk bubbles. You know, usual stuff. Wanna hang?
    </div>
    
    <div id="three" class="sms_bubble">
        Yeah!
    </div>
    
    <div id="four" class="sms_bubble blue-right">
    Where at?
    </div>
    
    <div id="five" class="sms_bubble">
    Let's go up to that one coffee shop off 1st street.
    </div>-->
</body>
