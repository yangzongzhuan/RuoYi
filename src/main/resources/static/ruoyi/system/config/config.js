var prefix = ctx + "system/config"

$(function() {
	var columns = [{
            checkbox: true
        },
		{
			field : 'configId', 
			title : '参数主键' 
		},
		{
			field : 'configName', 
			title : '参数名称' 
		},
		{
			field : 'configKey', 
			title : '参数键名' 
		},
		{
			field : 'configValue', 
			title : '参数键值' 
		},
		{
            field: 'configType',
            title: '系统内置',
            align: 'center',
            formatter: function(value, row, index) {
                if (value == 'Y') {
                    return '<span class="label label-success">是</span>';
                } else if (value == 'N') {
                    return '<span class="label label-primary">否</span>';
                }
            }
        },
		{
			field : 'createDateTimeStr', 
			title : '创建时间' 
		},
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
            	var actions = [];
				actions.push('<a class="btn btn-primary btn-sm ' + editFlag + '" href="#" title="编辑" mce_href="#" onclick="edit(\'' + row.configId + '\')"><i class="fa fa-edit"></i></a> ');
				actions.push('<a class="btn btn-warning btn-sm ' + removeFlag + '" href="#" title="删除" onclick="remove(\'' + row.configId + '\')"><i class="fa fa-remove"></i></a>');
				return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

/*参数配置-新增*/
function add() {
    var url = prefix + '/add';
    layer_showAuto("新增参数", url);
}

/*参数配置-修改*/
function edit(configId) {
    var url = prefix + '/edit/' + configId;
    layer_showAuto("修改参数", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中参数配置吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("configId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post");
	});
}
