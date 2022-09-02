$(function(){

    var numberClick = 0;

    const appendPeople = function(data){
        var peopleCode = '<a href="#" class="people-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#people-list')
            .append('<div>' + peopleCode + '</div>');
    };

    //Loading peoples on load page
//    $.get('/peoples/', function(response)
//    {
//        for(i in response) {
//            appendPeople(response[i]);
//        }
//    });

    //Show adding people form
    $('#show-add-people-form').click(function(){
        $('#people-form').css({display: 'flex'});
    });

//Closing adding people form
    $('#people-form').click(function(event){
        if(event.target === this) {
            $(this).css({display: 'none'});
        }
    });

    $('#people-put-form').click(function(event){
        if(event.target === this) {
            $(this).css({display: 'none'});
            location.reload();
        }
    });

    //Getting people
    $(document).on('click', '.people-link', function(){
        var link = $(this);
        var peopleId = link.data('id');

        $.ajax({
            method: "GET",
            url: '/peoples/' + peopleId,
            success: function(response)
            {

                    var code = '<div class="people-one"><span>Сообщение:' + response.message +
                    '</span><p></p><button id="put-people" data-id="' + peopleId +
                    '">Редактировать</button><p></p><button id="dell-people" data-id="' + peopleId +
                    '">Удалить</button></div>';

                    link.parent().append(code);
                    numberClick = link.data('id');
                 $('#put-people').click(function(){
                   $('#people-put-form > form').html('');
                   let fillingPeoplePutForm = '<h2>Редактирование данных о человеке</h2>';
                   fillingPeoplePutForm += '<label>Имя человека </label>';
                   fillingPeoplePutForm += '<input type="text" name="name" value="' + response.name +
                   '">';
                   fillingPeoplePutForm += '<label>Сообщение</label>';
                   fillingPeoplePutForm += '<input type="text" name="message" value="' + response
                   .message + '">';
                   fillingPeoplePutForm += '<hr><button id="put-people-save" data-id="' + peopleId +
                   '">Редактировать</button>';
                   $('#people-put-form > form').append(fillingPeoplePutForm);
                   $('#people-put-form').css({display: 'flex'});
                   $('#put-people-save').click(function()
                       {
                           var data = $('#people-put-form form').serialize();
                           var link = $(this);
                           var peopleId = link.data('id');
                           $.ajax({
                               method: "PUT",
                               url: '/peoples/' + peopleId,
                               data: data,
                               success: function(response)
                               {
                                   $('#people-put-form').css('display', 'none');
                                   var people = {};
                                   people.id = response;
                                   var dataArray = $('#people-put-form form').serializeArray();
                                   for(i in dataArray) {
                                       people[dataArray[i]['header']] = dataArray[i]['value'];
                                   }
                                   appendPeople(people);
                               }
                           });
                           location.reload();
                           return false;
                       });
                });

                $('#dell-people').click(function(){
                   var link = $(this);
                   var peopleId = link.data('id');
                   $.ajax({
                       method: "DELETE",
                       url: '/peoples/' + peopleId,
                   });
                   location.reload();
                   return false;
                });
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Человек не найден!');
                }
            }
        });
        return false;
    });



    //Adding People
    $('#save-people').click(function()
    {
        var data = $('#people-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/peoples/',
            data: data,
            success: function(response)
            {
                $('#people-form').css('display', 'none');
                var people = {};
                people.id = response;
                var dataArray = $('#people-form form').serializeArray();
                for(i in dataArray) {
                    people[dataArray[i]['header']] = dataArray[i]['value'];
                }
                appendPeople(people);
            }
        });
        return false;
    });
});