/**
 * 通用方法封装处理
 * Copyright (c) 2019 ruoyi 
 */
$(function() {
	
    //  layer扩展皮肤
    if (window.layer !== undefined) {
        layer.config({
            extend: 'moon/style.css',
            skin: 'layer-ext-moon'
        });
    }
	
    // 回到顶部绑定
    if ($.fn.toTop !== undefined) {
        $('#scroll-up').toTop();
    }
	
    // select2复选框事件绑定
    if ($.fn.select2 !== undefined) {
        $.fn.select2.defaults.set( "theme", "bootstrap" );
        $("select.form-control:not(.noselect2)").each(function () {
            $(this).select2().on("change", function () {
                $(this).valid();
            })
        })
    }
	
    // iCheck单选框及复选框事件绑定
    if ($.fn.iCheck !== undefined) {
        $(".check-box:not(.noicheck),.radio-box:not(.noicheck)").each(function() {
            $(this).iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
            })
        })
    }
	
    // 取消回车自动提交表单
    $(document).on("keypress", ":input:not(textarea):not([type=submit])", function(event) {
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    });
	 
    // laydate 时间控件绑定
    if ($(".select-time").length > 0) {
       layui.use('laydate', function() {
            var laydate = layui.laydate;
            var startDate = laydate.render({
                elem: '#startTime',
                max: $('#endTime').val(),
                theme: 'molv',
                trigger: 'click',
                done: function(value, date) {
                    // 结束时间大于开始时间
                    if (value !== '') {
                        endDate.config.min.year = date.year;
                        endDate.config.min.month = date.month - 1;
                        endDate.config.min.date = date.date;
                    } else {
                        endDate.config.min.year = '';
                        endDate.config.min.month = '';
                        endDate.config.min.date = '';
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#endTime',
                min: $('#startTime').val(),
                theme: 'molv',
                trigger: 'click',
                done: function(value, date) {
                    // 开始时间小于结束时间
                    if (value !== '') {
                        startDate.config.max.year = date.year;
                        startDate.config.max.month = date.month - 1;
                        startDate.config.max.date = date.date;
                    } else {
                        startDate.config.max.year = '2099';
                        startDate.config.max.month = '12';
                        startDate.config.max.date = '31';
                    }
                }
            });
        });
    }
	
    // laydate time-input 时间控件绑定
    if ($(".time-input").length > 0) {
        layui.use('laydate', function () {
            var com = layui.laydate;
            $(".time-input").each(function (index, item) {
                var time = $(item);
                // 控制控件外观
                var type = time.attr("data-type") || 'date';
                // 控制回显格式
                var format = time.attr("data-format") || 'yyyy-MM-dd';
                // 控制日期控件按钮
                var buttons = time.attr("data-btn") || 'clear|now|confirm', newBtnArr = [];
                // 日期控件选择完成后回调处理
                var callback = time.attr("data-callback") || {};
                if (buttons) {
                    if (buttons.indexOf("|") > 0) {
                        var btnArr = buttons.split("|"), btnLen = btnArr.length;
                        for (var j = 0; j < btnLen; j++) {
                            if ("clear" === btnArr[j] || "now" === btnArr[j] || "confirm" === btnArr[j]) {
                                newBtnArr.push(btnArr[j]);
                            }
                        }
                    } else {
                        if ("clear" === buttons || "now" === buttons || "confirm" === buttons) {
                            newBtnArr.push(buttons);
                        }
                    }
                } else {
                    newBtnArr = ['clear', 'now', 'confirm'];
                }
                com.render({
                    elem: item,
                    theme: 'molv',
                    trigger: 'click',
                    type: type,
                    format: format,
                    btns: newBtnArr,
                    done: function (value, data) {
                        if (typeof window[callback] != 'undefined'
                            && window[callback] instanceof Function) {
                            window[callback](value, data);
                        }
                    }
                });
            });
        });
    }
	
    // tree 关键字搜索绑定
    if ($("#keyword").length > 0) {
        $("#keyword").bind("focus", function focusKey(e) {
            if ($("#keyword").hasClass("empty")) {
                $("#keyword").removeClass("empty");
            }
        }).bind("blur", function blurKey(e) {
            if ($("#keyword").val() === "") {
                $("#keyword").addClass("empty");
            }
            $.tree.searchNode(e);
        }).bind("input propertychange", $.tree.searchNode);
    }
	
    // tree表格树 展开/折叠
    var expandFlag;
    $("#expandAllBtn").click(function() {
        var dataExpand = $.common.isEmpty(table.options.expandAll) ? true : table.options.expandAll;
        expandFlag = $.common.isEmpty(expandFlag) ? dataExpand : expandFlag;
        if (!expandFlag) {
            $.bttTable.bootstrapTreeTable('expandAll');
        } else {
            $.bttTable.bootstrapTreeTable('collapseAll');
        }
        expandFlag = expandFlag ? false: true;
    })
	
    // 按下ESC按钮关闭弹层
    $('body', document).on('keyup', function(e) {
        if (e.which === 27) {
            $.modal.closeAll();
        }
    });
});

