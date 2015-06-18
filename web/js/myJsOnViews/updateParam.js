
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


function isData( string) {
   return !!(string.search('data') + 1);
}



function changeParam(method,newVal,params){
    var supurl="";
    for(var key in params){
        supurl+="&"+key+"="+params[key];
    }
    $.ajax({
        url:"/"+method+"?newVal="+newVal+supurl,
        dataType : "json",
        cache: false,
        success: function(json){
            if(json==="true"){
                return "true";
            }else{
                return "Ошибка: "+json;
            }
            //alert(json)
        },
        error: function(json){
            return "Ошибка: "+json;
        }
    });
}

$('.changebleParam').dblclick(function(){
    var changebleElem = $(this);
    var params={};
    changebleElem.each(function () {
        $(this.attributes).each(function(){
            if(isData(this.name)){
                params[this.name]=this.value;
            }
        });
    });
    params.tos
    var elemClone = changebleElem.clone();
    var paramType = $(this).attr('data-type');
    var value = $(this).text();
    var input = "<input type=text id='inputForChangebleElem' class='inp' name='" + paramType + "' value='" + value + "'/>";
    changebleElem.html(input);
    document.addEventListener('click',changesListener);
    function changesListener(event){
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
});


$('.changebleParam').dblclick(function(){
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
    });