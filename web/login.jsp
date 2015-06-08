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
        <title>Диалог +||Построение телефонных продаж за 1 день</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link rel="shortcut icon" href="./img/icon.png">
    </head>
    <body >
<script src="<c:url value='/js/jquery-1.11.2.min.js'/>"> </script>
<script src="<c:url value='/js/bootstrap.js'/>"> </script>
        <div class="jumbotron" style="background-color: #5bc0de;">
            <div class="container">

                <nav class="navbar navbar-default navbar-fixed-top">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <a class="navbar-brand" href="#">
                                <img alt="Brand" src="./img/icon.png" width=22 style="float: left;"/>
                                <span style="float: left;margin-left: 10px;font-size: 25px;">Диалог+</span>

                            </a>
                        </div>
                        <p class="navbar-text navbar-right" style="margin-right: 10px;"><a href="#" data-toggle="modal" data-target="#entrance" class="navbar-link">Вход </a> 
                            или <a href="<c:url value="/Registration/registration" />" class="navbar-link">Регистрация </a> </p>
                    </div>
                </nav>


                <div class="page-header">
                    <h1>Диалог+ - это сервис для организации процесса телефонных продаж</h1>
                </div>

                <div >
                    <h3>Увеличить продажи в несколько раз. Обучить специалиста за 1 день.
                        Провести телемаркетинговую компанию по лояльности. Организовать удаленную работу.
                        Сделать прозрачным процесс продаж. Уберечь бизнес от утечки информации.
                        Все это здесь.</h3>
                </div>
                <h2><a href="<c:url value="/Registration/registration" />" class="label label-danger " style="float: right;">Демо</a></h2>
            </div>
        </div>
        <div class="container">
            <div >
                <div class="page-header"><h3> Как это работает</h3></div>
                <p>Вы описываете речевые модули которые вам необходимы. Оператор в процессе диалога с
                    клиентом получает подсказки  о том, что нужно говорить. При этом история диалогов фиксируется в 
                    отчетах.</p>
                <p>С программой <strong>Диалог +</strong> менеджеры работают по готовым речевым модулям. Во первых менеджеры  не
                    тратят время на выдумывание и заучивание текста. Во вторых снимается психологический барьер. 
                    Это помогает менеджерам работать с клиентами быстрее на 20%. Новый сотрудник полностью 
                    включиться в работу в течении 2 часов. Таким образом при расширении штата снижаются затраты на 
                    обучение.Кроме того Диалог + доступен повсюду, где есть интернет. Это позволяет нанять на удаленную работу
                    менее дорогостоящих сотрудников.</p>

                <p>В программе предусмотрено 2 роли: администратор и оператор. Администратору доступна  работа с
                    клиентской базой и отчетами.
                    Оператор в каждый момент времени работает только с одной карточкой клиента, то есть у него 
                    отсутствует возможность скопировать весь список клиентов. Так же оператор не может вернуться к 
                    клиентам, которых он обработал.  Это практически исключают утечку клиентской базы.</p>


            </div>
        </div>

        <div class="container" style="margin-top: 20px;">
            <div >
                <div class="row">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-7">
                        <iframe width="560" height="315" src="https://www.youtube.com/embed/SKhoVD7MhnU" frameborder="0" allowfullscreen></iframe>
                    </div>
                </div>
                <h1><a href="<c:url value="/Registration/registration" />" class="label label-warning">Попробовать</a></h1>

            </div>
        </div>    
        <div class="container" style="margin-top: 20px;">
            <div class="page-header"><h3>Плюсы куска</h3></div>
            <div>
                <div class="list-group">
                    <a class="list-group-item">
                        <p class="list-group-item-text">
                            <strong>Диалог +</strong> позволяет редактировать речевые модули "на лету", то
                            есть улучшать процесс телефонных коммуникаций можно 
                            прямо в момент проведения кампании </p>
                    </a>
                    <a class="list-group-item ">
                        <p class="list-group-item-text">
                            Благодаря <strong>куску</strong> процесс найма сотрудника стал легче. Плюс со стороны работодателя в том, что 
                            работать с программой может человек без специальной подготовки. Плюс соискателя в том, что 
                            кандидат может начать работать сразу, ему не нужно проходить тесты на профессиональную 
                            пригодность и заучивать большой объем информации. Так как в рабочей области оператора имеются 
                            подсказки, речевые модули.</p>
                    </a>
                    <a class="list-group-item ">
                        <p class="list-group-item-text">
                            Благодаря <strong>куску</strong> процесс найма сотрудника стал легче. Плюс со стороны работодателя в том, что 
                            работать с программой может человек без специальной подготовки. Плюс соискателя в том, что 
                            кандидат может начать работать сразу, ему не нужно проходить тесты на профессиональную 
                            пригодность и заучивать большой объем информации. Так как в рабочей области оператора имеются 
                            подсказки, речевые модули.</p>
                    </a>
                    <a class="list-group-item ">
                        <p class="list-group-item-text">
                            Новый сотрудник бытро вливается в коллектив. Начать работать проще за счет готовых речевых
                            модулей. Таким образом мы снимаем с оператора ответственность, так как он ведет диалог согласно 
                            речевым модулям. Это снимает психологический барьер, когда оператор разговаривает с клиентом.</p>
                    </a>
                    <a  class="list-group-item ">
                        <p class="list-group-item-text">
                            Все данные о клиентах доступны в вашем  личном кабинете. Только авторизованному пользователю
                            доступны рабочие области.</p>
                    </a>
                    <a class="list-group-item ">
                        <p class="list-group-item-text">
                            Наши сервера работают 24 часа в сутки, что обеспечивает постоянную работоспособность.  На 
                            случай чрезвычайной ситуации  мы создаем резервную копию, что позволяет восстановить рабочее 
                            состояние программы.</p>
                    </a>
                </div>
            </div>
            <h2><a href="<c:url value="/Registration/registration" />" class="label label-danger " style="float: right;">Демо-версия на 14 дней бесплатно</a></h2>
        </div>

        <div class="container" style="margin-top: 20px;">
            <div class="page-header"><h3>Стоимость</h3></div>
            <div class="row">
                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">

                        <div class="caption">
                            <h3 id="thumbnail-label">Пробная подписка<a class="anchorjs-link" href="#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 160px;">
                                <p>Подписка на 14 дней.</p>
                                <p>Количество пользователей до 3</p>
                                <p>Количество телемаркетинговых кампаний до 5</p>
                                <p>Количество клиентов до 500</p>

                            </div>
                            <p>Строимость: <strong>бесплатно</strong></p>
                            <p><a href="<c:url value="/Registration/registration" />" class="btn btn-warning" role="button">Получить Диалог +</a></p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3 id="thumbnail-label">Годовая подписка<a class="anchorjs-link" href="#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 160px;">
                                <p>Подписка на год.</p>
                                <p>Количество пользователей неограниченно</p>
                                <p>Количество телемаркетинговых кампаний неограниченно</p>
                                <p>Количество клиентов до неограниченно</p>

                            </div>
                            <p>Строимость: <strong>25 000 р.</strong></p>
                            <p><a href="<c:url value="/Registration/registration" />" class="btn btn-primary" role="button">Получить Диалог +</a></p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3 id="thumbnail-label">Диалог+ навсегда<a class="anchorjs-link" ><span class="anchorjs-icon"></span></a></h3>
                            <div style="height: 160px;">
                                <p>Установка на свой сервер</p>

                            </div>
                            <p>Строимость: <strong>150 000 р.</strong></p>
                            <p><a href="<c:url value="/Registration/registration" />" class="btn btn-danger" role="button">Получить Диалог +</a> </p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="container" style="margin-top: 20px;">
            <nav class="navbar navbar-default navbar-static-bottom" >
                <p class="navbar-text">&copy ООО Прим 2015 <a href="#" class="navbar-link">Контакты </a></p>
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
                                <input placeholder="Введите логин" class="form-control" name="j_username" id="login"  type="text">
                            </div>

                            <div class="form-group">
                                <label for="password">Пароль:</label>
                                <input placeholder="Введите пароль" class="form-control" name="j_password" id="password"  type="password">
                            </div>

                            <div class="form-group">
                                <button type="submit" class="login-button">Войти</button>
                            </div>

                            <p class="forgot-password"><a href="<c:url value="/User/recoveryPassword" />">Забыли пароль?</a>   
                                <a href="<c:url value="/Registration/registration" />">Регистрация!!</a></p>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
