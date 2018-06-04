$(function() {
    console.log("class_info");
    var uuid = getQueryString('messid');
    var classid = getQueryString('classid');
    if(uuid!=''&&uuid!=null){
    	console.log("消息查看");
    	$('.top_navi span').html('消息详情');
    	$('.top_navi img').hide();
    	$('.btn_lessadd').hide();
    	var url ="/withyou/doClass";
    	var postdata = {
    			varClassId:uuid,
    			userType:'student',
    			act:'getClaInDeta'
    	};
    	$.post(url,postdata,function(result){
    		var JSON = eval('('+result+')');
    		var row = JSON.rs[0];
    		console.log();
    		$('#title').val(row.title);
    		$('#content').val(row.content);
    	});
    }
    $('.btn_lessadd').click(function () {
        var title = $('#title').val();
        var content = $('#content').val();
        console.log(title+content);
        var url = '/withyou/doClass';
        var postdata ={
            varMessTitle:title,
        	varMessCont:content,
        	varClassId:classid,
            act : 'infoadd'
        };
        $.post(url,postdata, function (result) {
            var JSON  = eval('('+result+')');
            if(JSON.rtnCode == 0 ){
                alert('通知添加成功');
            }else{
                alert('通知添加失败');
            }
        });
    });

})