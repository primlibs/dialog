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
            /*$(document).addEventListener(function(event) {
                var newVal = $('#input').val();
                var target = $(event.target);
                var cientId = $('#moduleBufer').data('clientId');
                alert(event.type+"-"+event.currentTarget);
                if (target.attr('name') !== paramType) {
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
                }
                

            });*/
        
        document.addEventListener('click',changeParam);
            
            function changeParam(event){
                var newVal = $('#input').val();
                var target = $(event.target);
                var cientId = $('#moduleBufer').attr('[data-clientid]');
                //alert(event.type+"-"+event.currentTarget);
                if (target.attr('name') !== paramType) {
                    $.ajax({
                        url:"CallCentr/Client/updateClientFromUser?clientId="+cientId+"&param="+paramType+"&newVal="+newVal,
                        dataType : "json",
                        cache: false,
                        success: function(json){
                            changebleElem.html(newVal);
                            alert(json)
                        },
                        error: function(json){
                            alert('fail ');
                            changebleElem.html(value);
                        }
                    });
                    document.removeEventListener('click',arguments.callee);
                }
            }
            
            return false;
        //});
    });
    
});
