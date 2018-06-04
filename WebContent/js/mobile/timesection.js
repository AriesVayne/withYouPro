/**
 * @author: mefisto
 * @description:
 * @Date: 2017/12/18 0018 20:40
 */
function timesection(start_btn,end_btn,sub_btn) {
    //时间选择器
    var MaxDay = getCurrDay();
    $(start_btn).calendar({
        maxDate: MaxDay,
        inputReadOnly: true,
    });

    $(end_btn).calendar({
        // maxDate: MaxDay,
        inputReadOnly: true,
    });

    $(sub_btn).click(function () {
        var start = $(start_btn).val();
        var end = $(end_btn).val();
        if( start > end){
            $.alert('开始时间不能大于结束时间',function () {
                $(start_btn).val(MaxDay);
                $(end_btn).val(MaxDay);
            });
            return false
        }
    });

}

function StringToDate(s) {
    var d = new Date();
    d.setYear(parseInt(s.substring(0,4),10));
    d.setMonth(parseInt(s.substring(5,7)-1,10));
    d.setDate(parseInt(s.substring(8,10),10));
    return d;
}

