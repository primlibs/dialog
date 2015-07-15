$(function(){
    $('.changebleStrategyParam').dblclick(function(){
        var changebleElem = $(this);
        var strategyId = changebleElem.attr('id');
        var paramType = changebleElem.attr('name');
        
        var value = $(this).text();
        var input = "<input type=text id='input' class='inp' name='" + paramType + "' value='" + value + "'/>";
        
        changebleElem.html(input);
        
        document.addEventListener('click',changeParam);
            
            function changeParam(event){
                var name = $('#input').val();
                var target = $(event.target);
                if (target.attr('name') !== paramType) {
                    $.ajax({
                        url:"../Strategy/renameStrategy?strategyId="+strategyId+"&name="+name,
                        dataType : "json",
                        cache: false,
                        success: function(json){
                            if(json==true){
                                changebleElem.html(name);
                            }else{
                                changebleElem.html(value);
                            }
                            
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
    
    $(".sortableGroup").sortable({
      items: "tr:not(.groupHeader)"
    });
    $("#sortableGroupsDiv").sortable({
      handle: ".groupHeader"
    });
});