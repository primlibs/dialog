<%-- 
    Document   : menutopadm
    Created on : 26.03.2015, 10:20:45
    Author     : Юрий
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>




<div class="navbar" style="margin-bottom: 0px;border-bottom: solid black 1px;">
    <ul class="nav navbar-nav">
        <li><a href="<c:url value="/Event/campaignList"/>">Кампании</a></li>
        <!--<li><a href="<c:url value="/UserReport/summarizedReport"/>">Отчет по операторам</a></li>-->
        <li><a href="<c:url value="/Client/clientList"/>">Клиенты</a></li>
        <!--<li><a href="<c:url value="/Event/campaign"/>">Мои звонки</a></li>  -->
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Мои звонки<span class="caret"></span></a>
        <ul class="dropdown-menu">
                <li><a href="<c:url value="/Event/campaign"/>">Текущие</a></li>
                <li><a href="<c:url value="/Event/postponedEvents"/>">Перенесенные</a></li>
            </ul></li>   
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Входящие <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="/Event/inCampaign"/>">Звонки</a></li>
                <li><a href="<c:url value="/Event/inCallReport"/>">Отчетность</a></li>
            </ul>                    
        </li>
        
        <li class="dropdown"><a href="#" data-toggle="dropdown"  class="dropdown-toggle" >Настройки <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="/User/userList"/>">Пользователи</a></li>
                <li><a href="<c:url value="/Strategy/show"/>">Сценарии</a></li>
                <li><a href="<c:url value="/Tag/show"/>">Тэги</a></li>
            </ul>                    
        </li>
        
        <c:if test="${superadmin eq 'superadmin'}">
            <li class="dropdown">  <a href="#" data-toggle="dropdown"  class="dropdown-toggle">Управление <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="/Tarif/list"/>">Тарифы</a></li>
                <li><a href="<c:url value="/Lk/cabinets"/>">Кабинеты</a></li>
            </ul></li>
        </c:if>
        <c:if test="${superadmin eq 'superadmin'}">
            <li><a href="<c:url value="/Task/taskList"/>">Задачи</a></li>
        </c:if>    
        <li><a href="#" class="btn btn-default learn" style="border:0px solid transparent"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></a></li>
    </ul> 
    <%@include file="/WEB-INF/jsp/menuright.jsp" %>
    
    
    
    
    
    
    
    
    
    <!-- HTML код кнопки (для отображения модального окна) -->

    
<!-- HTML код диалогового окна-->
<div id="learning" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">Обучение Диалог шаг <strong id="stephead">0</strong> из 8 </h4>
      </div>
      <div class="modal-body content" style="min-height:400px;" id="step" pos="0">
        <h4>Добро пожаловать в интерактивное обучение Диалог</h4> Просто следуйте подсказкам, прогресс обучения будет сохранен, Вы сможете вернуться к обучению в любой момент. Для продолжения, нажмите кнопку <strong class="text-primary">">"</strong></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default first">Начало</button>
		<button type="button" class="btn btn-default laststep"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span></button>
		<button type="button" class="btn btn-default nextstep" ><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">Завершить</button>
      </div>
    </div>
  </div>
