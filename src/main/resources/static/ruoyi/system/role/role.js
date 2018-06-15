var prefix = ctx + "system/role"

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
                    return '<span class="badge badge-primary">正常</span>';
                } else if (value == 1) {
                    return '<span class="badge badge-danger">禁用</span>';
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
				actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="#" onclick="edit(\'' + row.roleId + '\')"><i class="fa fa-edit"></i>编辑</a> ');
				actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="#" onclick="remove(\'' + row.roleId + '\')"><i class="fa fa-remove"></i>删除</a>');
				return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

/*角色管理-搜索*/
function search() {
    $('.bootstrap-table').bootstrapTable('refresh', queryParams);
}

function queryParams(params) {
	return {
		// 传递参数查询参数
		pageSize:       params.limit,
		pageNum:        params.offset / params.limit + 1,
		searchValue:    params.search,
		orderByColumn:  params.sort,
		isAsc:          params.order,
		roleName:       $("#roleName").val(),
		roleKey:        $("#roleKey").val(),
		status:         $("#status option:selected").val()
	};
}

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
	$.modalConfirm("确定要删除选中角色吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("roleId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post");
	});
}
