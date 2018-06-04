$(function() {
	showLesson();
	// $('.register').click(function() {
	// window.location.href="reg_stu.html";
	// });

	$('#class_add').click(function() {
		window.location.href = "class_lessonadd.html";
	});
	function showLesson() {
		console.log("showlesson");
		var url = '/withyou/doClass';
		var postdata = {
			url : url,
			userType : 'teacher',
			act : 'getLessons'
		};
		$.post(url,postdata,function(result){
			var JSON = eval('('+result+')');
			console.log(JSON);
			if(JSON.rtnCode == 0 ){
				var item = '';
				var length =JSON.rs.length;
				for(var i=0;i<length;i++){
					var row = JSON.rs[i];
					console.log();
					item+='<a href="class_navi.html?uuid='+row.uuid+'">' +
						'<li>\n' +
                        '            <div class="left_pic">\n' +
                        '                <img src="../../FileUpload/classOnline/'+row.imgpath+'">\n' +
                        '            </div>\n' +
                        '            <div class="right_info">\n' +
                        '                <h5>'+row.classname+'</h5>\n' +
                        '                <h4>'+row.teachername+'</h4>\n' +
                        '                <h3>'+row.major+'</h3>\n' +
                        '            </div>\n' +
                        '        </li></a>';
					$('.content .classcontent').empty().append(item);
				}
			}else{
				console.log('获取数据失败');
				
			}
		});
	}
})