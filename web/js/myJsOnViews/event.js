$(function(){
    alert(rdy)
    function showModule(moduleId){
        $('#moduleShow').html($('.hiddenModule #'+moduleId).clone());
    }
});
