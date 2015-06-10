$(function(){


$('.changebleUserListParam').dblclick(function(){
        var changebleElem = $(this);
        var elemClone = changebleElem.clone();
        var paramType = $(this).attr('name');
        var cabinetUserId = changebleElem.attr('data-cabinetUserId');
        var value = $(this).text();
        var input = "<input type=text id='inputForChangebleUserListElem' class='UserListInp' name='" + paramType + "' value='" + value + "'/>";
        if(paramType=='userRole'){
            input ="<select id='inputForChangebleUserListElem' class='UserListInp' name='userRole'><option value='1'>Администратор</option><option value='0'>Пользователь</option></select>";
        }else if(paramType=='makingCalls'){
            input ="<select id='inputForChangebleUserListElem' class='UserListInp' name='makingCalls'><option value='1'>Да</option><option value='0'>Нет</option></select>";
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
                                    changebleElem.html(input.text());
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
                            alert("Что-то пошло не так: "+json);
                            changebleElem.html(value);
                        }
                    });
                    document.removeEventListener('click',arguments.callee);
                }
            }
            
            return false;
    });
});