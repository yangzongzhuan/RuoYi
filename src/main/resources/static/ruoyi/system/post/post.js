var prefix = ctx + "system/post"
$(function() {
	var columns = [{
            checkbox: true
        },
        {
            field: 'postId',
            title: '岗位编号'
        },
        {
            field: 'postCode',
            title: '岗位编码'
        },
        {
            field: 'postName',
            title: '岗位名称'
        },
        {
            field: 'postSort',
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
                    return '<span class="label label-danger">停用</span>';
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
				actions.push('<a class="btn btn-primary btn-sm ' + editFlag + '" href="#" title="编辑" mce_href="#" onclick="edit(\'' + row.postId + '\')"><i class="fa fa-edit"></i></a> ');
				actions.push('<a class="btn btn-warning btn-sm ' + removeFlag + '" href="#" title="删除" onclick="remove(\'' + row.postId + '\')"><i class="fa fa-remove"></i></a>');
				return actions.join('');
            }
        }];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

/*岗位管理-新增*/
function add() {
    var url = prefix + '/add';
    layer_showAuto("新增岗位", url);
}

/*岗位管理-修改*/
function edit(postId) {
    var url = prefix + '/edit/' + postId;
    layer_showAuto("修改岗位", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中岗位吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("postId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', { "ids": rows }, "post");
	});
}
