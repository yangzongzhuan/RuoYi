var prefix = ctx + "tool/gen"

$(function() {
	var columns = [{
            checkbox: true
        },
        {
            field: 'tableName',
            title: '表名称'
        },
        {
            field: 'tableComment',
            title: '表描述'
        },
        {
            field: 'createDateTimeStr',
            title: '创建时间'
        },
        {
            field: 'updateDateTimeStr',
            title: '更新时间'
        },
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
                var msg = '<a class="btn btn-primary btn-sm" href="#" title="生成" onclick="genCode(\'' + row.tableName + '\')"><i class="fa fa-bug"></i></a> ';
                return msg;
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

// 生成代码
function genCode(tableName) {
	$.modalConfirm("确定要生成" + tableName + "表代码吗？", function() {
		location.href = prefix + "/genCode/" + tableName;
		layer.msg('执行成功,正在生成代码请稍后…', {icon: 1});
    })
}

//批量生成代码
function batchGenCode() {
	var rows = $.getSelections("tableName");
	if (rows.length == 0) {
		$.modalMsg("请选择要生成的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要生成选中的" + rows.length + "条数据吗?", function() {
		location.href = prefix + "/batchGenCode?tables=" + JSON.stringify(rows);
		layer.msg('执行成功,正在生成代码请稍后…', {icon: 1});
	});
}
