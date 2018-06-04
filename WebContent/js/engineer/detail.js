$(function() {
	showDetail();
	$(".repair_content ul button").click(function(){
		var uuid = getQueryString('uuid');
		var feedback = $("#feedback textarea").val();
		var repairflag = $('#opeartion option:checked').val();//类型
		var url = "/withyou/doRepair";
		var postdata = {
				act:'updateInfo',
				varObject:'engineer',
				varUuid:uuid,
				repairflag:repairflag,
				feedback:feedback
		};
		console.log(postdata);
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')');
			if(JSON.rtnCode == 0){
				alert("更新成功");
				console.log("更新成功");
			}else{
				alert("更新失败");
				console.log("更新失败");
			}
		});
	
	});
	function showDetail() {
		var uuid = getQueryString('uuid');
		var url = "/withyou/doRepair";
		var postdata = {
			act : 'repairDetail',
			varUuid : uuid
		};
		$.post(url, postdata,
				function(result) {
					var JSON = eval('(' + result + ')');
					console.log(JSON);
					if (JSON.rtnCode == 0) {
						var row = JSON.rs;
						$("#people em").text(row[0].realname);
						$("#phone em").text(row[0].connphone);
						$("#location em").text(row[0].location);
						$("#device em").text(row[0].device);
						$("#status em").text("未解决");
						if (row[0].repairflag == 0) {
							$("#status em").text("已解决");
						}
						if (row[0].repairflag == 2) {
							$("#status em").text("已关闭");
						}
						$(".desc_detail #faultimg img").attr('src',"/withyou/FileUpload/repairOnline/"+ row[0].picture);
						$(".desc_detail #faultdesc textarea").val(
								row[0].repairdesc);
						if(row[0].feedback!=null){
							$(".desc_detail #feedback textarea").val(
									row[0].feedback);
						}
					} else {
						console.log("获取数据失败");
					}
				});
	}
});
