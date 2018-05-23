var prefix = ctx + "monitor/job"

$(function() {
	var columns = [{
            checkbox: true
        },
        {
            field: 'jobId',
            title: '任务编号'
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
            field: 'cronExpression',
            title: '执行表达式'
        },
        {
            field: 'status',
            title: '状态',
            align: 'center',
            formatter: function(value, row, index) {
                if (value == 0) {
                    return '<span class="label label-success">正常</span>';
                } else if (value == 1) {
                    return '<span class="label label-danger">暂停</span>';
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
				actions.push(statusTools(row));
				actions.push('<a class="btn btn-primary btn-sm ' + editFlag + '" href="#" title="编辑" mce_href="#" onclick="edit(\'' + row.jobId + '\')"><i class="fa fa-edit"></i></a> ');
				actions.push('<a class="btn btn-warning btn-sm ' + removeFlag + '" href="#" title="删除" onclick="remove(\'' + row.jobId + '\')"><i class="fa fa-remove"></i></a>');
				return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

function statusTools(row) {
    if (row.status == 1) {
        return '<a class="btn btn-primary btn-sm ' + statusFlag + '" href="#" title="启用" onclick="start(this,\'' + row.jobId + '\')"><i class="fa fa-check"></i></a> ';
    } else {
    	return '<a class="btn btn-primary btn-sm ' + statusFlag + '" href="#" title="停用" onclick="stop(this,\'' + row.jobId + '\')"><i class="fa fa-minus"></i></a> ';
    }
}

/*调度任务-停用*/
function stop(obj, jobId) {
	$.modalConfirm("确认要停用吗？", function() {
		_ajax(prefix + "/changeStatus/", { "jobId": jobId, "status": 1 }, "post");
    })
}

/*调度任务-启用*/
function start(obj, jobId) {
	$.modalConfirm("确认要启用吗？", function() {
		_ajax(prefix + "/changeStatus/", { "jobId": jobId, "status": 0 }, "post");
    })
}

/*调度任务-新增*/
function add() {
    var url = prefix + '/add';
    layer_showAuto("新增调度任务", url);
}

/*调度任务-修改*/
function edit(jobId) {
    var url = prefix + '/edit/' + jobId;
    layer_showAuto("修改调度任务", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中调度任务吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("jobId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post");
	});
}

//调度日志查询
function jobLog(id) {
	var url = ctx + 'monitor/jobLog';
	createMenuItem(url, "调度日志");
}
