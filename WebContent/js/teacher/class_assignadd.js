$(function() {
    console.log("class_assignadd");
    var assingid = getQueryString('assingid');
    var classid = getQueryString('classid');
    if(assingid!=''&&assingid!=null){
    	console.log("作业查看");
    	$('.top_navi span').html('作业详情');
    	$('.top_navi img').hide();
    	$('.btn_lessadd').hide();
    	var url ="/withyou/doClass";
    	var postdata = {
    			varClassId:assingid,
    			userType:'teacher',
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
        var title = $('#title').val();
        var content = $('#content').val();
        console.log(title+content);
        var url = '/withyou/doClass';
        var postdata ={
            varMessTitle:title,
        	varMessCont:content,
        	varClassId:classid,
            act : 'assignadd'
        };
        $.post(url,postdata, function (result) {
            var JSON  = eval('('+result+')');
            if(JSON.rtnCode == 0 ){
                alert('作业添加成功');
            }else{
                alert('作业添加失败');
            }
        });
    });

})