/**
 * 通用方法封装处理
 * Copyright (c) 2018 ruoyi
 */
(function ($) {
    $.extend({
    	// 表格封装处理
    	table: {
            _option: {},
            _params: {},
            // 初始化表格
            init: function(options) {
                $.table._option = options;
                $.table._params = $.common.isEmpty(options.queryParams) ? $.table.queryParams : options.queryParams;
                _sortOrder = $.common.isEmpty(options.sortOrder) ? "asc" : options.sortOrder;
                _sortName = $.common.isEmpty(options.sortName) ? "" : options.sortName;
                $('#bootstrap-table').bootstrapTable({
                    url: options.url,                                   // 请求后台的URL（*）
                    contentType: "application/x-www-form-urlencoded",   // 编码类型
                    method: 'post',                                     // 请求方式（*）
                    cache: false,                                       // 是否使用缓存
                    sortable: true,                                     // 是否启用排序
                    sortStable: true,                                   // 设置为 true 将获得稳定的排序
                    sortName: _sortName,                                // 排序列名称
                    sortOrder: _sortOrder,                              // 排序方式  asc 或者 desc
                    pagination: true,                                   // 是否显示分页（*）
                    pageNumber: 1,                                      // 初始化加载第一页，默认第一页
                    pageSize: 10,                                       // 每页的记录行数（*） 
                    pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
                    iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
        	        toolbar: '#toolbar',                                // 指定工作栏
                    sidePagination: "server",                           // 启用服务端分页 
                    search: $.common.visible(options.search),           // 是否显示搜索框功能
                    showRefresh: $.common.visible(options.showRefresh), // 是否显示刷新按钮
        			showColumns: $.common.visible(options.showColumns), // 是否显示隐藏某列下拉框
        			showToggle: $.common.visible(options.showToggle),   // 是否显示详细视图和列表视图的切换按钮
        			showExport: $.common.visible(options.showExport),   // 是否支持导出文件
                    queryParams: $.table._params,                       // 传递参数（*）
                    columns: options.columns                            // 显示列信息（*）
                });
            },
            // 查询条件
            queryParams: function(params) {
            	return {
        			// 传递参数查询参数
        			pageSize:       params.limit,
        			pageNum:        params.offset / params.limit + 1,
        			searchValue:    params.search,
        			orderByColumn:  params.sort,
        			isAsc:          params.order
        		}; 
            },
            // 搜索
            search: function(form) {
    		    var params = $("#bootstrap-table").bootstrapTable('getOptions');
    		    params.queryParams = function(params) {
    		        var search = {};
    		        $.each($("#" + form).serializeArray(), function(i, field) {
    		            search[field.name] = field.value;
    		        });
    		        search.pageSize = params.limit;
    		        search.pageNum = params.offset / params.limit + 1;
    		        search.searchValue = params.search;
    		        search.orderByColumn = params.sort;
    		        search.isAsc = params.order;
    		        return search;
    		    }
    		    $("#bootstrap-table").bootstrapTable('refresh', params);
    		},
    		// 下载
    		exportExcel: function(form) {
    			$.post($.table._option.exportUrl, $("#" + form).serializeArray(), function(result) {
    				if (result.code == web_status.SUCCESS) {
    			        window.location.href = ctx + "common/download?fileName=" + result.msg + "&delete=" + true;
    				} else {
    					$.modal.alertError(result.msg);
    				}
    			});
    		},
            // 刷新
            refresh: function() {
                $("#bootstrap-table").bootstrapTable('refresh', {
                    url: $.table._option.url,
                    silent: true
                });
            },
            // 查询选中列值
            selectColumns: function(column) {
            	return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
        	        return row[column];
        	    });
            },
            // 查询选中首列值
            selectFirstColumns: function() {
            	return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
        	        return row[$.table._option.columns[1].field];
        	    });
            }
        },
        // 表格树封装处理
        treeTable: {
            _option: {},
            _treeTable: {},
            // 初始化表格
            init: function(options) {
                $.table._option = options;
                var treeTable = $('#bootstrap-table').bootstrapTreeTable({
        		    code : options.id,             // 用于设置父子关系
        	        parentCode : options.parentId, // 用于设置父子关系
        	    	type: 'get',                   // 请求方式（*）
        	        url: options.url,              // 请求后台的URL（*）
        	        ajaxParams : {},               // 请求数据的ajax的data属性
        			expandColumn : '0',            // 在哪一列上面显示展开按钮
        			striped : false,               // 是否各行渐变色
        			bordered : true,               // 是否显示边框
        			expandAll : $.common.visible(options.expandAll), // 是否全部展开
        	        columns: options.columns
        	    });
                $.treeTable._treeTable = treeTable;
            },
            // 条件查询
            search: function(form) {
            	var params = {};
            	$.each($("#" + form).serializeArray(), function(i, field) {
            		params[field.name] = field.value;
		        });
            	$.treeTable._treeTable.bootstrapTreeTable('refresh', params);
            },
            // 刷新
            refresh: function() {
            	$.treeTable._treeTable.bootstrapTreeTable('refresh');
            },
        },
        // 表单封装处理
    	form: {
            // 获取选中复选框项
            selectCheckeds: function(name) {
            	var checkeds = "";
        	    $('input:checkbox[name="' + name + '"]:checked').each(function(i) {
        	        if (0 == i) {
        	        	checkeds = $(this).val();
        	        } else {
        	        	checkeds += ("," + $(this).val());
        	        }
        	    });
        	    return checkeds;
            },
            // 获取选中下拉框项
            selectSelects: function(name) {
            	var selects = "";
        	    $('#' + name + ' option:selected').each(function (i) {
        	        if (0 == i) {
        	        	selects = $(this).val();
        	        } else {
        	        	selects += ("," + $(this).val());
        	        }
        	    });
        	    return selects;
            }
        },
        // 弹出层封装处理
    	modal: {
    		// 显示图标
    		icon: function(type) {
            	var icon = "";
        	    if (type == modal_status.WARNING) {
        	        icon = 0;
        	    } else if (type == modal_status.SUCCESS) {
        	        icon = 1;
        	    } else if (type == modal_status.FAIL) {
        	        icon = 2;
        	    } else {
        	        icon = 3;
        	    }
        	    return icon;
            },
    		// 消息提示
            msg: function(content, type) {
            	if (type != undefined) {
                    layer.msg(content, { icon: $.modal.icon(type), time: 1000, shift: 5 });
                } else {
                    layer.msg(content);
                }
            },
            // 错误消息
            msgError: function(content) {
            	$.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function(content) {
            	$.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function(content) {
            	$.modal.msg(content, modal_status.WARNING);
            },
    		// 弹出提示
            alert: function(content, type) {
        	    layer.alert(content, {
        	        icon: $.modal.icon(type),
        	        title: "系统提示",
        	        btn: ['确认'],
        	        btnclass: ['btn btn-primary'],
        	    });
            },
            // 消息提示并刷新父窗体
            msgReload: function(msg, type) {
            	layer.msg(msg, {
            	    icon: $.modal.icon(type),
            	    time: 500,
            	    shade: [0.1, '#8F8F8F']
            	},
            	function() {
            	    $.modal.reload();
            	});
            },
            // 错误提示
            alertError: function(content) {
            	$.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示
            alertSuccess: function(content) {
            	$.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示
            alertWarning: function(content) {
            	$.modal.alert(content, modal_status.WARNING);
            },
            // 关闭窗体
            close: function () {
            	var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 确认窗体
            confirm: function (content, callBack) {
            	layer.confirm(content, {
        	        icon: 3,
        	        title: "系统提示",
        	        btn: ['确认', '取消'],
        	        btnclass: ['btn btn-primary', 'btn btn-danger'],
        	    }, function (index) {
        	    	layer.close(index);
        	        callBack(true);
        	    });
            },
            // 弹出层指定宽度
            open: function (title, url, width, height) {
            	if ($.common.isEmpty(title)) {
                    title = false;
                };
                if ($.common.isEmpty(url)) {
                    url = "404.html";
                };
                if ($.common.isEmpty(width)) {
                	width = 800;
                };
                if ($.common.isEmpty(height)) {
                	height = ($(window).height() - 50);
                };
            	layer.open({
            		type: 2,
            		area: [width + 'px', height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: title,
            		content: url
            	});
            },
            // 弹出层全屏
            openFull: function (title, url, width, height) {
            	if ($.common.isEmpty(title)) {
                    title = false;
                };
                if ($.common.isEmpty(url)) {
                    url = "404.html";
                };
                if ($.common.isEmpty(width)) {
                	width = 800;
                };
                if ($.common.isEmpty(height)) {
                	height = ($(window).height() - 50);
                };
                var index = layer.open({
            		type: 2,
            		area: [width + 'px', height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: title,
            		content: url
            	});
                layer.full(index);
            },
            // 打开遮罩层
            loading: function (message) {
            	$.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
            },
            // 关闭遮罩层
            closeLoading: function () {
            	setTimeout(function(){
            		$.unblockUI();
            	}, 50);
            },
            // 重新加载
            reload: function () {
            	parent.location.reload();
            }
        },
        // 操作封装处理
        operate: {
        	// 提交数据
        	submit: function(url, type, dataType, data) {
        		$.modal.loading("正在处理中，请稍后...");
            	var config = {
        	        url: url,
        	        type: type,
        	        dataType: dataType,
        	        data: data,
        	        success: function(result) {
        	        	$.operate.ajaxSuccess(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // post请求传输
            post: function(url, data) {
            	$.operate.submit(url, "post", "json", data);
            },
            // 删除信息
            remove: function(id) {
            	$.modal.confirm("确定删除该条" + $.table._option.modalName + "信息吗？", function() {
	            	var url = $.common.isEmpty(id) ? $.table._option.removeUrl : $.table._option.removeUrl.replace("{id}", id);
	            	var data = { "ids": id };
	            	$.operate.submit(url, "post", "json", data);
            	});
            },
            // 批量删除信息
            batRemove: function() {
        		var rows = $.common.isEmpty($.table._option.id) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.id);
        		if (rows.length == 0) {
        			$.modal.alertWarning("请至少选择一条记录");
        			return;
        		}
        		$.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
        			var url = $.table._option.removeUrl;
        			var data = { "ids": rows.join() };
        			$.operate.submit(url, "post", "json", data);
        		});
            },
            // 添加信息
            add: function(id) {
            	var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.open("添加" + $.table._option.modalName, url);
            },
            // 修改信息
            edit: function(id) {
            	var url = $.table._option.updateUrl.replace("{id}", id);
            	$.modal.open("修改" + $.table._option.modalName, url);
            },
            // 添加信息 全屏
            addFull: function(id) {
            	var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + $.table._option.modalName, url);
            },
            // 修改信息 全屏
            editFull: function(id) {
            	var url = $.table._option.updateUrl.replace("{id}", id);
            	$.modal.openFull("修改" + $.table._option.modalName, url);
            },
            // 保存信息
            save: function(url, data) {
            	$.modal.loading("正在处理中，请稍后...");
            	var config = {
        	        url: url,
        	        type: "post",
        	        dataType: "json",
        	        data: data,
        	        success: function(result) {
        	        	$.operate.saveSuccess(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // 保存结果弹出msg刷新table表格
            ajaxSuccess: function (result) {
            	if (result.code == web_status.SUCCESS) {
                	$.modal.msgSuccess(result.msg);
            		$.table.refresh();
                } else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            },
            // 保存结果提示msg
            saveSuccess: function (result) {
            	if (result.code == web_status.SUCCESS) {
            		$.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                } else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            }
        },
        // 通用方法封装处理
    	common: {
    		// 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return false;
            },
            // 是否显示数据 为空默认为显示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            }
        }
    });
})(jQuery);

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