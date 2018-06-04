$(function() {
    console.log("class_info");
	var uuid = getQueryString('uuid');
	console.log(uuid);
	showInfoList();
	$('#infoadd').click(function() {
        console.log(uuid);
		window.location.href = "class_infoadd.html?classid="+uuid;
    });
	$('.content ul li').click(function() {
		console.log("123");
	});
	$('.content ul li').on("click",function(){
		console.log("asdfsd");
		console.log($(this).find('#content_desc').text());
    });

	function showInfoList(){
		var url ='/withyou/doClass';
		var postdata={
			varClassId:uuid,
            userType:'teacher',
			act:'getClassInfoList'
		};
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')');
			var item = '';
			if(JSON.rtnCode == 0){
                for(var i=0;i< JSON.rs.length; i++){
                	var row = JSON.rs[i];
                	var time = '';
                	if(row.time!=null){
                		time = row.time.substring(0, 10);
                	}else{
                		time = '';
                	}
                		
                    item += ' <a href="class_infoadd.html?messid='+row.uuid+'"><li>\n' +
                    '            <div class="left_img">\n' +
                    '                <img src="../../img/teacher/icon_mess.png">\n' +
                    '            </div>\n' +
                    '            <div class="mid_content">\n' +
                    '                <span>'+row.title+'</span>\n' +
                    '            </div>\n' +
                    '            <div class="right_time">\n' +
                    '                <span>'+time+'</span>\n' +
                    '            </div>\n' +
                    '   <div id="content_desc" style="display:none">'+row.content+'</div>     ' +
					'</li></a>';
                }
                $('.content ul').empty().append(item);
            }else{
                alert('数据获取失败');	
			}
		});
	}
})