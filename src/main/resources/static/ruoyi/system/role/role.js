var prefix = "/system/role"

$(function() {
	var columns = [{
            checkbox: true
        },
        {
            field: 'roleId',
            title: '角色编号'
        },
        {
            field: 'roleName',
            title: '角色名称'
        },
        {
            field: 'roleKey',
            title: '权限字符'
        },
        {
            field: 'roleSort',
            title: '显示顺序'
        },
        {
            field: 'status',
            title: '状态',
            align: 'center',
            formatter: function(value, row, index) {
                if (value == 0) {
                    return '<span class="label label-success">正常</span>';
                } else if (value == 1) {
                    return '<span class="label label-danger">禁用</span>';
                }
            }
        },
        {
            field: 'createTime',
            title: '创建时间'
        },
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
            	var actions = [];
				actions.push('<a class="btn btn-primary btn-sm ' + editFlag + '" href="#" title="编辑" mce_href="#" onclick="edit(\'' + row.roleId + '\')"><i class="fa fa-edit"></i></a> ');
				actions.push('<a class="btn btn-warning btn-sm ' + removeFlag + '" href="#" title="删除" onclick="remove(\'' + row.roleId + '\')"><i class="fa fa-remove"></i></a>');
				return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

/*角色管理-新增*/
function add() {
    var url = prefix + '/add';
    layer_showAuto("新增角色", url);
}

/*角色管理-修改*/
function edit(roleId) {
    var url = prefix + '/edit/' + roleId;
    layer_showAuto("修改角色", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中角色吗？", function(r) {
		_ajax(prefix + "/remove/" + id, "", "post", r);
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("roleId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function(r) {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post", r);
	});
}
