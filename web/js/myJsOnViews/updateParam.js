$('.changebleParam').dblclick(function(){
        var changebleElem = $(this);
        var elemClone = changebleElem.clone();
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