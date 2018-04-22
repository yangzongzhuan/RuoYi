/**
 * 通用方法封装处理
 * Copyright (c) 2018 ruoyi 
 */
/*
	参数解释：
	title	标题
	url		请求的url
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function layer_show(title, url, w, h) {
    if (title == null || title == '') {
        title = false;
    };
    if (url == null || url == '') {
        url = "404.html";
    };
    if (w == null || w == '') {
        w = 800;
    };
    if (h == null || h == '') {
        h = ($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false,
        //不固定
        maxmin: true,
        shade: 0.4,
        title: title,
        content: url
    });
}

function layer_showAuto(title, url) {
	layer_show(title, url, '', '');
}

/*关闭弹出框口*/
function layer_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

//状态码
web_status = {
    SUCCESS: 0,
    FAIL: 500
};

//对ajax的post方法再次封装
_ajax_save = function(url, data) {
    var config = {
        url: url,
        type: "post",
        dataType: "json",
        data: data,
        success: function(result) {
        	handleSuccess(result);
        }
    };
    $.ajax(config)
};

//对jquery的ajax方法再次封装
_ajax = function(url, data, type, r) {
	if (!r) {
		return;
	}
    var config = {
        url: url,
        type: type,
        dataType: "json",
        data: data,
        success: function(result) {
            simpleSuccess(result);
        }
    };
    $.ajax(config)
};

/** 返回结果处理 */
function simpleSuccess(result) {
    if (result.code == web_status.SUCCESS) {
		$.modalMsg(result.msg, "success");
		$.refreshTable();
    } else {
    	$.modalAlert(result.msg, "error");
    }
}

/** 操作结果处理 */
function handleSuccess(result) {
    if (result.code == web_status.SUCCESS) {
    	parent.layer.msg("新增成功,正在刷新数据请稍后……",{icon:1,time: 500,shade: [0.1,'#fff']},function(){
			$.parentReload();
		});
    } else {
    	$.modalAlert(result.msg, "error");
    }
}

$(document).ready(function() {  
    $(".onoffswitch-checkbox").on('click', function(){  
        clickSwitch()  
    });  
  
    var clickSwitch = function() {  
        if ($(".onoffswitch-checkbox").is(':checked')) {  
            alert("在ON的状态下");
        } else {  
        	alert("在OFF的状态下");
        }  
    };  
});  

/** 时间格式化 */
function formatDate(_date, _pattern) {
	var date = new Date(_date);
	var newDate = date.format(_pattern);
	return newDate;
}

Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k]
					: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}

// 创建选项卡
function createMenuItem(dataUrl, menuName) {
    dataIndex = Math.floor(Math.random()*100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;

    // 选项卡菜单已存在
    $('.menuTab', window.parent.document).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                scrollToTab(this);
                // 显示tab对应的内容区
                $('.mainContent .RuoYi_iframe', window.parent.document).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.RuoYi_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', window.parent.document).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', window.parent.document).find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', window.parent.document).append(str);
        scrollToTab($('.menuTab.active'));
    }
    return false;
}
