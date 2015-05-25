$(function(){
    function showModule(moduleId){
        $('#moduleShow').html($('.hiddenModule #'+moduleId).clone());
    }
});
