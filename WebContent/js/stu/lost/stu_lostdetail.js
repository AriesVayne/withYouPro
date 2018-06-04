$(function() {
	var uuid = getQueryString('uuid');
	var hf = getQueryString('hf');
	var type = getQueryString('type');
	var resflag = getQueryString('resflag');

	showDetail();
	function showDetail(){
	    console.log("lostdetail--uuid"+uuid+"hf--"+hf);
	    if(hf=='1'||resflag == '0'){
	    	$('#ownerfind').css('display','none');
	    	$('#donebutton').css('display','none');
	    } 
	    if(type=='lost'||resflag == '0'){
	    	$('#ownerfind').css('display','none');
	    }
	    var url = "/withyou/doLostFound";
		var postdata={
				act:'lostDetail',
				varUuid:uuid
		};
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')');
			console.log(JSON);
			if(JSON.rtnCode == 0){
				var row = JSON.rs;
				$("#people em").text(row[0].pickname);
				$("#phone em").text(row[0].pickphone);
				$("#locale em").text(row[0].picklocale);
				$("#resname em").text(row[0].resname);
				$("#time em").text(row[0].picktime);
				$(".desc_detail #faultimg img").attr('src',"/withyou/FileUpload/lostFound/"+row[0].respicpath);
				$(".desc_detail #resfeature textarea").val(row[0].resfeature);
				if(row[0].resflag == '0'){
					$('#status em').text('已回家');
				}else{
					$('#status em').text('走失中');
				}
			}else{
				console.log("获取数据失败");
			}
		});
	}
	$('#donebutton').click(function (){
		var postdata = {
			act:'updateLost',
			varResType:type,
			varUuid : uuid,
			varResFlag : '0'
		};
		if (type == 'found') {
			postdata.varOwerName = $('#owername input').val();
			postdata.varOwerPhone = $('#owerphone input').val();
		}
		var url='/withyou/doLostFound';
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')');
			console.log(JSON);
		});
	});
});
