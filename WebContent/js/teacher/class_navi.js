$(function() {
	var uuid = getQueryString('uuid');
	console.log(uuid);
	$('.content ul .first').click(function() {
        console.log(uuid);
		window.location.href = "class_info.html?uuid="+uuid;
    });

    $('.content ul .second').click(function() {
        console.log(uuid);
        window.location.href = "class_file.html?uuid="+uuid;
    });

    $('.content ul .third').click(function() {
        console.log(uuid);
        window.location.href = "class_assign.html?uuid="+uuid;
    });
})