<%-- 
    Document   : menuright
    Created on : 31.03.2015, 10:57:14
    Author     : Юрий
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<ul class="nav navbar-nav  pull-right" style="margin-bottom: 0px">
    
    

    <li style="top: 15px;"> Организация: <span class="changebleParam" data-method="changeOrgName" data-controller="/User" name="newName">${nameCompany }</span> &nbsp </li>    


    <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" > ${nameUser } <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li style="color: #00A1D2"> <center>${role }</center></li>
            <li style="color: #00A1D2"> <a href="<c:url value="/User/changePassword"/>">Сменить пароль</a></li>
            <li style="color: blue">  <a href="<c:url value="/logout"/>"> <img width="20px" src="<c:url value="/img/exit.png"/>">Выйти</a>  </li>
        </ul>                    
    </li>
</ul>
