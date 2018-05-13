var prefix = ctx + "system/dept"

window.onload = function() {
	loading();
};

function loading() {
	var columns = [{
        	field : 'deptName',
			title : '部门名称'
        },
        {
        	field : 'orderNum',
			title : '排序'
        },
        {
        	field : 'status',
			title : '状态',
			formatter : function(item, index) {
				if (item.status == '0') {
					return '<span class="label label-primary">正常</span>';
				} else if (item.status == '1') {
					return '<span class="label label-danger">停用</span>';
				}
			}
        },
        {
        	field: 'createTimeStr',
			title : '创建时间'
        },
        {
        	title : '操作',
			align : 'center',
			formatter : function(row, index) {
				if(row.parentId != 0) {
					var actions = [];
					actions.push('<a class="btn btn-primary btn-sm ' + editFlag + '" href="#" title="编辑" mce_href="#" onclick="edit(\'' + row.deptId + '\')"><i class="fa fa-edit"></i></a> ');
					actions.push('<a class="btn btn-primary btn-sm ' + addFlag + '" href="#" title="新增" mce_href="#" onclick="add(\'' + row.deptId + '\')"><i class="fa fa-plus"></i></a> ');
					actions.push('<a class="btn btn-warning btn-sm ' + removeFlag + '" href="#" title="删除" mce_href="#" onclick="remove(\'' + row.deptId + '\')"><i class="fa fa-remove"></i></a>');
					return actions.join('');
				} else {
					return "";
				}
			}
        }];
	var url = prefix + "/list";
	$.initTreeTable('deptId', 'parentId', columns, url);
}

/*部门管理-新增*/
function add(deptId) {
    var url = prefix + '/add/' + deptId;
    layer_showAuto("新增部门", url);
}

/*部门管理-修改*/
function edit(deptId) {
    var url = prefix + '/edit/' + deptId;
    layer_showAuto("修改部门", url);
}

/*部门管理-删除*/
function remove(deptId) {
	layer.confirm("确定要删除部门吗？",{icon: 3, title:'提示'},function(index){
		$.ajax({
			type : 'get',
			url: prefix + "/remove/" + deptId,
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg, { icon: 1, time: 1000 });
					loading();
				} else {
					layer.alert(r.msg, { icon: 2, title: "系统提示" });
				}
			}
		});
	});
}
