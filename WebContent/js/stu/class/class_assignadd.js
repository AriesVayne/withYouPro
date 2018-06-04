$(function() {
    console.log("class_assignadd");
    var assingid = getQueryString('assingid');
    var classid = getQueryString('classid');
    if(assingid!=''&&assingid!=null){
    	console.log("学生上传作业");
    	$('.top_navi span').html('作业详情');
    	$('.top_navi img').hide();
    	//$('.btn_lessadd').hide();
    	var url ="/withyou/doClass";
    	var postdata = {
    			varClassId:assingid,
    			userType:'student',
    			act:'getAssignDeta'
    	};
    	$.post(url,postdata,function(result){
    		var JSON = eval('('+result+')');
    		var row = JSON.rs[0];
    		console.log(JSON);
    		$('#title').val(row.title);
    		$('#content').val(row.content);
    	});
    }
    $('.btn_lessadd').click(function () {
    	varResFileName = $('#varResName').val();
        form1.action =
			"/withyou/doClass?act=stuFileUp&varClassId=" +assingid+'&varAssid='+classid+'&varResFileName=sourceFile'
		console.log("varClassId"+varClassId);
		document.form1.submit();
		return;
    });

})