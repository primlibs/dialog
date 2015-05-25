$(function(){
    
    $('.showableModule').click(function(){
        showModule($(this).attr('id'));
    });
    
    function showModule(moduleId){
        $('#moduleShow').html($('.hiddenModule #'+moduleId).clone());
    }
});
