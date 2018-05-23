var prefix = ctx + "monitor/jobLog"

$(function() {
	var columns = [{
            checkbox: true
        },
        {
            field: 'jobLogId',
            title: '任务日志编号'
        },
        {
            field: 'jobName',
            title: '任务名称'
        },
        {
            field: 'jobGroup',
            title: '任务组名'
        },
        {
            field: 'methodName',
            title: '方法名称'
        },
        {
            field: 'params',
            title: '方法参数'
        },
        {
            field: 'jobMessage',
            title: '日志信息'
        },
        {
            field: 'isException',
            title: '状态',
            align: 'center',
            formatter: function(value, row, index) {
                if (value == 0) {
                    return '<span class="label label-success">正常</span>';
                } else if (value == 1) {
                    return '<span class="label label-danger">异常</span>';
                }
            }
        },
        {
            field: 'createDateTimeStr',
            title: '创建时间'
        },
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
            	var actions = [];
				actions.push('<a class="btn btn-warning btn-sm ' + removeFlag + '" href="#" title="删除" onclick="remove(\'' + row.jobLogId + '\')"><i class="fa fa-remove"></i></a>');
				return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

// 单条删除
function remove(jobLogId) {
	$.modalConfirm("确定要删除选中岗位吗？", function() {
		_ajax(prefix + "/remove/" + jobLogId, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("jobLogId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post");
	});
}
