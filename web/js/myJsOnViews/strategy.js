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
      items: "tr:not(.groupHeader)",
      opacity: 0.4, 
      zIndex: 102,
      stop: function(event, ui) {
           var moduleId = $(ui.item).attr("data-moduleid");
           var newPosition = ui.item.index()-1;
           $.ajax({
                        url:"../Strategy/changeModulePosition?moduleId="+moduleId+"&newPosition="+newPosition,
                        dataType : "json",
                        cache: false,
                        error: function(json){
                            alert("Что-то пошло не так: "+json);
                        }
                    });
        }
    });
    $("#sortableGroupsDiv").sortable({
      handle: ".groupHeader",
      opacity: 0.4, 
      zIndex: 102,
      stop: function(event, ui) {
           var groupId = $(ui.item).attr("data-groupid");
           var newPosition = ui.item.index()+1;
           $.ajax({
                        url:"../Strategy/changeGroupPosition?groupId="+groupId+"&newPosition="+newPosition,
                        dataType : "json",
                        cache: false,
                        error: function(json){
                            alert("Что-то пошло не так: "+json);
                        }
                    });
        }
    });
});