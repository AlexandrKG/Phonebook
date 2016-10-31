$(document).ready(function() {
    $('#addphone').click( function(event){
//        event.preventDefault();
        $('#overlay').fadeIn(400,
            function(){
                $('#modal_form')
                    .css('display', 'block')
                    .animate({opacity: 1, top: '50%'}, 200);
            });
    });

    $('#modal_close, #overlay').click(closemodal);

    function closemodal(){
        $('#modal_form')
            .animate({opacity: 0, top: '45%'}, 200,
            function(){
                $(this).css('display', 'none');
                $('#overlay').fadeOut(400);
            }
        );
    }
});

function closemodal(){
    $('#modal_form')
        .animate({opacity: 0, top: '45%'}, 200,
        function(){
            $(this).css('display', 'none');
            $('#overlay').fadeOut(400);
        }
    );
    resiveFilterData();
};

function tester(){
    var $form = $("#phoneForm"),
        term = $form.find( "input[name='name']" ).val() + ";" +
            $form.find( "input[name='surname']" ).val() + ";" +
            $form.find( "input[name='middlename']" ).val() + ";" +
            $form.find( "input[name='mobile']" ).val() + ";" +
            $form.find( "input[name='phone']" ).val() + ";" +
            $form.find( "input[name='address']" ).val() + ";" +
            $form.find( "input[name='email']" ).val();

    alert("Test: " + term);
};

function sendData(idOwner){
    var $form = $("#phoneForm"),
        term = $form.find( "input[name='name']" ).val() + ";" +
            $form.find( "input[name='surname']" ).val() + ";" +
            $form.find( "input[name='middlename']" ).val() + ";" +
            $form.find( "input[name='mobile']" ).val() + ";" +
            $form.find( "input[name='phone']" ).val() + ";" +
            $form.find( "input[name='address']" ).val() + ";" +
            $form.find( "input[name='email']" ).val() + ";" + idOwner,
        url = $form.attr( "action" );
    $.post( url, { data: term });
};

function resiveAllData(rdata){
    $("#plist").find("tr:gt(0)").remove();
    $.getJSON("/getphonelist",{ uid: rdata },function(data,status) {
        var items = [];
        var parsVal;
        $.each(data, function(key, val) {
            parsVal = jQuery.parseJSON(val);
            items.push('<tr>');
            items.push('<td>' + parsVal.Num + '</td>');
            items.push('<td>' + parsVal.Name + '</td>');
            items.push('<td>' + parsVal.Surname + '</td>');
            items.push('<td>' + parsVal.Middlename + '</td>');
            items.push('<td>' + parsVal.Mobail + '</td>');
            items.push('<td>' + parsVal.Phone + '</td>');
            items.push('<td>' + parsVal.Address + '</td>');
            items.push('<td>' + parsVal.Email + '</td>');
            items.push('</tr>');
        });
        $('<tbody/>', {'class': 'my-new-list',html: items.join('')}).appendTo('#plist');
    });
};


function resiveFilterData(){
    $("#plist").find("tr:gt(0)").remove();

    var $form = $("#filter"),
        term_id = $form.find( "input[name='uid']" ).val(),
        term_nm = $form.find( "input[name='fname']" ).val(),
        term_sn = $form.find( "input[name='fsurname']" ).val(),
        term_ph = $form.find( "input[name='fmobile']" ).val(),
        url = $form.attr( "action" );
    $.post(url,{uid:term_id,fname:term_nm,fsurname:term_sn,fmobile:term_ph },function(data,status) {
        var items = [];
        var parsVal;
        $.each(data, function(key, val) {
            val.sort;
            parsVal = jQuery.parseJSON(val);
            items.push('<tr>');
            items.push('<td>' + '<input type="checkbox" id="'+parsVal.Num+'"/> ' + '</td>');
            items.push('<td>' + parsVal.Name + '</td>');
            items.push('<td>' + parsVal.Surname + '</td>');
            items.push('<td>' + parsVal.Middlename + '</td>');
            items.push('<td>' + parsVal.Mobail + '</td>');
            items.push('<td>' + parsVal.Phone + '</td>');
            items.push('<td>' + parsVal.Address + '</td>');
            items.push('<td>' + parsVal.Email + '</td>');
            items.push('</tr>');
        });
        $('<tbody/>', {'class': 'my-new-list',html: items.join('')}).appendTo('#plist');
    });
};


function delRecords(){
    var max = $("input:checkbox:checked").length;
//    alert(max);
    var items = "";
    for (var i = 0; i < $("input:checkbox:checked").length; i++) {
        items +=  $("input:checkbox:checked")[i].id ;
        if(i<max-1) {
            items += ";"
        }
    }
//    alert("To del = " + items);
    $.post( "/delcontacts", { data: items });
};