$(function(){
    
    $('.deletinghref').click(function(){
       var cuid = $(this).attr('id') ;
       $('[name = tagIdtoDelete]').val(cuid);
    });
    
});

