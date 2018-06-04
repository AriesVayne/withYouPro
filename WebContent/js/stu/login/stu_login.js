$(function() {
	console.log('stu_login');
	
	$('.register').click(function() {
		window.location.href="reg_stu.html";
	});

	$('.login').click(function() {
		var url = '/withyou/doAccount';
		var username = $('#username').val();
		var pwd = $('#password').val();
		postdata = {
			username : username,
			password : pwd,
			act : 'login_stu'
		};
		$.post(url, postdata, function(result) {
			var JSON = eval('(' + result + ')');
			console.log(JSON);
			if (JSON.rtnCode == 0) {
				alert('登录成功');
				window.location.href = 'html/stu/index.html';
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
})