$(function() {
    console.log("class_filelist");
    var uuid = getQueryString('messid');
    var classid = getQueryString('uuid');
    $('#fileAdd').click(function(){
    	window.location.href='class_assignadd.html?classid='+classid;
    });
    showFileList();
    
    function showFileList(){
    	console.log("showFileList");
    	var url ='/withyou/doClass';
    	var items = '';
    	var postdata={
    			varClassId:classid,
    			userType:'teacher',
    			act:'getAssiList'
    	};
    	$.post(url,postdata,function(result){
    		var JSON = eval('('+result+')');
            console.log(JSON);
            if(JSON.rs.length == 0){
            	items +=
                    '<li>\n' +
                    '<div class="left_img">\n' +
                    '</div>\n' +
                    '<div class="mid_content">\n' +
                    '\t<span>当前课程通知消息为空哦</span>\n' +
                    '</div>\n' +
                    '<div class="right_time">\n' +
                    '\t<span></span>\n' +
                    '</div>\n' +
                    '</li>\n';
            }
            for(var i=0; i <JSON.rs.length;i++){
            	var row = JSON.rs[i];
            	  if(row.time!=null){
                      time = row.time.substring(0, 10);
                  }else{
                      time = '';
                  }
                items +='<a href="class_assignadd.html?assingid='+row.assid+'">\n' +
                    '<li>\n' +
                    '<div class="left_img">\n' +
                    '\t<img src="../../img/teacher/class_assignment.png">\n' +
                    '</div>\n' +
                    '<div class="mid_content">\n' +
                    '\t<span>'+row.title+'</span>\n' +
                    '</div>\n' +
                    '<div class="right_time">\n' +
                    '\t<span>'+time+'</span>\n' +
                    '</div>\n' +
                    '</li>\n' +
                    '</a>';
			}
            console.log(items);
			$('.content ul').empty().append(items);

    	});
    }

});