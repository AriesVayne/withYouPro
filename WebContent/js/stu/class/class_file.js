$(function() {
    console.log("class_filelist");
    var uuid = getQueryString('messid');
    var classid = getQueryString('uuid');
    $('#fileAdd').click(function(){
    	window.location.href='class_fileupload.html?calssid='+classid;
    });
    showFileList();
    
    function showFileList(){
    	console.log("showFileList");
    	var url ='/withyou/doClass';
    	var postdata={
    			varClassId:classid,
    			act:'getClassFilePathList'
    	};
    	$.post(url,postdata,function(result){
    		var JSON = eval('('+result+')');
            console.log(JSON);
            var items = '';
            if(JSON.rs.length == 0||JSON.rs.length == 20002){
            	items +='<li >\n' +
                '<div class="left_img">\n' +
                '</div>\n' +
                '<div class="mid_content" style="overflow: hidden;">\n' +
                '<span>当前课程文件为空哦</span>\n' +
                '</div>\n' +
                '<div class="right_time">\n' +
                '<span></span>\n' +
                '</div>\n' +
                '</li>\n';
            }
            for(var i=0; i <JSON.rs.length;i++){
            	var row = JSON.rs[i];
					items += '<a href="../../'+row.filepath+'">\n' +
                    '<li >\n' +
                    '<div class="left_img">\n' +
                    '<img src="../../img/teacher/class_resources.png">\n' +
                    '</div>\n' +
                    '<div class="mid_content" style="overflow: hidden;">\n' +
                    '<span>'+row.filename+'</span>\n' +
                    '</div>\n' +
                    '<div class="right_time">\n' +
                    '<span>'+row.length+'</span>\n' +
                    '</div>\n' +
                    '</li>\n' +
                    '</a>';
			}
			$('.content ul').empty().append(items);

    	});
    }

});