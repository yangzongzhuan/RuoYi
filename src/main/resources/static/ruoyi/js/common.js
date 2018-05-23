/**
 * 通用方法封装处理
 * Copyright (c) 2018 ruoyi 
 */

/** 消息状态码 */
web_status = {
    SUCCESS: 0,
    FAIL: 500
};

/** 弹窗状态码 */
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};

/** 弹出层指定宽度 */
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

/** 弹出层自动宽高 */
function layer_showAuto(title, url) {
	layer_show(title, url, '', '');
}

/** 关闭弹出框口 */
function layer_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/** 对ajax的post方法再次封装 */
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

/** 对jquery的ajax方法再次封装 */
_ajax = function(url, data, type) {
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
		$.modalMsg(result.msg, modal_status.SUCCESS);
		$.refreshTable();
    } else {
    	$.modalAlert(result.msg, modal_status.FAIL);
    }
}

/** 操作结果处理 */
function handleSuccess(result) {
    if (result.code == web_status.SUCCESS) {
    	parent.layer.msg("新增成功,正在刷新数据请稍后……",{icon:1,time: 500,shade: [0.1,'#fff']},function(){
			$.parentReload();
		});
    } else {
    	$.modalAlert(result.msg, modal_status.FAIL);
    }
}

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

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
    dataIndex = Math.floor(Math.random()*100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .RuoYi_iframe', topWindow).each(function() {
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
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

/** 设置全局ajax超时处理 */
$.ajaxSetup({
    complete: function(XMLHttpRequest, textStatus) {
        if (textStatus == "parsererror") {
        	$.modalConfirm("登陆超时！请重新登陆！", function() {
        		window.location.href = ctx + "login";
        	})
        } else if (textStatus == "error") {
        	$.modalAlert("请求超时！请稍后再试！", 'warning');
        }
    }
}); 
