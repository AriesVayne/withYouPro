/**
 * @author: mefisto
 * @description:
 * @Date: 2017/2/24 0024 9:41
 */
$(function(){

    // 默认加载进页面关闭缓冲等待
    $.hideIndicator();

    // tab切换
    $('.bar.bar-tab').click(function () {
        // 正式上线后关闭
        // $.showPreloader();
        // setTimeout(function () {
        //     $.hidePreloader();
        // }, 2000);
    });

    $('.bar-tab .tab-item.external').click(function () {
        $.showIndicator();
        setTimeout(function () {
            $.hideIndicator();
        }, 1000);
    });

    //后退
   $('span.icon-left.pull-left').click(function() {
       // window.history.back();
       location.href=document.referrer;
       //location.reload();
   });

    //picker 点击关闭
    $(document).on('click','.picker-items', function () {
        $('.close-picker').click();
    });

	//手机端运行断网兼容
    $('.external').click(function(){
        // var state = $(this).hasClass('disable');
        // if(state){
        //     return false
        // }
        // $.showPreloader();
        // setTimeout(function () {
        //     $.hidePreloader();
        // }, 2000);
        //
        // try{
        //     top.chkoffline()
        // }
        // catch(e){
        //     //$.alert('函数调用出错!<br><br>'+e);
        // }
    });

    // 小面板
    $('.lte_panel').click(function () {
        var state = $(this).attr('state');
        if(state == 0){
            $('.lte_panel').attr('state',1);
            $('.menu_select').css('z-index',9999);
            $('.menu_select').css('opacity',1);
        }else{
            $('.lte_panel').attr('state',0);
            $('.menu_select').css('z-index',0);
            $('.menu_select').css('opacity',0);
        }
    });

    // 通知链接
     $('.icon.pull-right.header_info').click(function () {
         $.showIndicator();
         window.location.href = '/?ctl=main&mod=main&act=notification';
     });
     $('.newsbadge').click(function () {
         $.showIndicator();
         window.location.href = '/?ctl=main&mod=main&act=notification';
     });

     // 有消息图标才请求消息状态
     if($('.header_info').length == 1){
         checkNotifi();
     }
});

//判断短信是否运行在微信内置浏览器中
function is_weixin(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

function valid(container,min,max,text)
{
    var thisVal = $(container).val();
    var thisLeng = thisVal.length;
    var min = parseInt(min);
    var max = parseInt(max);
    var dispalyMin = min+1;
    if(thisVal==''){
        $.alert('抱歉,'+text+'不应为空,<br>请填写相关内容');
        return false
    }else {
        if(thisLeng <= min){
            $.alert('抱歉,'+text+'最少应'+dispalyMin+'字符');
            return false
        }
        else if(thisLeng > max){
            $.alert('抱歉,'+text+'超出限制长度');
            return false
        }
        else
        {
            return true
        }
    }
};

//向上取整数
function round(value, num){
    return Math.round(value * Math.pow(10, num)) / Math.pow(10, num);
};

//经纬度计算公式
function getFlatternDistance(lat1,lng1,lat2,lng2){
    var PI = Math.PI;
    var EARTH_RADIUS = 6378137.0;//单位M
    function getRad(d)
    {
        return d*PI/180.0;
    }
    var x1 = parseFloat(lat1);
    var x2 = parseFloat(lat2);
    var y1 = parseFloat(lng1);
    var y2 = parseFloat(lng2);
    var radLat1 = parseFloat(getRad(x1));
    var radLat2 = parseFloat(getRad(x2));
    var a = parseFloat(radLat1) - parseFloat(radLat2);
    var b = getRad(y1) - getRad(y2);
    var s = 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
    s = s*EARTH_RADIUS;
    s = Math.round(s)/1000;
    return s;
};

//验证手机号
function checkPhoneComm(container)
{
    var re = /^1[3|4|5|7|8][0-9]\d{8}$/;
    var val =  $(container).val();
    if(val.search(re) == -1) {
        $.alert('手机号格式不正确');
        return false;
    }
    else {
        return true;
    }
};

//校验固话和手机号码的正则
function telAndPhone(container,text) {
    var integer1 = /^(0|86|17951)?(13[0-9]|15[012356789]|17[01678]|18[0-9]|14[57])[0-9]{8}$/;
    var integer2 = /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    if(!integer1.test($(container).val())&&!integer2.test($(container).val())){
        $.alert( text + '电话格式不正确，请输入正确的固定电话或手机号',"提示");
        return false;
    }else{
        return true
    }
}

//转换小数显示
function parseNum(container,type,text)
{
    var thisNum;
    $(container).each(function(){
        $this = $(this);
        if($this.text() == 'null' || $this.text() == ''|| $this.text() == '-' ){
            $this.text(text);
        }
        else
        {
            type == 'int' ?  thisNum = parseInt($this.text()): thisNum = parseFloat($this.text()).toFixed(2);
            $this.text(thisNum);
        }
    });
};


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = location.search.substr(1).match(reg);
    if (r != null)
        return unescape(decodeURI(r[2]));
    else
        return null;
}

function writeTitle() {
      // var url = $('#ComQuery').data('url');//获取
      var url = '/?ctl=ajax&mod=main&act=GetShopUserInfo';//获取
          var postdata = {};
          $.post(url,postdata,function(result){
              var JSON = eval('('+result+')');
              if(JSON.rtnCode == 0 ){
                  $('h1.title s').text(JSON.userInfo.usercode);
              }else{
                  $('h1.title s').text('未登录');
              }
          });
}
// $.showPreloader();

//20171105 mefisto 获取当前日期
function getCurrDay()
{
    var myDate = new Date();
    var year =  myDate.getFullYear();
    var month =  myDate.getMonth()+1;
    var day =  myDate.getDate();
    return [''+year+'-'+month+'-'+day+'']
}

function checkNotifi() {
      var act = getQueryString('act');
      if(act == 'login') return false;
      var url = '/?ctl=ajax&mod=main&act=UnreadNoticeNumQuery';//获取
      var postdata = {
          passwd:localStorage.userpwd
      };
      $.post(url,postdata,function(result){
          var JSON = eval('('+result+')');
          if(JSON == '-990'){
              window.location.href = '/?act=login';
          }else if(JSON != '-993'){
              var rtnCode = JSON.rtnCode;
              if(rtnCode == 0 ){
                  var  length = JSON.rs[0]['COUNT'] > 99 ? 99: JSON.rs[0]['COUNT'];
                  $('.newsbadge').text(length);
              }
          }
      });
}

// 审核状态
function checkState(rtnCode) {
    if( rtnCode == 11003 ){
        $.alert('商家用户未审核，请通过审核后使用本功能！',function () {
            window.location.href = '/';
            return false
        })
    }else{
        return true
    }
}

// 返回错误码
function returnErr(rtnCode) {
    if(rtnCode == 11003) return '商家未通过审核';
}

//建立一個可存取到該file的url
function getObjectURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

// 设置
var setInterTime = 4000;
var smsTextLimit = 64;
