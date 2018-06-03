/**
 * 表格通用方法封装处理
 * Copyright (c) 2018 ruoyi
 */
(function($) {
    $.extend({
        ryTable: {
            _option: {},
            _params: {},
            init: function(options) {
                $.ryTable._option = options;
                $.ryTable._params = options.queryParams == null ? $.ryTable.queryParams : options.queryParams;
                $('.bootstrap-table').bootstrapTable({
                    url: options.url,                                   // 请求后台的URL（*）
                    contentType: "application/x-www-form-urlencoded",   // 编码类型
                    method: 'post',                                     // 请求方式（*）
                    toolbar: '#toolbar',                                // 工具按钮用哪个容器
                    cache: false,                                       // 是否使用缓存
                    sortable: false,                                    // 是否启用排序
                    sortOrder: "asc",                                   // 排序方式
                    sortStable: true,                                   // 设置为 true 将获得稳定的排序
                    pagination: true,                                   // 是否显示分页（*）
                    sidePagination: "server",                           // 启用服务端分页 
                    pageNumber: 1,                                      // 初始化加载第一页，默认第一页
                    pageSize: 10,                                       // 每页的记录行数（*） 
                    pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
                    queryParams: $.ryTable._params,                     // 传递参数（*）
                    columns: options.columns                            // 显示列信息（*）
                });
            },
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
            refresh: function() {
                $(".bootstrap-table").bootstrapTable('refresh', {
                    url: $.ryTable._option.url
                });
            }
        }
    });
})(jQuery);

function default_params(params) {
	return {
		// 传递参数查询参数
		pageSize:       params.limit,
		pageNum:        params.offset / params.limit + 1,
		searchValue:    params.search,
		orderByColumn:  params.sort,
		isAsc:          params.order
	};
}