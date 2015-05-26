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
        var cientId = $('#moduleBufer').attr('[data-clientId]');
        var value = $(this).text();
        var input = "<input type=text id='" + cientId + "' class='inp' name='" + paramType + "' value='" + value + "'/>";
        
        changebleElem.html(input);
        $(document).on('click', function(event) {
            var target = $(event.target);
            var newVal = $('.inp').val();
            if (target.attr('name') !== input.attr('name')) {
                $.ajax({
                    url:"CallCentr/Client/updateClientFromUser?clientId="+cientId+"&param="+paramType+"&newVal="+newVal,
                    dataType : "json",
                    cache: false,
                    success: function(json){
                        changebleElem.html(newVal);
                    },
                    error: function(json){
                        alert('fail');
                        changebleElem.html(value);
                    }
                });
            }
        });
        
    });
    
});