(function ($) {
    'use strict';
    $.fn.toTop = function(opt) {
        var elem = this;
        var win = (opt && opt.hasOwnProperty('win')) ? opt.win : $(window);
        var doc = (opt && opt.hasOwnProperty('doc')) ? opt.doc : $('html, body');
        var options = $.extend({
            autohide: true,
            offset: 50,
            speed: 500,
            position: true,
            right: 15,
            bottom: 5
        }, opt);
        elem.css({
            'cursor': 'pointer'
        });
        if (options.autohide) {
            elem.css('display', 'none');
        }
        if (options.position) {
            elem.css({
                'position': 'fixed',
                'right': options.right,
                'bottom': options.bottom,
            });
        }
        elem.click(function() {
            doc.animate({
                scrollTop: 0
            }, options.speed);
        });
        win.scroll(function() {
            var scrolling = win.scrollTop();
            if (options.autohide) {
                if (scrolling > options.offset) {
                    elem.fadeIn(options.speed);
                } else elem.fadeOut(options.speed);
            }
        });
    };
})(jQuery);

/** 刷新选项卡 */
var refreshItem = function(){
    var topWindow = $(window.parent.document);
    var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-id');
    var target = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow);
    var url = target.attr('src');
    target.attr('src', url).ready();
}

/** 关闭选项卡 */
var closeItem = function(dataId){
	var topWindow = $(window.parent.document);
	if($.common.isNotEmpty(dataId)){
	    window.parent.$.modal.closeLoading();
	    // 根据dataId关闭指定选项卡
	    $('.menuTab[data-id="' + dataId + '"]', topWindow).remove();
	    // 移除相应tab对应的内容区
	    $('.mainContent .RuoYi_iframe[data-id="' + dataId + '"]', topWindow).remove();
	    return;
	}
	var panelUrl = window.frameElement.getAttribute('data-panel');
	$('.page-tabs-content .active i', topWindow).click();
	if($.common.isNotEmpty(panelUrl)){
	    $('.menuTab[data-id="' + panelUrl + '"]', topWindow).addClass('active').siblings('.menuTab').removeClass('active');
	    $('.mainContent .RuoYi_iframe', topWindow).each(function() {
	        if ($(this).data('id') == panelUrl) {
	            $(this).show().siblings('.RuoYi_iframe').hide();
	            return false;
            }
        });
    }
}

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName, isRefresh) {
    var panelUrl = window.frameElement.getAttribute('data-id');
    dataIndex = $.common.random(1, 100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                scrollToTab(this);
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .RuoYi_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.RuoYi_iframe').hide();
                        return false;
                    }
                });
            }
            if (isRefresh) {
            	refreshTab();
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab noactive" data-id="' + dataUrl + '" data-panel="' + panelUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" data-panel="' + panelUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(str1);
        
        window.parent.$.modal.loading("数据加载中，请稍后...");
        $('.mainContent iframe:visible', topWindow).load(function () {
            window.parent.$.modal.closeLoading();
        });

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
        scrollToTab($('.menuTab.active', topWindow));
    }
    return false;
}

// 刷新iframe
function refreshTab() {
	var topWindow = $(window.parent.document);
	var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-id');
	var target = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow);
    var url = target.attr('src');
	target.attr('src', url).ready();
}

// 滚动到指定选项卡
function scrollToTab(element) {
    var topWindow = $(window.parent.document);
    var marginLeftVal = calSumWidth($(element).prevAll()),
    marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs", topWindow).children().not(".menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs", topWindow).outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content", topWindow).outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content", topWindow).outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content', topWindow).animate({ marginLeft: 0 - scrollVal + 'px' }, "fast");
}

//计算元素集合的总宽度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function() {
        width += $(this).outerWidth(true);
    });
    return width;
}

