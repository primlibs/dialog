$('.changebleParam').dblclick(function(){
        var changebleElem = $(this);
        var paramType = $(this).attr('name');
        
        var value = $(this).text();
        var input = "<input type=text id='input' class='inp' name='" + paramType + "' value='" + value + "'/>";
        
        changebleElem.html(input);
        
        document.addEventListener('click',changeParam);
            
            function changeParam(event){
                var newVal = $('#input').val();
                var target = $(event.target);
                var cientId = $('#moduleBufer').attr('data-clientid');
                if (target.attr('name') !== paramType) {
                    $.ajax({
                        url:"../Event/updateClientFromUser?clientId="+cientId+"&param="+paramType+"&newVal="+newVal,
                        dataType : "json",
                        cache: false,
                        success: function(json){
                            if(json==true){
                                changebleElem.html(newVal);
                            }else{
                                changebleElem.html(value);
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