/**
 * 通用js方法封装处理
 * Copyright (c) 2018 ruoyi
 */
(function ($) {
    $.extend({
    	_treeTable: {},
    	_tree: {},
    	// 表格封装处理
    	table: {
            _option: {},
            _params: {},
            // 初始化表格参数
            init: function(options) {
                $.table._option = options;
                $.table._params = $.common.isEmpty(options.queryParams) ? $.table.queryParams : options.queryParams;
                _sortOrder = $.common.isEmpty(options.sortOrder) ? "asc" : options.sortOrder;
                _sortName = $.common.isEmpty(options.sortName) ? "" : options.sortName;
                _striped = $.common.isEmpty(options.striped) ? false : options.striped;
                _escape = $.common.isEmpty(options.escape) ? false : options.escape;
                $('#bootstrap-table').bootstrapTable({
                    url: options.url,                                   // 请求后台的URL（*）
                    contentType: "application/x-www-form-urlencoded",   // 编码类型
                    method: 'post',                                     // 请求方式（*）
                    cache: false,                                       // 是否使用缓存
                    striped: _striped,                                  // 是否显示行间隔色
                    sortable: true,                                     // 是否启用排序
                    sortStable: true,                                   // 设置为 true 将获得稳定的排序
                    sortName: _sortName,                                // 排序列名称
                    sortOrder: _sortOrder,                              // 排序方式  asc 或者 desc
                    pagination: $.common.visible(options.pagination),   // 是否显示分页（*）
                    pageNumber: 1,                                      // 初始化加载第一页，默认第一页
                    pageSize: 10,                                       // 每页的记录行数（*） 
                    pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
                    escape: _escape,                                    // 转义HTML字符串
                    iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
        	        toolbar: '#toolbar',                                // 指定工作栏
                    sidePagination: "server",                           // 启用服务端分页
                    search: $.common.visible(options.search),           // 是否显示搜索框功能
                    showSearch: $.common.visible(options.showSearch),   // 是否显示检索信息
                    showRefresh: $.common.visible(options.showRefresh), // 是否显示刷新按钮
        			showColumns: $.common.visible(options.showColumns), // 是否显示隐藏某列下拉框
        			showToggle: $.common.visible(options.showToggle),   // 是否显示详细视图和列表视图的切换按钮
        			showExport: $.common.visible(options.showExport),   // 是否支持导出文件
                    queryParams: $.table._params,                       // 传递参数（*）
                    columns: options.columns,                           // 显示列信息（*）
                    responseHandler: $.table.responseHandler            // 回调函数
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
            // 请求获取数据后处理回调函数
            responseHandler: function(res) {
                if (res.code == 0) {
                    return { rows: res.rows, total: res.total };
                } else {
                	$.modal.alertWarning(res.msg);
                	return { rows: [], total: 0 };
                }
            },
            // 搜索-默认第一个form
            search: function(formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
    		    var params = $("#bootstrap-table").bootstrapTable('getOptions');
    		    params.queryParams = function(params) {
    		        var search = {};
    		        $.each($("#" + currentId).serializeArray(), function(i, field) {
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
    		// 下载-默认第一个form
    		exportExcel: function(formId) {
    			var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
    			$.modal.loading("正在导出数据，请稍后...");
    			$.post($.table._option.exportUrl, $("#" + currentId).serializeArray(), function(result) {
    				if (result.code == web_status.SUCCESS) {
    			        window.location.href = ctx + "common/download?fileName=" + result.msg + "&delete=" + true;
    				} else {
    					$.modal.alertError(result.msg);
    				}
    				$.modal.closeLoading();
    			});
    		},
            // 刷新表格
            refresh: function() {
                $("#bootstrap-table").bootstrapTable('refresh', {
                    silent: true
                });
            },
            // 查询表格指定列值
            selectColumns: function(column) {
            	return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
        	        return row[column];
        	    });
            },
            // 查询表格首列值
            selectFirstColumns: function() {
            	return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
        	        return row[$.table._option.columns[1].field];
        	    });
            },
            // 回显数据字典
            selectDictLabel: function(datas, value) {
            	var actions = [];
                $.each(datas, function(index, dict) {
                    if (dict.dictValue == value) {
                    	actions.push("<span class='badge badge-" + dict.listClass + "'>" + dict.dictLabel + "</span>");
                        return false;
                    }
                });
                return actions.join('');
            },
            // 显示表格指定列
            showColumn: function(column) {
                $("#bootstrap-table").bootstrapTable('showColumn', column);
            },
            // 隐藏表格指定列
            hideColumn: function(column) {
            	$("#bootstrap-table").bootstrapTable('hideColumn', column);
            }
        },
        // 表格树封装处理
        treeTable: {
            _option: {},
            // 初始化表格
            init: function(options) {
                $.table._option = options;
                _striped = $.common.isEmpty(options.striped) ? false : options.striped;
                _expandColumn = $.common.isEmpty(options.expandColumn) ? '1' : options.expandColumn;
                var treeTable = $('#bootstrap-tree-table').bootstrapTreeTable({
                	code: options.code,                                 // 用于设置父子关系
        		    parentCode: options.parentCode,                     // 用于设置父子关系
        	    	type: 'get',                                        // 请求方式（*）
        	        url: options.url,                                   // 请求后台的URL（*）
        	        ajaxParams: {},                                     // 请求数据的ajax的data属性
        			expandColumn: _expandColumn,                        // 在哪一列上面显示展开按钮
        			striped: _striped,                                  // 是否显示行间隔色
        			bordered: true,                                     // 是否显示边框
        			toolbar: '#toolbar',                                // 指定工作栏
        			showRefresh: $.common.visible(options.showRefresh), // 是否显示刷新按钮
        			showColumns: $.common.visible(options.showColumns), // 是否显示隐藏某列下拉框
        			expandAll: $.common.visible(options.expandAll),     // 是否全部展开
        			expandFirst: $.common.visible(options.expandFirst), // 是否默认第一级展开--expandAll为false时生效
        	        columns: options.columns
        	    });
                $._treeTable = treeTable;
            },
            // 条件查询
            search: function(formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
            	var params = {};
            	$.each($("#" + currentId).serializeArray(), function(i, field) {
            		params[field.name] = field.value;
		        });
            	$._treeTable.bootstrapTreeTable('refresh', params);
            },
            // 刷新
            refresh: function() {
            	$._treeTable.bootstrapTreeTable('refresh');
            },
        },
        // 表单封装处理
    	form: {
    		// 表单重置
    		reset: function(formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
            	$("#" + currentId)[0].reset();
            },
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
            	//如果是移动端，就使用自适应大小弹窗
            	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            	    width = 'auto';
            	    height = 'auto';
            	}
            	if ($.common.isEmpty(title)) {
                    title = false;
                };
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
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
            		content: url,
            		btn: ['确定', '关闭'],
            	    // 弹层外区域关闭
            		shadeClose: true,
            		yes: function(index, layero) {
            	        var iframeWin = layero.find('iframe')[0];
            	        iframeWin.contentWindow.submitHandler();
            	    },
            	    cancel: function(index) {
            	        return true;
            	    }
            	});
            },
            // 弹出层指定参数选项
            openOptions: function (options) {
            	var _url = $.common.isEmpty(options.url) ? "/404.html" : options.url; 
            	var _title = $.common.isEmpty(options.title) ? "系统窗口" : options.title; 
                var _width = $.common.isEmpty(options.width) ? "800" : options.width; 
                var _height = $.common.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
                layer.open({
                    type: 2,
            		maxmin: true,
                    shade: 0.3,
                    title: _title,
                    fix: false,
                    area: [_width + 'px', _height + 'px'],
                    content: _url,
                    shadeClose: true,
                    btn: ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'],
                    yes: function (index, layero) {
                        options.callBack(index, layero)
                    }, cancel: function () {
                        return true;
                    }
                });
            },
            // 弹出层全屏
            openFull: function (title, url, width, height) {
            	//如果是移动端，就使用自适应大小弹窗
            	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            	    width = 'auto';
            	    height = 'auto';
            	}
            	if ($.common.isEmpty(title)) {
                    title = false;
                };
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
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
            		content: url,
            		// 弹层外区域关闭
            		shadeClose: true
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
            // 详细信息
            detail: function(id, width, height) {
            	var _url = $.common.isEmpty(id) ? $.table._option.detailUrl : $.table._option.detailUrl.replace("{id}", id);
                var _width = $.common.isEmpty(width) ? "800" : width; 
                var _height = $.common.isEmpty(height) ? ($(window).height() - 50) : height;
            	//如果是移动端，就使用自适应大小弹窗
            	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            	    _width = 'auto';
            	    _height = 'auto';
            	}
            	layer.open({
            		type: 2,
            		area: [_width + 'px', _height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: $.table._option.modalName + "详细",
            		content: _url,
            		btn: '关闭',
            	    // 弹层外区域关闭
            		shadeClose: true,
            		success: function(layer) {
            			layer[0].childNodes[3].childNodes[0].attributes[0].value='layui-layer-btn1';
            		},
            		btn1: function(index) {
            			layer.close(index);
            	    }
            	});
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
            removeAll: function() {
        		var rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
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
            // 清空信息
            clean: function() {
            	$.modal.confirm("确定清空所有" + $.table._option.modalName + "吗？", function() {
	            	var url = $.table._option.cleanUrl;
	            	$.operate.submit(url, "post", "json", "");
            	});
            },
            // 添加信息
            add: function(id) {
            	var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.open("添加" + $.table._option.modalName, url);
            },
            // 修改信息
            edit: function(id) {
            	var url = "/404.html";
            	if ($.common.isNotEmpty(id)) {
            	    url = $.table._option.updateUrl.replace("{id}", id);
            	} else {
            	    var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
            	    if (id.length == 0) {
            			$.modal.alertWarning("请至少选择一条记录");
            			return;
            		}
            	    url = $.table._option.updateUrl.replace("{id}", id);
            	}
            	$.modal.open("修改" + $.table._option.modalName, url);
            },
            // 工具栏表格树修改
            editTree: function() {
            	var row = $('#bootstrap-tree-table').bootstrapTreeTable('getSelections')[0];
            	if ($.common.isEmpty(row)) {
        			$.modal.alertWarning("请至少选择一条记录");
        			return;
        		}
                var url = $.table._option.updateUrl.replace("{id}", row[$.table._option.uniqueId]);
                $.modal.open("修改" + $.table._option.modalName, url);
            },
            // 添加信息 全屏
            addFull: function(id) {
            	var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + $.table._option.modalName, url);
            },
            // 修改信息 全屏
            editFull: function(id) {
            	var url = "/404.html";
            	if ($.common.isNotEmpty(id)) {
            	    url = $.table._option.updateUrl.replace("{id}", id);
            	} else {
            	    var row = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
            	    url = $.table._option.updateUrl.replace("{id}", row);
            	}
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
        	        	$.operate.successCallback(result);
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
            // 成功结果提示msg（父窗体全局更新）
            saveSuccess: function (result) {
            	if (result.code == web_status.SUCCESS) {
            		$.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                } else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            },
            // 成功回调执行事件（父窗体静默更新）
            successCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                    if (window.parent.$("#bootstrap-table").length > 0) {
                        $.modal.close();
                        window.parent.$.modal.msgSuccess(result.msg);
                        window.parent.$.table.refresh();
                    } else if (window.parent.$("#bootstrap-tree-table").length > 0) {
                        $.modal.close();
                        window.parent.$.modal.msgSuccess(result.msg);
                        window.parent.$.treeTable.refresh();
                    } else {
                        $.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                    }
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            }
        },
        // 校验封装处理
        validate: {
        	// 判断返回标识是否唯一 false 不存在 true 存在
        	unique: function (value) {
            	if (value == "0") {
                    return true;
                }
                return false;
            },
            // 表单验证
            form: function (formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().form();
            }
        },
        // 树插件封装处理
        tree: {
        	_option: {},
        	_lastValue: {},
        	// 初始化树结构
        	init: function(options) {
        		$.tree._option = options;
        		// 属性ID
        		var _id = $.common.isEmpty(options.id) ? "tree" : options.id;
        		// 展开等级节点
        		var _expandLevel = $.common.isEmpty(options.expandLevel) ? 0 : options.expandLevel;
        		// 树结构初始化加载
        	    var setting = {
        	    	check: options.check,
        	        view: { selectedMulti: false, nameIsHTML: true },
        	        data: { key: { title: "title" }, simpleData: { enable: true } },
        	        callback: { onClick: options.onClick }
        	    };
        	    $.get(options.url, function(data) {
        	    	var treeName = $("#treeName").val();
        			var treeId = $("#treeId").val();
        			tree = $.fn.zTree.init($("#" + _id), setting, data);
        			$._tree = tree;
        			// 展开第一级节点
        			var nodes = tree.getNodesByParam("level", 0);
        			for (var i = 0; i < nodes.length; i++) {
        				if(_expandLevel > 0) {
        					tree.expandNode(nodes[i], true, false, false);
        				}
    				    $.tree.selectByIdName(treeId, treeName, nodes[i]);
        			}
        			// 展开第二级节点
        			nodes = tree.getNodesByParam("level", 1);
        			for (var i = 0; i < nodes.length; i++) {
        				if(_expandLevel > 1) {
        					tree.expandNode(nodes[i], true, false, false);
        				}
    				    $.tree.selectByIdName(treeId, treeName, nodes[i]);
        			}
        			// 展开第三级节点
        			nodes = tree.getNodesByParam("level", 2);
        			for (var i = 0; i < nodes.length; i++) {
        				if(_expandLevel > 2) {
        					tree.expandNode(nodes[i], true, false, false);
        				}
    				    $.tree.selectByIdName(treeId, treeName, nodes[i]);
        			}
        		}, null, null, "正在加载，请稍后...");
        	},
        	// 搜索节点
        	searchNode: function() {
        		// 取得输入的关键字的值
        		var value = $.common.trim($("#keyword").val());
        		if ($.tree._lastValue === value) {
        		    return;
        		}
        		// 保存最后一次搜索名称
        		$.tree._lastValue = value;
        		var nodes = $._tree.getNodes();
        		// 如果要查空字串，就退出不查了。
        		if (value == "") {
        		    $.tree.showAllNode(nodes);
        		    return;
        		}
        		$.tree.hideAllNode(nodes);
        		// 根据搜索值模糊匹配
        		$.tree.updateNodes($._tree.getNodesByParamFuzzy("name", value));
        	},
        	// 根据Id和Name选中指定节点
        	selectByIdName: function(treeId, treeName, node) {
        		if ($.common.isNotEmpty(treeName) && $.common.isNotEmpty(treeId)) {
        			if (treeId == node.id && treeName == node.name) {
            			$._tree.selectNode(node, true);
            		}
        		}
        	},
        	// 显示所有节点
        	showAllNode: function(nodes) {
        		nodes = $._tree.transformToArray(nodes);
        		for (var i = nodes.length - 1; i >= 0; i--) {
        		    if (nodes[i].getParentNode() != null) {
        		    	$._tree.expandNode(nodes[i], true, false, false, false);
        		    } else {
        		    	$._tree.expandNode(nodes[i], true, true, false, false);
        		    }
        		    $._tree.showNode(nodes[i]);
        		    $.tree.showAllNode(nodes[i].children);
        		}
        	},
        	// 隐藏所有节点
        	hideAllNode: function(nodes) {
        	    var tree = $.fn.zTree.getZTreeObj("tree");
        	    var nodes = $._tree.transformToArray(nodes);
        	    for (var i = nodes.length - 1; i >= 0; i--) {
        	    	$._tree.hideNode(nodes[i]);
        	    }
        	},
        	// 显示所有父节点
        	showParent: function(treeNode) {
        		var parentNode;
        		while ((parentNode = treeNode.getParentNode()) != null) {
        			$._tree.showNode(parentNode);
        			$._tree.expandNode(parentNode, true, false, false);
        		    treeNode = parentNode;
        		}
        	},
        	// 显示所有孩子节点
        	showChildren: function(treeNode) {
        		if (treeNode.isParent) {
        		    for (var idx in treeNode.children) {
        		        var node = treeNode.children[idx];
        		        $._tree.showNode(node);
        		        $.tree.showChildren(node);
        		    }
        		}
        	},
        	// 更新节点状态
        	updateNodes: function(nodeList) {
        		$._tree.showNodes(nodeList);
        		for (var i = 0, l = nodeList.length; i < l; i++) {
        		    var treeNode = nodeList[i];
        		    $.tree.showChildren(treeNode);
        		    $.tree.showParent(treeNode)
        		}
        	},
        	// 获取当前被勾选集合
        	getCheckedNodes: function(column) {
        		var _column = $.common.isEmpty(column) ? "id" : column;
        		var nodes = $._tree.getCheckedNodes(true);
        		return $.map(nodes, function (row) {
        	        return row[_column];
        	    }).join();
        	},
        	// 不允许根父节点选择
        	notAllowParents: function(_tree) {
    		    var nodes = _tree.getSelectedNodes();
    		    for (var i = 0; i < nodes.length; i++) {
    		        if (nodes[i].level == 0) {
    		            $.modal.msgError("不能选择根节点（" + nodes[i].name + "）");
    		            return false;
    		        }
    		        if (nodes[i].isParent) {
    		            $.modal.msgError("不能选择父节点（" + nodes[i].name + "）");
    		            return false;
    		        }
    		    }
        		return true;
        	},
        	// 不允许最后层级节点选择
        	notAllowLastLevel: function(_tree) {
    		    var nodes = _tree.getSelectedNodes();
    		    for (var i = 0; i < nodes.length; i++) {
    		    	if (nodes[i].level == nodes.length + 1) {
    		    		$.modal.msgError("不能选择最后层级节点（" + nodes[i].name + "）");
    		            return false;
    		        }
    		    }
        		return true;
        	},
        	// 隐藏/显示搜索栏
        	toggleSearch: function() {
        		$('#search').slideToggle(200);
        		$('#btnShow').toggle();
        		$('#btnHide').toggle();
        		$('#keyword').focus();
        	},
        	// 折叠
        	collapse: function() {
        		$._tree.expandAll(false);
        	},
        	// 展开
        	expand: function() {
        		$._tree.expandAll(true);
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
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
            	return !$.common.isEmpty(value);
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
            },
            startWith: function(value, start) {
                var reg = new RegExp("^" + start);
                return reg.test(value)
            },
            endWith: function(value, end) {
                var reg = new RegExp(end + "$");
                return reg.test(value)
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