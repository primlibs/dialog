$(function(){
        $('.changeAssignFromSpec').on('click',function(){
            var userId = $(this).attr('data-userid');
            $('#userFromId').val(userId);
            //alert(userId)
        });
        $('#moduleReportTumblr').on('click',function(){
            $('#moduleReport').toggleClass('hidden');
        })
        $('#workReportTumblr').on('click',function(){
            $('#workReport').toggleClass('hidden');
        })
});
