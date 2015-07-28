$(function() {
    /*
     * позволяет обновить значение 1 параметра, на контроллере значение принимается как newval;
     * все необходимые доп. параметры перечисляются с префиксом data-;
     * контроллер по умолчанию тот же, что и для страницы, если нужен другой его можно добавить
     * в качестве значения параметра data-controller;
     * 
     **/
    
    $('.changingbtn').click(function(){
       
        var changebleElem = $('[name='+$(this).attr('id')+']');
        //alert(changebleElem.attr('data-method'));
        var params = {};
        changebleElem.each(function() {
            $(this.attributes).each(function() {
                if (isData(this.name)) {
                    params[this.name.substring(5)] = this.value;
                }
            });
        });
        var method = params['method'];
        delete params['method'];
        /*var str="";
         $.each(params,function(key,value){
         str+=key+"-"+value+";";
         });
         alert(str);*/

        var elemClone = changebleElem.clone();
        var name = changebleElem.attr('name');
        var paramType = $(changebleElem).attr('data-type');
        var value = $(changebleElem).text();
        var input = "<input type=text id='inputForChangebleElem' class='inp' name='" + name + "' value='" + value + "'/>";
        changebleElem.html(input);
        document.addEventListener('click',changesListener,true);
        function changesListener(event) {
            var newVal = $('#inputForChangebleElem').val();
            var target = $(event.target);
            if (target.attr('name') !== name) {
                if (method !== undefined) {
                    changebleElem.html(newVal);
                    var supurl = "";
                    for (var key in params) {
                        supurl += "&" + key + "=" + params[key];
                    }
                    $.ajax({
                        url: "./" + method + "?newval=" + newVal + supurl,
                        dataType: "json",
                        cache: false,
                        success: function(json) {
                            if (json['status'] != true) {
                                if (json['message'] != undefined) {
                                    alert(json['message']);
                                    changebleElem.html(value);
                                } else {
                                    alert("При обновлении параметра возникла ошибка, ответ сервера не удалось разобрать. Попробуйте обновить страницу и повторить операцию или обратитесь к системному администратору.");
                                }
                            }
                        },
                        error: function(json) {
                            changebleElem.html(value);
                            alert("Ошибка: Обновить информацию не удалось, попробуйте обновить страницу и повторить операцию или обратитесь к системному администратору: "+json);
                        }
                    });
                    
                }
                document.removeEventListener('click', arguments.callee,true);
                
            }

        }
    });

    $('.changebleParam').dblclick(function() {
        var changebleElem = $(this);

        var params = {};
        changebleElem.each(function() {
            $(this.attributes).each(function() {
                if (isData(this.name)) {
                    params[this.name.substring(5)] = this.value;
                }
            });
        });
        var method = params['method'];
        delete params['method'];
        var controller = params['controller'];
        delete params['controller'];
        if(controller===undefined){
            controller=".";
        }
        /*var str="";
         $.each(params,function(key,value){
         str+=key+"-"+value+";";
         });
         alert(str);*/

        var elemClone = changebleElem.clone();
        var name = changebleElem.attr('name');
        var paramType = $(this).attr('data-type');
        var value = $(this).text();
        var input = "<input type=text id='inputForChangebleElem' class='inp' name='" + name + "' value='" + value + "'/>";
        changebleElem.html(input);
        document.addEventListener('click', changesListener);
        function changesListener(event) {
            var newVal = $('#inputForChangebleElem').val();
            var target = $(event.target);
            if (target.attr('name') !== name) {
                if (method !== undefined) {
                    
                    var supurl = "";
                    for (var key in params) {
                        supurl += "&" + key + "=" + params[key];
                    }
                    var urlbody = controller+"/"+method + "?newval=" + newVal + supurl;
                    $.ajax({
                        //url: "./" + method + "?newval=" + newVal + supurl,
                        url: urlbody,
                        dataType: "json",
                        cache: false,
                        success: function(json) {
                            /*var str;
                             $(json).each(function(key, value) {
                             str += key + "=" + value + "; ";
                             })*/
                            if (json['status'] != true) {
                                if (json['message'] != undefined) {
                                    alert(json['message']);
                                } else {
                                    alert("При обновлении параметра возникла ошибка, ответ сервера не удалось разобрать. Попробуйте обновить страницу и повторить операцию или обратитесь к системному администратору.");
                                }
                                changebleElem.html(value);
                            }else{
                                changebleElem.html(newVal);
                            }
                        },
                        error: function(json) {
                            changebleElem.html(value);
                            alert("Ошибка: Обновить информацию не удалось, попробуйте обновить страницу и повторить операцию или обратитесь к системному администратору: "+json);
                        }
                    });
                    
                }
                document.removeEventListener('click', arguments.callee);
                
            }

        }
    });
    
    function isData(str) {
            if (~str.indexOf("data-")) {
                return true;
            }
            return false;
        }


    /*$('.changebleParam').dblclick(function(){
     var changebleElem = $(this);
     var elemClone = changebleElem.clone();//?
     var paramType = $(this).attr('name');
     
     var value = $(this).text();
     var input = "<input type=text id='inputForChangebleElem' class='inp' name='" + paramType + "' value='" + value + "'/>";
     
     changebleElem.html(input);
     
     document.addEventListener('click',changeParam);
     
     function changeParam(event){
     var newVal = $('#inputForChangebleElem').val();
     var target = $(event.target);
     var cientId = $('#elemClone').attr('data-clientid');
     if (target.attr('name') !== paramType) {
     $.ajax({
     url:"/updateClientFromUser?clientId="+cientId+"&param="+paramType+"&newVal="+newVal,
     dataType : "json",
     cache: false,
     success: function(json){
     if(json==true){
     changebleElem.html(newVal);
     }else{
     changebleElem.html(value);
     alert(json);
     }
     //alert(json)
     },
     error: function(json){
     alert("Что-то пошло не так: "+json);
     changebleElem.html(value);
     }
     });
     document.removeEventListener('click',arguments.callee);
     }
     }
     
     return false;
     });*/
});