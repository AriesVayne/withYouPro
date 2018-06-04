$(function() {
	var status= ''; //全部
	var content = '';
	var hf='1';
	showDetail();
	$('.buttons-tab #button_1').click(function(){
		status= '';//全部
	});
	$('.buttons-tab #button_2').click(function(){
		status= '1';//待处理
	});
	$('.buttons-tab #button_3').click(function(){
		status= '0';//已经处理
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
		var url = '/withyou/doRepair';
        var postdata = {
        	act:'getRepairList',
            varObject:'stu',
            varStatus:status
        };
        $.post(url,postdata,function(result){
            var JSON = eval('('+result+')');
            console.log(JSON);
            if (JSON.rtnCode == 0) {
            	content = ''
                console.log('获取成功'+hf);
                var row = JSON.rs;
                var rsLength = row.length;
                for(var i=0; i<rsLength; i++){
                	 content += '<a href="repair_detail.html?uuid='+row[i].uuid+'" class="external">'+
                	 '<li><i class="icon_cube red">修</i>\n' +
                     '<div class="lt">\n' +
                     '<h5>'+row[i].device+'</h5>\n' +
                     '<s>'+row[i].repairdesc+'</s>\n' +
                     '</div>\n' +
                     '<div class="lt">'+
						'<!-- <h5>2017</h5> -->'+
						'<s id="time_l">'+row[i].uploadtime+'</s>'+
					'</div>'+
					'<div class="rt">\n' +
                     '<a href="'+row[i].connphone+'"> <img\n' +
                     'src="../../img/stu/repair/icon_repair.png" alt="">\n' +
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