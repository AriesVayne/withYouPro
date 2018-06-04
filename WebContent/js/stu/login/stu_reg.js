$(function() {

	console.log('stu_reg');
	
	$('.mb2 .regis').click(function() {
		console.log('regis');
		var url = '/withyou/doAccount';
		var username = $('#username').val();
		var pwd = $('#password').val();
		var realname = $('#realname').val();
		var grade = $('#grade').val();
		var college = $('#college').val();
		var major = $('#major').val();
		var dormitory = $('#dormitory').val();
//		console.log("username:"+username+"pwd:"+pwd+"realname:"+realname+"grade:"+grade+"college:"+college+"major:"+major+"dormitory:"+dormitory);
		postdata = {
			username : username,
			password : pwd,
			realname : realname,
			grade : grade,
			college : college,
			major : major,
			dormitory : dormitory,
			act : 'regis_stu'
		};
		$.post(url, postdata, function(result) {
			var JSON = eval('(' + result + ')');
			console.log(JSON);
			if (JSON.rtnCode == 0) {
				alert('注册成功');
				window.location.href = 'login_stu.html';
				// alert(result);
			} else if (JSON.rtnCode == 10002) {
				alert('帐号已经存在');
				return;
			} else if (JSON.rtnCode == 10003) {
				alert('注册失败');
				return;
			}
		});

	});
})