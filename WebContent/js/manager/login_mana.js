$(function() {
	$('.login-w3 .login').click(function() {
		var url = '/withyou/doAccount';
		var username = $('#username').val();
		var password = $('#password').val();
		console.log("username："+username+"----password："+password);
		var postdata={
				username : username,
				password : password,
				act : 'login_manager'
		};
		$.post(url, postdata, function(result) {
			var JSON = eval('(' + result + ')');
			console.log(JSON);
			if (JSON.rtnCode == 0) {
				alert('登录成功');
				window.location.href = 'html/manager/index.html';
				// alert(result);
			} else if (JSON.rtnCode == 1001) {
				alert('密码错误');
				return;
			} else if (JSON.rtnCode == 1000) {
				alert('帐号不存在');
				return;
			}
		});
	});
});
