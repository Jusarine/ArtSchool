$(document).ready(function() {
    $('.x_editable').editable({
        mode: 'inline',
        emptytext: 'What are you currently working on?'
    });
    $.fn.editableform.buttons =
        '<button type="submit" class="btn btn-primary btn-sm editable-submit">'+
        '<i class="fa fa-fw fa-check"></i>'+
        '</button>'+
        '<button type="button" class="btn btn-default btn-sm editable-cancel">'+
        '<i class="fa fa-fw fa-times"></i>'+
        '</button>';
});