$(function () {

    $('.showableModule').click(function () {
        showModule($(this).attr('id'));
        $('.showableModule').css("font-weight","normal");
        $(this).css("font-weight","Bold");
    });

    $('.checkIn').click(function () {
        var moduleId = $(this).attr('id');
        $.ajax({
            url: "../Event/inCall?moduleId=" + moduleId,
            dataType: "json",
            cache: false,
        });
    });





    $('.fullshowgroupwithmodules').click(function () {
        var groupId = $(this).attr('data-fullshowgroupid');
        $('.hidingModule[data-fullshowgroupid=' + groupId + ']').toggleClass('hidden');
    })

    $('.groupwithmodules').click(function () {
        var groupId = $(this).attr('data-groupid');
        $('.hidingModule[data-groupid=' + groupId + ']').toggleClass('hidden');
    })

    function showModule(moduleId) {
        var div = '';
        $('#moduleShow').html(div);

        writeHistory(moduleId);

        div = $('#' + moduleId + '.hiddenModule').clone();
        $('#moduleShow').html(div);

    }

    function writeHistory(moduleId) {
        var dateObj = new Date();
        var dateInp = '<input type="hidden" name="dates" value="' + dateObj.valueOf() + '">';
        var moduleInp = '<input type="hidden" name="modules" value="' + moduleId + '">';

        $('#badFinishForm').append(moduleInp);
        $('#goodFinishForm').append(moduleInp);
        $('#postponeForm').append(moduleInp);
        $('#badFinishForm').append(dateInp);
        $('#goodFinishForm').append(dateInp);
        $('#postponeForm').append(dateInp);
    }

    /*function writeHistory(moduleId){
     var dateObj = new Date();
     var eventId = $('#moduleBufer').attr('data-eventId');
     $.ajax({
     url:"../Event/writeModuleInHistory?date="+dateObj.valueOf()+"&moduleId="+moduleId+"&eventId="+eventId,
     dataType : "json",
     type: "POST",
     cache: false,
     success: function(json){
     //alert(json)
     },
     error: function(json){
     //alert('fail ');
     }
     });
     }*/



    $('.changebleEventParam').dblclick(function () {
        var changebleElem = $(this);
        var paramType = $(this).attr('id');

        var value = $(this).text();
        var input = "<input type=text id='input' class='inp' name='" + paramType + "' value='" + value + "'/>";

        changebleElem.html(input);

        document.addEventListener('click', changeParam);

        function changeParam(event) {
            var newVal = $('#input').val();
            var target = $(event.target);
            var cientId = $('#moduleBufer').attr('data-clientid');
            var eventId = changebleElem.attr('data-eventId')
            if (target.attr('name') !== paramType) {
                $.ajax({
                    url: "../Event/updateClientFromUser?clientId=" + cientId + "&param=" + paramType + "&newVal=" + newVal + "&eventId=" + eventId,
                    dataType: "json",
                    cache: false,
                    success: function (json) {
                        if (json == true) {
                            changebleElem.html(newVal);
                        } else {
                            changebleElem.html(newVal);
                           
                        }

                    },
                    error: function (json) {
                        alert("Что-то пошло не так: " + json);
                        changebleElem.html(value);
                    }
                });
                document.removeEventListener('click', arguments.callee);
            }
        }

        return false;
    });

});
