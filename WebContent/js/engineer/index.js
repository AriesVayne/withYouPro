$(function () {
	
	getSnapInfo();
    $('.main_panel span').click(function () {
        var header = $('header').clone();
        var index = $(this).index();
        $('.pop_style .pop_header').empty().append(header);
        $('.pop_style .filter_panel span').removeClass('active');
        $('.pop_style .filter_panel span').eq(index).addClass('active');
        $('.pop_style .filter_panel span i').removeClass('icon-up').addClass('icon-down');
        $('.pop_style .filter_panel span.active i').removeClass('icon-down').addClass('icon-up');
        $('.filter_container .tabs').hide();
        $('.filter_container .tabs').eq(index).show();
        $.popup('.pop_style');
    });

    $('.filter_container ul li').live("click",function () {
        $(this).parent().find('li').removeClass('active');
        $(this).addClass('active');
        $.closeModal('.pop_style');
        showDetail();
    });

    $('.pop_style .filter_panel span').click(function () {
        var index = $(this).index();
        $('.pop_style .filter_panel span').removeClass('active');
        $(this).addClass('active');
        $('.filter_container .tabs').hide();
        $('.filter_container .tabs').eq(index).show();
        showDetail();
    });

    // 关闭弹窗
    $('.pop_style .icon.icon-left.pull-left').live('click',function () {
        $.closeModal('.pop_style');
    });

    // 时间绑定
    timesection('#balance_start','#balance_end','#main_page_time .btn_time');

    // 时间重置
    $('.reset_btn').click(function () {
    	var myDate = new Date(); 
    	$('.sel_container #balance_start').val(myDate.toLocaleDateString().toString().replace(/\//g,'-'));
    	$('.sel_container #balance_end').val(myDate.toLocaleDateString().toString().replace(/\//g,'-'));
    });
    
    //不限制时间
    $('.clear_btn').click(function () {
        $('#balance_start').val('');
        $('#balance_end').val('');
        $('.list_title i').text('');
    });

    // 时间确定
    $('.confirm_time_btn').click(function () {
        $.closeModal('.pop_style');
        if($('.sel_container #balance_start').val()==''&&$('.sel_container #balance_end').val()==''){
            $('.list_title i').text();
        }
        if($('.sel_container #balance_start').val()==''&&$('.sel_container #balance_end').val()!=''){
            $('.list_title i').text( "到 "+$('#balance_end').val()+" 为止");
        }
        if($('.sel_container #balance_start').val()!=''&&$('.sel_container #balance_end').val()==''){
            $('.list_title i').text( "从 "+$('#balance_start').val()+" 起");
        }
        if($('.sel_container #balance_start').val()!=''&&$('.sel_container #balance_end').val()!=''){
            $('.list_title i').text( $('#balance_start').val()+" 至 "+$('#balance_end').val());
        }
        showDetail();
    });
});
function getSnapInfo(){
	var url = '/withyou/doRepair';
	var postdata ={
			act:'engiSnapInfo'
	};
	$.post(url,postdata,function (result){
		var JSON = eval('('+result+')');
		console.log(JSON);
		if(JSON.rtnCode == 0){
			var row =JSON.rs;
			$('#work_num').text(row[0].all+'单');
			$('#done_num').text(row[0].done);
			$('#undone_num').text(row[0].undone);
		}
		showDetail();
	});
}
function showDetail(){
	var url = '/withyou/doRepair';
	var status = '';
	var startdata=$('.sel_container #balance_start').val();
	var enddata=$('.sel_container #balance_end').val();
	switch ($('.filter_container .tab1').find('.active').html()) {
		case '全部状态':
			break;
		case '已处理':
			status = '0';
			break;
		case '待处理':
			status = '1';
			break;
		case '已驳回':
			status = '2';
			break;
	}
    var postdata = {
    	act:'getRepairList',
        varObject:'engineer'
    };
    if(startdata){
        postdata.BDT=startdata;
    }
    if(enddata){
        postdata.EDT=enddata;
    }
    if(status){
    	postdata.varStatus=status;
    }
    console.log(postdata);
    $.post(url,postdata,function(result){
        var JSON = eval('('+result+')');
        console.log(JSON);
        if (JSON.rtnCode == 0) {
        	content = ''
            console.log('获取成功');
            var row = JSON.rs;
            var rsLength = row.length;
            for(var i=0; i<rsLength; i++){
            	 content += '<a href="detail.html?uuid='+row[i].uuid+'" class="external">'+
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
                 'src="../../img/engineer/icon_repair.png" alt="">\n' +
                 '</a>\n' +
                 '</div>\n' +
                 '</li></a>';
            }
      		$('.list_container #shopListAdvancedQuery').empty().append(content);
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