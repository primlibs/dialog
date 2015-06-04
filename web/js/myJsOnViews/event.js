$(function(){
    
    $('.showableModule').click(function(){
        showModule($(this).attr('id'));
    });
    
    function showModule(moduleId){
        var div = '';
        $('#moduleShow').html(div);
        
        writeHistory(moduleId);
        
        div=$('#'+moduleId+'.hiddenModule').clone();
        $('#moduleShow').html(div);
        
    }
    
    function writeHistory(moduleId){
        var dateObj = new Date();
        var eventId = $('#moduleBufer').attr('data-eventId');
        $.ajax({
            url:"/CallCentr/Event/writeModuleInHistory?date="+dateObj.valueOf()+"&moduleId="+moduleId+"&eventId="+eventId,
            dataType : "json",
            type: "POST",
            cache: false,
            success: function(json){
                //alert(json)
            },
            error: function(json){
                //alert('fail ');
            }
        });
    }
    
    
    
    $('.changebleParam').dblclick(function(){
        var changebleElem = $(this);
        var paramType = $(this).attr('id');
        
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
                        url:"/CallCentr/Event/updateClientFromUser?clientId="+cientId+"&param="+paramType+"&newVal="+newVal,
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
    
});
