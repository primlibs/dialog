$(function(){
    
    $('.showableModule').click(function(){
        showModule($(this).attr('id'));
    });
    
    function showModule(moduleId){
        var div = $('#'+moduleId+'.hiddenModule').clone();
        $('#moduleShow').html(div);
    }
});