/** 密码规则范围验证 */
function checkpwd(chrtype, password) {
    if (chrtype == 1) {
        if(!$.common.numValid(password)){
            $.modal.alertWarning("密码只能为0-9数字");
            return false;
        }
    } else if (chrtype == 2) {
        if(!$.common.enValid(password)){
            $.modal.alertWarning("密码只能为a-z和A-Z字母");
            return false;
        }
    } else if (chrtype == 3) {
        if(!$.common.enNumValid(password)){
            $.modal.alertWarning("密码必须包含字母以及数字");
            return false;
        }
    } else if (chrtype == 4) {
        if(!$.common.charValid(password)){
            $.modal.alertWarning("密码必须包含字母、数字、以及特殊符号<font color='red'>~!@#$%^&*()-=_+</font>");
            return false;
        }
    }
    return true;
}

// 日志打印封装处理
var log = {
    log: function(msg) {
        console.log(msg);
    },
    info: function(msg) {
        console.info(msg);
    },
    warn: function(msg) {
        console.warn(msg);
    },
    error: function(msg) {
        console.error(msg);
    }
};

// 本地缓存处理
var storage = {
    set: function(key, value) {
        window.localStorage.setItem(key, value);
    },
    get: function(key) {
        return window.localStorage.getItem(key);
    },
    remove: function(key) {
        window.localStorage.removeItem(key);
    },
    clear: function() {
        window.localStorage.clear();
    }
};

// 主子表操作封装处理
var sub = {
    editColumn: function() {
    	var dataColumns = [];
		for (var columnIndex = 0; columnIndex < table.options.columns.length; columnIndex++) {
    		if (table.options.columns[columnIndex].visible != false) {
    			dataColumns.push(table.options.columns[columnIndex]);
    		}
    	}
		var params = new Array();
		var data = $("#" + table.options.id).bootstrapTable('getData');
    	var count = data.length;
    	for (var dataIndex = 0; dataIndex < count; dataIndex++) {
    	    var columns = $('#' + table.options.id + ' tr[data-index="' + dataIndex + '"] td');
    	    var obj = new Object();
    	    for (var i = 0; i < columns.length; i++) {
    	        var inputValue = $(columns[i]).find('input');
    	        var selectValue = $(columns[i]).find('select');
    	        var textareaValue = $(columns[i]).find('textarea');
    	        var key = dataColumns[i].field;
    	        if ($.common.isNotEmpty(inputValue.val())) {
    	            obj[key] = inputValue.val();
    	        } else if ($.common.isNotEmpty(selectValue.val())) {
    	            obj[key] = selectValue.val();
    	        } else if ($.common.isNotEmpty(textareaValue.val())) {
    	            obj[key] = textareaValue.val();
    	        } else {
    	            obj[key] = "";
    	        }
    	    }
    	    var item = data[dataIndex];
    	    var extendObj = $.extend({}, item, obj);
    	    params.push({ index: dataIndex, row: extendObj });
    	}
    	$("#" + table.options.id).bootstrapTable("updateRow", params);
    },
    delColumn: function(column) {
    	sub.editColumn();
    	var subColumn = $.common.isEmpty(column) ? "index" : column;
    	var ids = $.table.selectColumns(subColumn);
        if (ids.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $("#" + table.options.id).bootstrapTable('remove', { field: subColumn, values: ids });
    },
    addColumn: function(row, tableId) {
    	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
    	table.set(currentId);
    	var count = $("#" + currentId).bootstrapTable('getData').length;
    	sub.editColumn();
    	$("#" + currentId).bootstrapTable('insertRow', {
            index: count + 1,
            row: row
        });
    }
};

// 动态加载css文件
function loadCss(file, headElem) {
    var link = document.createElement('link');
    link.href = file;
    link.rel = 'stylesheet';
    link.type = 'text/css';
    if (headElem) headElem.appendChild(link);
    else document.getElementsByTagName('head')[0].appendChild(link);
}

// 动态加载js文件
function loadJs(file, headElem) {
    var script = document.createElement('script');
    script.src = file;
    script.type = 'text/javascript';
    if (headElem) headElem.appendChild(script);
    else document.getElementsByTagName('head')[0].appendChild(script);
}

/** 设置全局ajax处理 */
$.ajaxSetup({
    complete: function(XMLHttpRequest, textStatus) {
        if (textStatus == 'timeout') {
            $.modal.alertWarning("服务器超时，请稍后再试！");
            $.modal.enable();
            $.modal.closeLoading();
        } else if (textStatus == "parsererror" || textStatus == "error") {
            $.modal.alertWarning("服务器错误，请联系管理员！");
            $.modal.enable();
            $.modal.closeLoading();
        }
    }
});
