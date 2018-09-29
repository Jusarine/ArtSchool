$(document).ready(function(){
    $("input:radio[name=gender]").click(function() {
        let value = $(this).val();
        let image_name;
        if(value === 'MALE'){
            image_name = "/images/boy.png";
        }else if (value === 'FEMALE'){
            image_name = "/images/girl.png";
        }
        $('#image').attr('src', image_name);
    });
});