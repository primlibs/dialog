$(function(){
        $('.changeAssignFromSpec').on('click',function(){
            var userId = $(this).attr('data-userid');
            $('#userFromId').val(userId);
            //alert(userId)
        });
});
