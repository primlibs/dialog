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
        <meta http-equiv="Cache-Control" content="no-cache">
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="./css/dialog.css" rel="stylesheet" type="text/css" >
        <link rel="shortcut icon" href="./img/icon(D).png">
    </head>
    <body >
        <script src="<c:url value='/js/jquery-1.11.2.min.js'/>"></script>
        <script src="<c:url value='/js/bootstrap.js'/>"></script>
        <div class="jumbotron" style="background-color: #5bc0de;background-color: #5bc0de;background-image: url(./img/mmc.jpg);margin-bottom: 0px; background-position: 0% 25%;      background-size: cover;      background-repeat: no-repeat;      color: white; ">
            <div class="container">

                <nav class="navbar navbar-default navbar-fixed-top" style="background-color:#2f2f2f;opacity: 0.9;">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <a class="navbar-brand" style="padding:10px 15px;" href="#">
                                <!--<img alt="Brand" src="./img/icon.png" style="float: left;width: 150px;"/>-->
                                <span style="color:white;float: left;margin-left: 25px;font-size: 25px;margin-top:20px;">Диалог</span>

                            </a>

                        </div>
                        <p class="navbar-text navbar-right" style="margin-right: 10px;font-size:15px;color:#F0F0F0">
                            +7(342) <strong style="font-size:20px;color:white">259-56-57</strong><br/>
                            <a href="#" data-toggle="modal" data-target="#entrance" class="whiteLink">Вход </a> 
                            или <a href="#" data-toggle="modal" data-target="#registrationwindow" class="whiteLink">Регистрация </a> 
                        </p>


                    </div>

                </nav>


                <div class="page-header col-sm-8" style="margin-top:80px;text-shadow: black 0.1em 0.1em 0.1em;">
                    <h2>Диалог - это online сервис  для организации процесса телефонных переговоров с помощью речевых модулей</h2>
                </div>

                <div class="col-sm-8">
                    <h4>Системный подход к организаци продаж и обучению сотрудников
                    </h4>
                </div>
            </div>
        </div>
        <div class="container">
            <div>
                <div class="page-header"><h2> Что такое Диалог</h2></div>
                <h4><p>Представьте себе ваши ощущения, когда под вашим началом работают 10 успешных менеджеров.</p>
                    <p>Система организации ваших продавцов похожа на четко выверенный и хорошо смазанный механимзм. Только иногда вам приходится чуть корректировать его работу.</p>
                    <p>Вы уверены , что даже если кто то из сотрудников покинет компанию, обучение нового менеджера займет всего неделю. Вы ощущаете удовольствие от того, что все работает так, как надо. </p>
                    <p>Диалог - это система управления холодыми звонками, которая превращает эти ощущения в реальность. </p></h4>
            </div>
        </div>

        <div class="container" style="margin-top: 20px;">
            <div>
                <div class="row">
                    <div class="col-sm-3"></div>
                </div>
                <h1><a href="#" data-toggle="modal" data-target="#registrationwindow" class="label label-warning">Попробуйте Диалог </a></h1>

            </div>
        </div>    




        <div class="container" style="margin-top: 20px;">
            <div class="page-header"><h2>Премущества Диалога перед списком клиентов и распечатанными речевыми модулями</h2></div>
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
                                <p>Добавить рабочее место нового специалиста очень просто - достаточно трех щелчков мышью.
                                    Благодаря сети интернет, вы можете нанимать удаленных сторудников.</p>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <div class="container" style="margin-top: 20px;">
                <div class="page-header"><h2>Для кого предназначен сервис</h2></div>
                <div>
                    <h4>
                        <p>Диалог подойдет компаниям, совершающим много типовых телефонных контактов.</p> 
                        <p>Компании, имеющие в штате 2 и более  специалистов по телефонным переговорам, 
                            смогут по достоинству оценить преимущества Диалога</p>
                        <p>Сферы деятельности, где Диалог может быть востребован: автосалоны, автосервисы, стоматологические кабинеты, медицинские центры, банки,
                            туроператоры, салоны красоты, страховые компании, телекоммуникационные компании, коллекторские агентства</p>
                    </h4>
                </div>
                <h1><a href="#" data-toggle="modal" data-target="#registrationwindow" style="float: right;" class="label label-danger ">Демо-версия на 14 дней бесплатно </a></h1>
            </div>




            <div class="container" style="margin-top: 20px;background: url('/img/guaranteebg.png') repeat-x 0 0;color: white;">
                <div class="page-header"><h2>Наши гарантии</h2></div>
                <div style='border-bottom: 1px solid #eee;'>
                    <h3>
                        Если в течение 1 месяца с момента оплаты при использовании* нашего сервиса, 
                        Вы понимаете, что он вам не подходит, нам достаточно всего лишь заявления** от вас, 
                        чтобы вернуть вам <strong>80%</strong> от суммы оплаты.  
                    </h3>
                </div>
                <div>
                    <h5>
                        <p>* под использованием мы понимаем не менее 2 созданных кампаний по обзвону и не менее
                            500 совершенных Вашими специалистами звонков с момента оплаты и до подачи заявления</p>
                        <p>** заявление подается в свободной форме за подписью директора компании и с печатью компании</p>
                        <p>*** при подачи заявления стороны расторгают ранее заключенный договор, подписывая соответсвующие акты.
                            При этом  оказание услуг по договору прекращается с момента подачи заявления</p>
                    </h5>
                </div>
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
                                <p><a href="#" class="btn btn-default btn-lg" data-toggle="modal" data-target="#registrationwindow" style="background-color: #ccc;" role="button">Получить Диалог</a></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="thumbnail" style="background-color: aliceblue;font-size: 16px;">
                            <div class="caption">
                                <h3 id="thumbnail-label" style="height: 50px;">Тариф базовый<a class="anchorjs-link" href="/login.jsp;jsessionid=701118AFAFCA471B4FBDD4EDCB983A29#thumbnail-label"><span class="anchorjs-icon"></span></a></h3>
                                <div style="height: 200px;">
                                    <p>Подписка на 12 месяцев</p>
                                    <p>Количество пользователей <b>до 10</b></p>
                                    <p>Количество телемаркетинговых кампаний <b>не ограничено</b></p>
                                    <p>Количество клиентов <b>не ограничено</b></p>

                                </div>
                                <p>Строимость: <strong>25 000 р.</strong></p>
                                <p><a href="#" class="btn btn-success btn-lg" data-toggle="modal" data-target="#registrationwindow" role="button">Получить Диалог</a></p>
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
                                    <p>Количество телемаркетинговых кампаний <b>не ограничено</b></p>
                                    <p>Количество клиентов <b>не ограничено</b></p>

                                </div>
                                <p>Строимость: <strong>70 000 р.</strong></p>
                                <p><a href="#" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#registrationwindow" role="button">Получить Диалог</a></p>
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
                                    <p>Количество телемаркетинговых кампаний <b>не ограничено</b></p>
                                    <p>Количество клиентов <b>не ограничено</b></p>

                                </div>
                                <p>Строимость: <strong>120 000 р.</strong></p>
                                <p><a href="#" data-toggle="modal" data-target="#registrationwindow" class="btn btn-warning btn-lg" role="button">Получить Диалог</a></p>
                            </div>
                        </div>
                    </div>

                </div>



                <div class="container" style="margin-top: 20px;">
                    <div class="page-header"><h1><a href="#" id="sale" class="label label-danger">Хотите скидку?</a></h1></div>
                    <div class="saletoggle collapse col-lg-10" style="border-bottom: 1px solid #eee;">
                        <ul>
                            <li><h3>Упомяните нас на http://vk.com и получите скидку в 5% от стоимости вашего договора</h3></li>
                            <li><h3>Упомяните нас на https://www.facebook.com и получите скидку в 5% от стоимости вашего договора</h3></li>
                            <li><h3>Приведите нам еще одного клиента и в случае заключения договора с ним получите подарок в размере 10% от стоимости его договора</h3></li>
                            <li><h3>Все скидки и подарки суммируются</h3></li>
                        </ul>
                    </div>
                    <div class="saletoggle collapse col-lg-10" style=""><h5>
                            *под упоминанием мы подразумеваем не лестный отзыв о нас, 
                            поскольку мы еще не работали с Вами, а просто указание нашего сервиса с адресом http://dialogpl.com/ как
                            новые возможности, которые Вы решили использовать для Вашего бизнеса</h5>

                    </div>
                </div>           






            </div>
            <div class="container" style="margin-top: 20px;">
                <nav class="navbar navbar-default navbar-static-bottom">
                    <p class="col-lg-4 txt text-center navbar-text">© ООО Прим 2015</p>
                    <p class="col-lg-3 txt text-center navbar-text"><a href="/confidence.jsp">Политика конфиденциальности</a></p>
                    <p class="col-lg-4 txt text-center navbar-text">+7(342)259-56-57</p>
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
                            <c:if test="${param.error=='true'}">
                                <center>
                                    <div class="alert alert-success" >
                                        <p>Введенные логин или пароль неверны.</p>
                                    </div>  
                                </center>
                                <c:set var="autherror" value="<script>
                                       $(window).load(function(){
                                       $('#entrance').modal('show');
                                       });
                                       </script>"/>

                            </c:if>
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
                                    <a href="#" data-toggle="modal" data-target="#registrationwindow">Регистрация!!</a></p>
                            </form>

                        </div>
                    </div>
                </div>
            </div>


            <c:set var="inedreg" value=""/>

            <c:if test="${not empty param.regerrors}"><c:set var="inedreg" value="<script>
                                                             $(window).load(function(){
                                                             $('#registrationwindow').modal('show');
                                                             });
                                                             </script>"/>
            </c:if>
            <div class="modal fade" id="registrationwindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                            <h4 class="modal-title" id="myModalLabel">Регистрация</h4>
                        </div>


                        <div class="modal-body">

                            <c:if test="${not empty param.regerrors}">
                                <center>
                                    <div class="alert alert-danger" >
                                        <c:forEach items="${param.regerrors}" var="error" >
                                            <p>${error}</p>
                                        </c:forEach>
                                    </div>  
                                </center>
                            </c:if>

                            <form method="post" action="Registration/registration" class="login">
                                <div class="form-group">
                                    <label for="regemail">email</label>
                                    <input placeholder="Введите email" class="form-control" name="regemail" id="login" type="email">
                                </div>

                                <div class="form-group">
                                    <label for="regphone">Телефон</label>
                                    <input placeholder="Введите телефон" class="form-control" name="regphone" id="password" type="phone">
                                </div>

                                <div class="form-group">
                                    <label for="regpassword">Пароль:</label>
                                    <input placeholder="Введите пароль" class="form-control" name="regpassword" id="password" type="password">
                                </div>

                                <div class="form-group">
                                    <label for="regconfirmPassword">Подтверждение пароля:</label>
                                    <input placeholder="Подтвердите пароль" class="form-control" name="regconfirmPassword" id="confirmPassword" type="password">
                                </div>

                                <div class="form-group">
                                    <button type="submit" class="login-button">Регистрация</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="infowindow" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                            <h4 class="modal-title" id="myModalLabel">Введите регистрационную информацию</h4>
                        </div>


                        <div class="modal-body">

                            <c:if test="${not empty param.serrors}">
                                <center>
                                    <div class="alert alert-success" >
                                        <c:forEach items="${param.serrors}" var="error" >
                                            <p>${error}</p>
                                        </c:forEach>
                                    </div>  
                                </center>
                            </c:if>

                            <form method="post" action="Registration/saveContacts" class="login">
                                <div class="form-group">
                                    <label for="clemail">email</label>
                                    <input placeholder="Введите email" class="form-control" name="clemail" id="clemail" type="email" value="${param.clemail}">
                                </div>

                                <div class="form-group">
                                    <label for="clphone">Телефон</label>
                                    <input placeholder="Введите телефон" class="form-control" name="clphone" id="clphone" type="phone" value="${param.clphone}">
                                </div>

                                <div class="form-group">
                                    <label for="clname">Как к Вам обращаться:</label>
                                    <input placeholder="Введите Ваше имя" class="form-control" name="clname" id="clname" type="text" value="${param.clname}">
                                </div>

                                <div class="form-group">
                                    <button type="submit" class="login-button">Отправить</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
            ${inedreg}
            ${autherror}
            <c:if test="${not empty param.serrors}"><script>
                $(window).load(function () {
                    $('#infowindow').modal('show');
                });
                </script>
            </c:if>

            <script type="text/javascript">
                $('#sale').on('click', function (e) {
                    e.preventDefault();
                    $('.saletoggle').collapse('toggle');
                    return false;
                });
            </script>


            <!-- Yandex.Metrika counter -->
            <script type="text/javascript">
                (function (d, w, c) {
                    (w[c] = w[c] || []).push(function () {
                        try {
                            w.yaCounter31478693 = new Ya.Metrika({
                                id: 31478693,
                                clickmap: true,
                                trackLinks: true,
                                accurateTrackBounce: true
                            });
                        } catch (e) {
                        }
                    });

                    var n = d.getElementsByTagName("script")[0],
                            s = d.createElement("script"),
                            f = function () {
                                n.parentNode.insertBefore(s, n);
                            };
                    s.type = "text/javascript";
                    s.async = true;
                    s.src = "https://mc.yandex.ru/metrika/watch.js";

                    if (w.opera == "[object Opera]") {
                        d.addEventListener("DOMContentLoaded", f, false);
                    } else {
                        f();
                    }
                })(document, window, "yandex_metrika_callbacks");
            </script>
            <noscript><div><img src="https://mc.yandex.ru/watch/31478693" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
            <!-- /Yandex.Metrika counter -->
    </body></html>