</div>

	<div id="step0" class="hide"><h4>Добро пожаловать в интерактивное обучение Диалог</h4> Просто следуйте подсказкам, прогресс обучения будет сохранен, Вы сможете вернуться к обучению в любой момент. Для продолжения, нажмите кнопку <strong class="text-primary">">"</strong></div>
	<div id="step1" class="hide"> <h4>Добавить пользователя</h4>
			<p>В меню щелкнем по пункту <strong>Настройки</strong> -> <strong><a href="#">Пользователи</a></strong></p>
			<p>В таблице мы видим поле роль оно может быть <strong>Администратор/Пользователь</strong>. И поле Участие в кампаниях оно имеет значение <strong>да/нет</strong>.</p>
			<p>Для добавления пользователя нажмите <strong><a href="#">Добавить</a></strong>.</p>
			<p><strong class="text-success">Администратор:</strong> По умолчанию администратор не участвует в кампаниях, поскольку это основная функция пользователя, но программой предусмотрена возможность участие администратора. Участие в кампаниях для администратора можно изменить: <strong>да/нет</strong> двойным щелчком мыши.</p>
			<p>Администратору доступны функции: добавление пользователя, создание кампаний, загрузки и просмотра клиентов, выгрузки отчетов в формате excel, смена пароля.</p>
			<p><strong class="text-success">Пользователь:</strong> это оператор, его основная функция звонки по клиентам. Оператору доступны функции : обзвона по киентам, смена пароля.</p>
	</div>
	
	
	<div id="step2" class="hide"><h4>Создать сценарий</h4>
	<p>Сценарий -это набор речевых модулей для одного разговора, он может включать в себя преодоление секретарского барьера, разговор с лицом, принимающим решения или ответ на телефонный звонок.</p>
	<p>В меню щелкнем по пункту <strong>Настройки</strong> -> <strong><a href="#">Сценарии</a></strong></p>
	<p>Для добавления сценария нажмите <strong><a href="#">Добавить</a></strong>.</p>
	<ol><p>Выберите название сценария и тип. <strong class="text-success">Входящий</strong> - 
	для входящих звонков(к примеру администратору на ресепшн). 
	<strong class="text-success">Исходящий</strong> - для исходящих</p></ol>
	<ol><p>После того, как сценарий добавлен, шелкните двойным щелчком мыши на его названии в таблице. 
		Вы попадете в меню редактирования сценария</p></ol>
	<p>Создайте группу, вписав название и щелкнув по кнопке в правой части экрана</p>
	<p>Чуть ниже название группы появится кнопка <strong class="text-success">Добавить модуль</strong>. Впишите название модуля и нажмите на нее</p>
	<p>После того, как модуль появится, над таблицей в левой части экрана должно появиться его название. Это реплика клиента. В текстовом поле введите речевой модуль оператора и нажмите сохранить</p>
	<p><strong>Вы создали свой первый модуль</strong></p>
	<p class="small">Текстовое поле поддерживает форматирование это удобно для создания подсказок операторам 
	(к примеру обычным текстом можно писать основной текст,
		курсивом - особенности, жирным - важные моменты, 
		также возможно выделение цветом или просто вставка таблицы из эксель)</p>
	</div>
	
	
	<div id="step3" class="hide"><h4>Создать кампанию</h4>
	<p class="text-danger"><strong>Если в вашем бизнесе вы не планируете исходящие звонки, 
	пропустите этот пункт</strong></p>
	<p><strong class="text-success">Кампания</strong>, - это набор клиентов, которых вы хотите
	 обзвонить по определенному сценарию разговора. Это может быть часть вашей клиенской базы или вся база. Одновременно может проводится несколько кампаний, к примеру продажа основного продукта, возврат клментов, информирование об акциях. 
	 Также, один и тот же клиент может быть одновременно задействован в нескольких кампаниях</p>
	<p>В меню щелкнем по пункту <strong>Кампании</strong> -> <strong><a href="#">Создать кампанию</a></strong></p>
	<p>Выберите сценарий разговора, заполните поле названия кампании, или оставьте порядковый номер</p>
	<p>Нажмите кнопку <strong class="text-success">Исходящий</strong></p>
	<p>Кампания создана</p>
	</div>
	
	
	<div id="step4" class="hide"><h4>Добавить клиентов</h4>
	<p class="text-danger"><strong>Если в вашем бизнесе вы не планируете исходящие звонки, 
	пропустите этот пункт</strong></p>
	<p>В меню щелкнем по пункту <a href="#">Кампании</a>.</p>
	<p>В таблице по двойному клику мыши перейдем в нужную нам кампанию.</p>
	<p>Нажимаем на кнопку <strong class="text-success">Получить форму excel</strong>, сохраняем.
	Далее заполняем файл Excel, который вы сохранили.</p>
		<p>Обязательными полями являются <strong>номер уникальный</strong>, <strong>Клиент</strong> и <strong>телефон</strong>. 
		Важно сохранить последовательность столбцов.Поле <strong>комментарий </strong> может быть заполнено любой справочной информацией</p>
		<p>Поле <strong>номер уникальный</strong> - это обязательное поле для загрузки клиентов в программу, 
		оно необходимо для того, чтобы не повторялись клиенты,
		когда вы будете использовать тот же список клиентов в другой кампании, 
		и для удобной работы с отчетами при выгрузке клиентов в excel или сторонние программы.</p>
		<p>Далее нажимаем на кнопку <strong class="text-success">Выберите файл(Обзор)</strong>, 
			выбираем наш файл Видим как появились 
			клиенты в поле таблицы <strong class="text-success">Не назначено.</strong></p>
	
	</div>
	<div id="step5" class="hide"><h4>Распределить клиентов</h4>
	<p class="text-danger"><strong>Если в вашем бизнесе вы не планируете исходящие звонки, 
	пропустите этот пункт</strong></p>
	<p>В меню щелкнем по пункту <a href="#">Кампании</a>.</p>
	<p>В таблице по двойному клику мыши перейдем в нужную нам кампанию.</p>
	<p>Нажмем <strong class="text-success">Распределить клиентов</strong> Попадаем в меню распределения, 
	ставим нужное значение на пользователей и нажимаем <strong class="text-success">отправить</strong>.</p> 
	<p>Видим как в таблице распределились звонки на пользователей.</p>
	<p>Теперь пользователи, и администраторы с возможностью совершать 
		звонки, получат возможность фиксировать и получать подсказки в области <strong class="text-success">Мои звонки</strong>. 
		Результаты можно посмотреть либо в детализации кампании в меню Кампании,
		либо в каждом конкретном клиенте, в области <strong><a href="#">Клиенты</a></strong>.
		Также в карточке клиента можно посмотреть историю диалога с клиентом.</p>
	</div>
	
	
	<div id="step6" class="hide"><h4>Отчетность и выгрузка в Excel</h4>
		<p>Отчетность по исходящим кампаниям представлена непосредственно в меню кампании и состоит из 4 разделов</p>
		<ol><p><strong class="text-success">Таблица кампании</strong> - сводные данные о количестве клиентов кампании, распределении,
			завершенном обзвоне, и назначенной нагрузке на менеджеров</p></ol>
		<ol><p><strong class="text-success">Отчет по модулям</strong> показывает в процентном соотношении те модули,
			после которых последовал отказ клиента.</p></ol>
		<ol><p><strong class="text-success">Отчет по работе</strong> показывает активность менеджеров по обзвону</p></ol>
		<ol><p><strong class="text-success">Отчет по причинам отказов</strong> показывает причины, зафиксированнные менеджерами, как отказы</p></ol>
		<p>Все цифры в отчетах по двойному щелчку мыши ведут в детализацию до клиентов.</p>
		<p>Любую детализацию администратор имеет возможность скачать в форме excel</p>
		<p>Отчетность по входящим звонкам находится в разделе  <strong>Входящие</strong> -> <strong><a href="#">Отчетность</a></strong></p>
		<p>Во входящей отчетности фиксируется время и вопросы, по которым звонили клиенты</p>
	</div>
	
	
	
	
	<div id="step7" class="hide"><h4>Памятка оператора</h4>
		<p><strong><a href="#">Мои звонки</a></strong> - это область в которой отображается таблица со 
		списком кампаний и сценариев, двойным кликом попадаем в конкретную кампанию.</p>
		<p>В рабочей области видим карточку клиента: название, адрес, контактное лицо
		компании и телефоны. Под карточкой клиента слева видим список модулей и 
		кнопки обработки клиента.</p>
		Под карточкой клиента справа выводятся тела речевых модулей подсказки</p>
		<p><strong><a href="#">Перенесенные</a></strong>  – список назначенных на конкретное время звонков</p>
		<p><strong><a href="#">Входящие</a></strong>  – область для получения подсказок по входящим звонкам.</p>
	</div>
	<div id="step8" class="hide"><h4>Карточка входящего звонка</h4>
		<p><strong class="text-success">Перенести</strong> - кнопка позволяющая перенести клиента на другую дату и
		время с указанием комментария,
		при этом клиент не будет показан до наступления назначенной даты,
		так же если дата просрочена то клиент будет выведен в первую очередь.</p>
		<p><strong class="text-success">Положительный исход</strong> - кнопка для положительного результата, 
		когда цель достигнута, при этом записывается дата и комментарий.<p>
		<p><strong class="text-success">Отрицательный исход</strong> - кнопка для отказ клиента от предложения,
		при этом из словаря отказов выбирается причина отказа.</p>
		<p><strong class="text-success">Следующий клиент</strong> - кнопка переключает 
		на карточку следующего клиента.К примеру если нет возможности дозвониться</p>
		<p>Реплики в правом меню - это слова клиента, переключаясь между фразами клиента,
		справа под карточкой клиента мы видим реплику клиента,
		а под ней текст оператора на данную реплику.</p>
		<p>Для входящих звонков не предусмотрены формы фиксации и
		переноса клиентов используются только подсказки</p>
	</div>
	
	<script type="text/javascript">
$(document).ready(function(){
  $(".learn").click(function(){
    $("#learning").modal('show');

  });
});
</script>
<script type="text/javascript">
$(document).ready(function(){
  $(".nextstep").click(function(){
    var pos=$("#step").attr('pos');
	if(pos<8){
		pos=pos*1+1;
		var content=$("#step"+pos).html();
		$(".content").html(content);
		$("#step").attr('pos',pos);
		$("#stephead").html(pos);
	}
  });
});
</script>
<script type="text/javascript">
$(document).ready(function(){
  $(".laststep").click(function(){
    var pos=$("#step").attr('pos');
	if(pos>0){
		pos=pos*1-1;
		var content=$("#step"+pos).html();
		$(".content").html(content);
		$("#step").attr('pos',pos);
		$("#stephead").html(pos);
	}
  });
});
</script>
<!-- script type="text/javascript">
$(document).ready(function(){
  $(".first").click(function(){
		var content=$("#step0").html();
		$(".content").html(content);
		$("#step").attr('pos',0);
		$("#stephead").html(0);
  });
});
</script -->
    

    
</div>  
