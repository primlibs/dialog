$(function() {
    /*var params={};
     changebleElem.each(function () {
     $(this.attributes).each(function(){
     params[this.name]=this.value;
     });
     });
     var str = "";
     $.each( params, function( key, value ) {
     str+= key + ": " + value+";";
     });
     alert(str);*/






    function changeParam(method, newVal, params) {
        var supurl = "";
        for (var key in params) {
            supurl += "&" + key + "=" + params[key];
        }
        $.ajax({
            url: "./" + method + "?newval=" + newVal + supurl,
            dataType: "json",
            cache: false,
            success: function(json) {
                var str;
                $(json).each(function(key, value) {
                    str += key + "=" + value + "; ";
                })
                /*if(json==="true"){
                 return "true";
                 }else{
                 return "Ошибка: "+json;
                 }*/
                return json;
                //alert(json)
            },
            error: function(json) {
                //return "Ошибка: "+json;
                return json;
            }
        });
    }

    $('.changebleParam').dblclick(function() {
        var changebleElem = $(this);

        function isData(str) {
            if (~str.indexOf("data-")) {
                return true;
            }
            return false;
        }
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
                            /*var str;
                             $(json).each(function(key, value) {
                             str += key + "=" + value + "; ";
                             })*/
                            if (json['status'] != 'true') {
                                if (json['message'] != undefined) {
                                    alert(json['message']);
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
                document.removeEventListener('click', arguments.callee);
                
            }

        }
    });


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