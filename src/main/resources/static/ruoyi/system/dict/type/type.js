var prefix = ctx + "system/dict"

$(function() {
		var columns = [{
            checkbox: true
        },
        {
            field: 'dictId',
            title: '字典主键'
        },
        {
            field: 'dictName',
            title: '字典名称'
        },
        {
            field: 'dictType',
            title: '字典类型'
        },
        {
            field: 'status',
            title: '状态',
            align: 'center',
            formatter: function(value, row, index) {
                if (value == 0) {
                    return '<span class="label label-success">正常</span>';
                } else if (value == 1) {
                    return '<span class="label label-danger">停用</span>';
                }
            }
        },
        {
            field: 'remark',
            title: '备注'
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
            	actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="#" onclick="edit(\'' + row.dictId + '\')"><i class="fa fa-edit"></i>编辑</a> ');
            	actions.push('<a class="btn btn-info btn-xs ' + listFlag + '" href="#" onclick="detail(\'' + row.dictId + '\')"><i class="fa fa-list-ul"></i>列表</a> ');
            	actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="#" onclick="remove(\'' + row.dictId + '\')"><i class="fa fa-remove"></i>删除</a>');
            	return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

/*字典管理-新增*/
function add() {
    var url = prefix + '/add';
    layer_showAuto("新增字典类型", url);
}

/*角色管理-修改*/
function edit(dictId) {
    var url = prefix + '/edit/' + dictId;
    layer_showAuto("修改字典类型", url);
}

/*字典列表-详细*/
function detail(dictId) {
	var url = prefix + '/detail/' + dictId;
	createMenuItem(url, "字典数据");
}

//单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中字典吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("dictId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post");
	});
}
