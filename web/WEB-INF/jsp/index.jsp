<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Call центр </title>
        <%@include file="/WEB-INF/jsp/css_js.jsp" %>
    </head>
    <body class="container">
        <%@include file="/WEB-INF/jsp/menutopadm.jsp" %>

<br>
<br><br><br><br><br><br><br><br><br>
        <div class="dropdown">
            <button class="btn btn-default" id="dropdownMenu" type="button" data-toggle="dropdown" aria-expanded="false">
                Меню <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                <li role="presentation"><a role="menuitem" href="#">Сепулька</a></li>
                <li role="presentation"><a role="menuitem" href="#">Сепулькарии</a></li>
                <li role="presentation"><a role="menuitem" href="#">Сепуление</a></li>
            </ul>
        </div>

        <div>
            <ul class="nav navbar-nav">
                <li><a href="#" class="dropdown-toggle">Home</a></li>
                <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >About <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Dr. Winthrop</a></li>
                        <li><a href="#">Dr. Chase</a></li>
                        <li><a href="#">Dr. Sanders</a></li>
                    </ul>                    
                </li>
                <li class="active"><a href="#">Services</a></li>
                <li><a href="#">Photo Gallery</a></li>
                <li><a href="#">Contact</a></li>  
            </ul> 
        </div>  


        <ul class="nav nav-pills">
            <li class="active"><a href="#">Ссылка</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Меню <b class="caret"></b></a>
                <ul id="menu1" class="dropdown-menu">
                    <li><a href="#">Действие</a></li>
                    <li><a href="#">Другое действие</a></li>
                    <li><a href="#">Ссылка</a></li>
                    <li class="divider"></li>
                    <li><a href="#">Отделенная ссылка</a></li>
                </ul>
            </li>
        </ul>


    </body>
</html>
