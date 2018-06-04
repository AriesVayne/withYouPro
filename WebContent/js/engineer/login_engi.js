$(function () {
    $('#login_btn').click(function () {
    	var url = '/withyou/doAccount';
		var username = $('#login_name').val();
		var pwd = $('#login_pwd').val();
		postdata = {
			username : username,
			password : pwd,
			act : 'login_engineer'
		};
		$.post(url, postdata, function(result) {
			var JSON = eval('(' + result + ')');
			console.log(JSON);
			if (JSON.rtnCode == 0) {
				alert('登录成功');
				window.location.href = 'html/engineer/index.html';
				// alert(result);
			} else if (JSON.rtnCode == 1001) {
				alert('密码错误');
				return;
			} else if (JSON.rtnCode == 1000) {
				alert('帐号不存在');
				return;
			}
		});

        }
    );
});
