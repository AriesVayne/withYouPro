$(function() {
	
	var enginConent = '';
	showEngineer();
	
	$(document).delegate(".am-btn-toolbar .am-text-secondary","click",function(){
		var varUuid = $(this).parent().find('#info_id').data('id');
		var sele = $(this).parent().find('.am-text-secondary option:checked').val();//类型
		if(sele != 0){
			console.log(varUuid+"----"+sele);
			var url = '/withyou/doRepair';
			var postdata={
					varUuid:varUuid,
					act:'updateInfo',
					varRepairid:sele,
					varObject:'manager'
			};
			console.log("updateInfo--manager"+postdata);
			$.post(url,postdata,function(result){
				var JSON = eval('('+result+')')
				if(JSON.rtnCode == 0){
					console.log("assEngineer---"+JSON);
					alert("更新成功");
					showList();
				}else{
					console.log("指派工程师失败");
				}
			});
		}
	} );
    
	$(document).delegate(".am-btn-toolbar .am-hide-sm-only","click",function(){
		var varUuid = $(this).parent().find('#info_id').data('id');
		console.log(varUuid);
		var url = '/withyou/doRepair';
		var postdata={
				varUuid:varUuid,
				act:'updateInfo',
				varRepairfalg:'2',
				varObject:'manager'
		};
		console.log("updateInfo--manager"+postdata+varUuid);
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')')
			if(JSON.rtnCode == 0){
				console.log("assEngineer---"+JSON);
				alert("更新成功");
				showList();
			}else{
				console.log("获取工程师失败");
			}
		});
	});
	
	function showEngineer(){
		console.log("showEngineer");
		var url = '/withyou/doRepair';
		var postdata={
				act:'getEngineer'
		};
		enginConent = '<option class="am-icon-pencil-square-o" value="0">指派</option>\n';
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')')
			if(JSON.rtnCode == 0){
				console.log(JSON);
				var row = JSON.rs;
				var length = row.length;
				for(var i=0; i<length;i++){
					enginConent += '<option class="am-icon-pencil-square-o" value="'+row[i].enginname+'">'+row[i].enginrealname+'</option>\n';
				}
				showList();
			}else{
				console.log("获取工程师失败");
			}
		});
	}
	
	function showList(){
		var url = '/withyou/doRepair';
		var items = "";
		var postdata = {
		    	act:'getRepairList',
		        varObject:'manager'
		    };
		$.post(url, postdata, function(result) {
			var JSON = eval('(' + result + ')');
			console.log(JSON);
			if (JSON.rtnCode == 0) {
				var row = JSON.rs;
				var length = row.length;
				for(var i=0; i<length; i++){
					var restatus='';
					var enginlist ='';
					var modconten = '<select id="info_id"  disabled data-id="'+row[i].uuid+'"\n';
					switch(row[i].repairflag){
					case '0':
						restatus='已解决';
						break;
					case '1':
						restatus='未解决';
						enginlist = enginConent;
						modconten = '<select id="info_id"  data-id="'+row[i].uuid+'"\n';
						break;
					case '2':
						restatus='已驳回';
						break;
					case '3':
						restatus='已指派';
						break;
					}
                    items +=
                    	'<tr>\n' +
                        '<td><input type="checkbox"></td>\n' +
                        '<td>'+(i+1)+'</td>\n' +
                        '<td><a href="#">'+row[i].location+'</a></td>\n' +
                        '<td>'+row[i].device+'</td>\n' +
                        '<td class="am-hide-sm-only">'+row[i].realname+'</td>\n' +
                        '<td class="am-hide-sm-only">'+row[i].uploadtime+'</td>\n' +
                        '<td class="am-hide-sm-only">'+restatus+'</td>\n' +
                        '<td>\n' +
                        '<div class="am-btn-toolbar">\n' +
                        '<div class="am-btn-group am-btn-group-xs">\n' +
                        modconten+
                        'class="am-btn am-btn-default am-btn-xs am-text-secondary">\n' +
                        enginlist+
                        '</select>\n' +
                        '<button\n' +
                        'class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">\n' +
                        '<span class="am-icon-trash-o"></span> 关闭\n' +
                        '</button>\n' +
//                        '<button\n' +
//                        'class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">\n' +
//                        '<span class="am-icon-trash-o"></span> 删除\n' +
                        '</button>\n' +
                        '</div>\n' +
                        '</div>\n' +
                        '</td>\n' +
                        '</tr>';
				}
				$('.am-cf span').text(i);
				$('.am-g table tbody').empty().append(items);
			} else {
				console.log('查询失败');
				return;
			} 
		});
	}
});
