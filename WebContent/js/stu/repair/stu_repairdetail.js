$(function() {
	console.log("asfsa");
	var uuid = getQueryString('uuid');
	var url = "/withyou/doRepair";
	var postdata={
			act:'repairDetail',
			varUuid:uuid
	};
	$.post(url,postdata,function(result){
		var JSON = eval('('+result+')');
		console.log(JSON);
		if(JSON.rtnCode == 0){
			var row = JSON.rs;
			$("#people em").text(row[0].realname);
			$("#phone em").text(row[0].connphone);
			$("#location em").text(row[0].location);
			$("#device em").text(row[0].device);
			$("#status em").text("未解决");
			if(row[0].repairflag == 0){
				$("#status em").text("已解决");
			}
			if(row[0].repairflag == 2){
				$("#status em").text("已关闭");
			}
			if(row[0].repairflag == 3){
				$("#status em").text("已指派");
			}
			$(".desc_detail #faultimg img").attr('src',"/withyou/FileUpload/repairOnline/"+row[0].picture);
			$(".desc_detail #faultdesc textarea").val(row[0].repairdesc);
			if(row[0].feedback!= null){
				$(".desc_detail #feedback textarea").val(row[0].feedback);
			}
		}else{
			console.log("获取数据失败");
		}
	});
});
