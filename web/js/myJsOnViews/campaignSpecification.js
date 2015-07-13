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
            var wrclass = $('#workReport').attr('class')
            if(wrclass.indexOf('hidden')+1){
                $("input[name='wropen']").val('0');
            }else{
                $("input[name='wropen']").val('1');
            }
        });
});
