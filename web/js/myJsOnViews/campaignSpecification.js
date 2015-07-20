$(function(){
        $('.changeAssignFromSpec').on('click',function(){
            var userId = $(this).attr('data-userid');
            $('#userFromId').val(userId);
            //alert(userId)
        });
        $('#moduleReportTumblr').on('click',function(){
            $('#moduleReport').toggleClass('hidden');
        });
        $('#failReasonReportTumblr').on('click',function(){
            $('#failReasonReport').toggleClass('hidden');
        });
        $('#workReportTumblr').on('click',function(){
            $('#workReport').toggleClass('hidden');
            var wrclass = $('#workReport').attr('class')
            if(wrclass.indexOf('hidden')+1){
                $("input[name='wropen']").val('0');
            }else{
                $("input[name='wropen']").val('1');
            }
        });
        $('#showModulesWithText').change(function(){
            var campaignId=$(this).attr('data-campaignid');
           var show;
           if ($(this).is(':checked')) {
               show=true;
            }else{
               show="";
            }
            
                $.ajax({
                        url:"../Event/setShowModulesWithText?show="+show+"&campaignId="+campaignId,
                        dataType : "json",
                        cache: false,
                        success: function(json){ 
                        },
                        error: function(json){
                            //alert("Что-то пошло не так: "+json);
                        }
                    });
        });
});
