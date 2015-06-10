$(function(){


$('.changebleUserListParam').dblclick(function(){
        var changebleElem = $(this);
        var elemClone = changebleElem.clone();
        var paramType = $(this).attr('name');
        var cabinetUserId = changebleElem.attr('data-cabinetUserId');
        var value = $(this).text();
        var input = "<input type=text id='inputForChangebleUserListElem' class='UserListInp' name='" + paramType + "' value='" + value + "'/>";
        if(paramType=='userRole'){
            if(value=='Администратор'){
                input ="<select id='inputForChangebleUserListElem' class='UserListInp' name='userRole'><option selected value='Администратор'>Администратор</option><option value='Пользователь'>Пользователь</option></select>";
            }else{
                input ="<select id='inputForChangebleUserListElem' class='UserListInp' name='userRole'><option value='Администратор'>Администратор</option><option selected value='Пользователь'>Пользователь</option></select>";
            }
        }else if(paramType=='makingCalls'){
            if(value=='Да'){
                input ="<select id='inputForChangebleUserListElem' class='UserListInp' name='makingCalls'><option selected value='Да'>Да</option><option value='Нет'>Нет</option></select>";
            }else{
                input ="<select id='inputForChangebleUserListElem' class='UserListInp' name='makingCalls'><option value='Да'>Да</option><option selected value='Нет'>Нет</option></select>";
            }
        }
        changebleElem.html(input);
        
        document.addEventListener('click',changeParam);
            
            function changeParam(event){
                var newVal = $('#inputForChangebleUserListElem').val();
                var target = $(event.target);
                if (target.attr('name') !== paramType) {
                    $.ajax({
                        url:"./updateParamInUser?cabinetUserId="+cabinetUserId+"&param="+paramType+"&newVal="+newVal,
                        dataType : "json",
                        cache: false,
                        success: function(json){
                            if(json==true){
                                if(paramType=='userRole'||paramType=='makingCalls'){
                                    changebleElem.html(newVal);
                                }else{
                                    changebleElem.html(newVal);
                                }
                            }else{
                                changebleElem.html(value);
                                alert(json);
                            }
                            //alert(json)
                        },
                        error: function(json){
                            if(paramType=='userRole'||paramType=='makingCalls'){
                                    changebleElem.html(newVal);
                                }else{
                                    changebleElem.html(newVal);
                                }
                        }
                    });
                    document.removeEventListener('click',arguments.callee);
                }
            }
            
            return false;
    });
});