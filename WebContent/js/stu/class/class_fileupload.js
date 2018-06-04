$(function() {
	console.log("class_fileupload");
	var varClassId = getQueryString('calssid')
	$('.content .button_fileup').click(function () {
		console.log("12313");
		varResFileName = $('#varResName').val();
        form1.action =
			"/withyou/doClass?act=teacFileUp&varClassId=" +varClassId+'&varResFileName='+varResFileName;
		console.log("varClassId"+varClassId);
		document.form1.submit();
		return;
	});

});