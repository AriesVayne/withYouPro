$(function () {
    $('#regis_btn').click(function () {
    	var userName = $('#login_name').val();
    	var realName = $('#real_name').val();
    	var passWord = $('#login_pwd').val();
    	var conPwd = $('#login_conpwd').val();
    	console.log("userName:"+userName+"--passWord:"+userName+"--conPwd:"+conPwd);
    	var url = '/withyou/doAccount';
    	var postdata = {
    			username:userName,
    			realname:realName,
    			password:userName,
    			act:'reg_engineer'
    	};
    	if(userName==""||realName==""||passWord==""||conPwd==""){
    		alert('请认真填写');
    		return;
    	}
    	$.post(url, postdata, function(result) {
    		var JSON = eval('('+result+')');
    		if (JSON.rtnCode == 0) {
				alert('注册成功');
				window.location.href = 'login_engi.html';
			} else if (JSON.rtnCode == 10002) {
				alert('帐号已经存在');
				return;
			} else if (JSON.rtnCode == 10003) {
				alert('注册失败');
				return;
			}
    	});
        }
    );
});
