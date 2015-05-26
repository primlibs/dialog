$(function(){
    
    $('.showableModule').click(function(){
        showModule($(this).attr('id'));
    });
    
    function showModule(moduleId){
        var div = '';
        $('#moduleShow').html(div);
        div=$('#'+moduleId+'.hiddenModule').clone();
        $('#moduleShow').html(div);
    }
    
    $('.changebleParam').dblclick(function(){
        var changebleElem = $(this);
        var paramType = $(this).attr('id');
        
        var value = $(this).text();
        var input = "<input type=text id='input' class='inp' name='" + paramType + "' value='" + value + "'/>";
        
        changebleElem.html(input);
        
        //$(document).ready(function(e) {
            //$(document).click(function(event) {});
             
             $(document).addEventListener("click",changeParam,false);
        
            function changeParam(event){
                var newVal = $('#input').val();
                var target = $(event.target);
                var cientId = $('#moduleBufer').data('clientId');
                if (target.attr('id') !== $('#input').attr('id')) {
                    $.ajax({
                        url:"CallCentr/Client/updateClientFromUser?clientId="+cientId+"&param="+paramType+"&newVal="+newVal,
                        dataType : "json",
                        cache: false,
                        success: function(json){
                            changebleElem.html(newVal);
                            alert(json)
                        },
                        error: function(json){
                            alert('fail '+paramType);
                            changebleElem.html(value);
                        }
                    });
                    $(document).removeEventListener("click",changeParam,false);
                }
            }
            
        //});
    });
    
});
