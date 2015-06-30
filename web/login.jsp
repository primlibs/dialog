<%-- 
    Document   : login
    Created on : 16.03.2015, 19:17:08
    Author     : Юрий
--%>
<%@ page session="true" import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Диалог||Построение телефонных продаж за 1 день</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="./css/dialog.css" rel="stylesheet" type="text/css" >
        <link rel="shortcut icon" href="./img/icon(D).png">
    </head>
    <body >
<script src="<c:url value='/js/jquery-1.11.2.min.js'/>"> </script>
<script src="<c:url value='/js/bootstrap.js'/>"> </script>
        <div class="jumbotron" style="background-color: #5bc0de;">
            <div class="container">

                <nav class="navbar navbar-default navbar-fixed-top">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <a class="navbar-brand" style="padding:10px 15px;" href="#">
                                <img alt="Brand" src="./img/icon.png" style="float: left;width: 150px;"/>
                                <!--<span style="float: left;margin-left: 10px;font-size: 25px;">Диалог</span>-->

                            </a>
                            
                        </div>
                        <p class="navbar-text navbar-right" style="margin-right: 10px;margin-top: 20px;">
						+7(342) <strong>259-56-57</strong><br/>
						<a href="#" data-toggle="modal" data-target="#entrance" class="navbar-link">Вход </a> 
                            или <a href="/Registration/registration" class="navbar-link">Регистрация </a> 
							</p>
							
                        
                    </div>
                    
                </nav>


                <div class="page-header" style="margin-top:80px;">
                    <h1>Диалог - это сервис для организации процесса телефонных переговоров с помощью речевых модулей</h1>
                </div>

                <div>
                    <h3>Увеличить продажи. 
                        Провести телемаркетинговую компанию по лояльности. Организовать удаленную работу.
                        Сделать прозрачным процесс продаж. Уберечь бизнес от утечки информации.
                        </h3>
                </div>
            </div>
        </div>
        <div class="container">
            <div>
                <div class="page-header"><h2> Что такое Диалог</h2></div>
                <h3><p>Онлайн-сервис, представляет из себя личный кабинет, в котором вы создаете сценарии разговора.</p>
                <p>Ваши сотрудники общаются по заданным сценариям с вашими клиентами, получая на экране монитора подсказки.</p>
                <p>Вы получаете отчетность по эффективности работы и качеству ваших сценариев </p></h3>
            </div>
        </div>

        <div class="container" style="margin-top: 20px;">
            <div>
                <div class="row">
                    <div class="col-sm-3"></div>
                    <!--<div class="col-sm-7">
                        <iframe width="560" height="315" src="https://www.youtube.com/embed/G0eIbWXek-Q" frameborder="0" allowfullscreen></iframe>
                    </div>-->
                </div>
                <h1><a href="/Registration/registration" class="label label-warning">Протестировать</a></h1>

            </div>
        </div>    
       

		
		
		<div class="container" style="margin-top: 20px;">
            <div class="page-header"><h2>Премущества Длалога перед списком клиентов и распечатанными речевыми модулями</h2></div>
            <div class="row">
                <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">

                        <div class="caption">
			
							<img src="/img/121.jpg"  height="180px" width="240px;" class="img-rounded"/>
							<h4 id="thumbnail-label" style="height: 50px;">Безопасность</h3>
                            <div style="height: 200px;">
								<p>Сотрудники, которые звонят, не видят весь список клиентов одновременно и не имеют возможности скачать его.
								В каждый момент времени звонящий работает только с одним клиентом.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">

                        <div class="caption">

							<img src="/img/122.jpg" height="180px"  width="240px;" class="img-rounded"/>

						   <h4 id="thumbnail-label" style="height: 50px;">Гибкость</h4>
                            <div style="height: 200px;">
								<p>Вы получаете возможность редактировать сценарии прямо в процессе проведения обзвона клиентов. Получаете гибкую самообучающуюся систему.</p>
                            </div>
                        </div>
                    </div>
                </div>
				<div class="col-sm-6 col-md-3">
                    <div class="thumbnail">

                        <div class="caption">
			
							<img src="/img/123.jpg" height="180px"  width="240px;" class="img-rounded"/>
   
						<h4 id="thumbnail-label" style="height: 50px;">Статистика</h4>
                            <div style="height: 200px;">
								<p>Вы имеете доступ к статистике звонков, отказов, удачных и неудачных сценариев как по текущим обзвонам, так и за прошлые периоды.</p>
                            </div>
                        </div>
                    </div>
                </div>
				<div class="col-sm-6 col-md-3">
                    <div class="thumbnail">

                        <div class="caption">

							<img src="/img/124.jpg" height="180px"  width="240px;" class="img-rounded"/>
        
							<h4 id="thumbnail-label" style="height: 50px;">Масштабируемость и удаленный доступ
							</h4>
                            <div style="height: 200px;">
								<p>Добавить рабочее место нового специалиста очень просто - достаточно 3 щелчков мышью.
								Благодаря  интернет, вы можете нанимать удаленных сторудников.</p>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
			
			
		 <div class="container" style="margin-top: 20px;">
            <div class="page-header"><h2>Для кого предназначен сервис</h2></div>
            <div>
				<h3>
				<p>Диалог подойдет компаниям, совершающим много типовых телефонных контактов.</p> 
				<p>Компании, имеющие в штате более 2 специалистов по телефонным переговорам, 
				смогут по достоинству оценить преимущества Диалога</p>
					<p>Сферы деятельности, где Диалог может быть востребован: автосалоны, автосервисы, стоматологические кабинеты, медицинские центры, банки,
					туроператоры, салоны красоты, страховые компании, телекоммуникационные компании, коллекторские агентства</p>
				</h3>
			</div>
            <h1><a href="/Registration/registration" class="label label-danger " style="float: right;">Демо-версия на 14 дней бесплатно</a></h1>
        </div>
		
        <div class="container" style="margin-top: 20px;">
            <div class="page-header"><h2>Стоимость</h2></div>
            <div class="row">
                <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">

                        <div class="caption">
                            <h3 id="thumbnail-label" style="height: 50px;">Пробная подписка<a class="anchorjs-link" href="/login.jsp;jsessionid=701118AFAFCA471B4FBDD4EDCB983A29#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 200px;">
                                <p>Подписка на 14 дней.</p>
                                <p>Количество пользователей <b>до 3</b></p>
                                <p>Количество телемаркетинговых кампаний до 5</p>
                                <p>Количество клиентов до 500</p>

                            </div>
                            <p>Строимость: <strong>бесплатно</strong></p>
                            <p><a href="/Registration/registration" class="btn btn-default btn-lg" id="dialogfreebtn" style="background-color: #ccc;" role="button">Получить Диалог</a></p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3 id="thumbnail-label" style="height: 50px;">Тариф базовый<a class="anchorjs-link" href="/login.jsp;jsessionid=701118AFAFCA471B4FBDD4EDCB983A29#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 200px;">
                                <p>Подписка на 12 месяцев</p>
                                <p>Количество пользователей <b>до 10</b></p>
                                <p>Количество телемаркетинговых кампаний неограниченно</p>
                                <p>Количество клиентов неограниченно</p>

                            </div>
                            <p>Строимость: <strong>25 000 р.</strong></p>
                            <p><a href="/Registration/registration" class="btn btn-success btn-lg" role="button">Получить Диалог</a></p>
                        </div>
                    </div>
                </div>
                        
                        <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3 id="thumbnail-label" style="height: 50px;">Тариф продвинутый<a class="anchorjs-link" href="/login.jsp;jsessionid=701118AFAFCA471B4FBDD4EDCB983A29#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 200px;">
                                <p>Подписка на 12 месяцев</p>
                                <p>Количество пользователей <b>до 30</b></p>
                                <p>Количество телемаркетинговых кампаний неограниченно</p>
                                <p>Количество клиентов неограниченно</p>

                            </div>
                            <p>Строимость: <strong>70 000 р.</strong></p>
                            <p><a href="/Registration/registration" class="btn btn-primary btn-lg" role="button">Получить Диалог</a></p>
                        </div>
                    </div>
                </div>
                        
                        <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3 id="thumbnail-label" style="height: 50px;">Тариф максимальный<a class="anchorjs-link" href="/login.jsp;jsessionid=701118AFAFCA471B4FBDD4EDCB983A29#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 200px;">
                                <p>Подписка на 12 месяцев</p>
                                <p>Количество пользователей <b>не ограничено</b></p>
                                <p>Количество телемаркетинговых кампаний неограниченно</p>
                                <p>Количество клиентов неограниченно</p>

                            </div>
                            <p>Строимость: <strong>120 000 р.</strong></p>
                            <p><a href="/Registration/registration" class="btn btn-warning btn-lg" role="button">Получить Диалог</a></p>
                        </div>
                    </div>
                </div>
                
            </div>
                        
                        <!--<div class="">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3 id="thumbnail-label" style="text-align: center;">Диалог навсегда<a class="anchorjs-link" ><span class="anchorjs-icon"></span></a></h3>
                                <p  style="text-align: center;">Установка на свой сервер</p>
                            <p  style="text-align: center;">Строимость: <strong>150 000 р.</strong></p>
                            <p><a href="/Registration/registration" class="btn btn-danger btn-block" role="button">Получить Диалог</a> </p>
                        </div>
                    </div>
                </div>-->

        </div>
        <div class="container" style="margin-top: 20px;">
            <nav class="navbar navbar-default navbar-static-bottom">
                <p class="navbar-text">© ООО Прим 2015 +7(342)259-56-57<!--<a href="#" class="navbar-link">Контакты </a>--></p>
            </nav>
        </div>

        <div class="modal fade" id="entrance" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title" id="myModalLabel">Авторизация</h4>
                    </div>
                    <div class="modal-body">
                        <form method="post" action="j_spring_security_check" class="login">
                            <div class="form-group">
                                <label for="login">Логин:</label>
                                <input placeholder="Введите логин" class="form-control" name="j_username" id="login" type="text">
                            </div>

                            <div class="form-group">
                                <label for="password">Пароль:</label>
                                <input placeholder="Введите пароль" class="form-control" name="j_password" id="password" type="password">
                            </div>

                            <div class="form-group">
                                <button type="submit" class="login-button">Войти</button>
                            </div>

                            <p class="forgot-password"><a href="/User/recoveryPassword">Забыли пароль?</a>   
                                <a href="/Registration/registration">Регистрация!!</a></p>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    

</body></html>
