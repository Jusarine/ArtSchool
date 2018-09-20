$(document).ready(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $('.x_editable').editable({
        mode: 'inline',
        emptytext: 'What are you currently working on?',
        ajaxOptions: {
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        }
    });
    $.fn.editableform.buttons =
        '<button type="submit" class="btn btn-primary btn-sm editable-submit">'+
        '<i class="fa fa-fw fa-check"></i>'+
        '</button>'+
        '<button type="button" class="btn btn-default btn-sm editable-cancel">'+
        '<i class="fa fa-fw fa-times"></i>'+
        '</button>';
});