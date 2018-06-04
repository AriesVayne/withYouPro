$(function() {
	var varLostResType= 'all'; //全部
	var content = '';
	var hf='1';
	showDetail();
	$('.buttons-tab #button_1').click(function(){
		varLostResType= 'all';//全部
	});
	$('.buttons-tab #button_2').click(function(){
		varLostResType= 'lost';//我的失物
	});
	$('.buttons-tab #button_3').click(function(){
		varLostResType= 'found';//我的失物
	});

	$('.buttons-tab a').click(function(){
		console.log("$('.buttons-tab a').click");
		if($(this).attr("href")=='#tab1'){
			hf='1';
  			//$('#tab1 #shopListAdvancedQuery').empty().append(content);
  		}
  		if($(this).attr("href")=='#tab2'){
  			hf='2';
//  			$('#tab2 #shopListAdvancedQuery').empty().append(content);
  		}
  		if($(this).attr("href")=='#tab3'){
  			hf='3';
//  			$('#tab3 #shopListAdvancedQuery').empty().append(content);
  		}
  		showDetail();
    });
	function showDetail(){
		var url = '/withyou/doLostFound';
        var postdata = {
        	act:'getLostList',
            varObject:'stu',
            varLostResType:varLostResType
        };
        $.post(url,postdata,function(result){
            var JSON = eval('('+result+')');
            console.log(JSON);
            if (JSON.rtnCode == 0) {
            	content = ''
                console.log('获取成功');
                var row = JSON.rs;
                var rsLength = row.length;
                for(var i=0; i<rsLength; i++){
                	var type  = "";
                	if(row[i].restype =='lost'){
                		type = '<li><i class="icon_cube red">失</i>\n';
                	}else{
                		type = '<li><i class="icon_cube red">拾</i>\n';
                	}
                	 content += '<a href="lost_detail.html?uuid='+row[i].uuid+'&hf='+hf+'&type='+row[i].restype+'&resflag='+row[i].resflag+'" class="external">'+
                	 type+
                     '<div class="lt">\n' +
                     '<h5>'+row[i].resname+'</h5>\n' +
                     '<s>'+row[i].resfeature+'</s>\n' +
                     '</div>\n' +
                     '<div class="lt">'+
						'<!-- <h5>2017</h5> -->'+
						'<s id="time_l">'+row[i].picktime+'</s>'+
					'</div>'+
					'<div class="rt">\n' +
                     '<a href="'+row[i].pickphone+'"> <img\n' +
                     'src="../../img/stu/icon_res.png" alt="">\n' +
                     '</a>\n' +
                     '</div>\n' +
                     '</li></a>';
                }
                if(hf =='1'){
          			$('#tab1 #shopListAdvancedQuery').empty().append(content);
          		}
                if(hf =='2'){
          			$('#tab2 #shopListAdvancedQuery').empty().append(content);
          		} 
                if(hf =='3'){
          			$('#tab3 #shopListAdvancedQuery').empty().append(content);
          		}
                return;
              
            } else if (JSON.rtnCode == 1002) {
                alert('数据为空');
                return;
            } else  {
                alert('查询失败');
                return;
            }
        });
	}
})