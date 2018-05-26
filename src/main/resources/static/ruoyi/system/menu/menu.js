var prefix = ctx + "system/menu"

window.onload = function() {
	loading();
};

function loading() {
	var columns = [
        {
			title : '菜单名称',
			field : 'menuName',
			width : '20%',
			formatter : function(row, index) {
				if(row.icon == null || row == "") {
					return row.menuName;
			    } else {
			    	return '<i class="' + row.icon + '"></i> <span class="nav-label">' + row.menuName + '</span>';
			    }
			}
		},
        {
        	field : 'orderNum',
			title : '排序',
			width : '10%',
        },
        {
        	field : 'url',
			title : '请求地址',
			width : '15%',
        },
        {
			title : '类型',
			field : 'menuType',
			width : '10%',
			formatter : function(item, index) {
				if (item.menuType == 'M') {
					return '<span class="label label-success">目录</span>';
				}
				if (item.menuType == 'C') {
					return '<span class="label label-primary">菜单</span>';
				}
				if (item.menuType == 'F') {
					return '<span class="label label-warning">按钮</span>';
				}
			}
		},
		{
            field: 'visible',
            title: '可见',
            width : '10%',
            formatter: function(row, index) {
                if (row.visible == 0) {
                    return '<span class="badge badge-primary">显示</span>';
                } else if (row.visible == 1) {
                    return '<span class="badge badge-danger">隐藏</span>';
                }
            }
        },
        {
        	field : 'perms',
			title : '权限标识',
			width : '15%',
        },
        {
        	title : '操作',
			width : '20%',
			formatter : function(row, index) {
				var actions = [];
				actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="#" mce_href="#" onclick="edit(\'' + row.menuId + '\')"><i class="fa fa-edit"></i>编辑</a> ');
				actions.push('<a class="btn btn-info btn-xs ' + addFlag + '" href="#" onclick="add(\'' + row.menuId + '\')"><i class="fa fa-plus"></i>新增</a> ');
				actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="#" onclick="remove(\'' + row.menuId + '\')"><i class="fa fa-remove"></i>删除</a>');
				return actions.join('');
			}
        }];
	var url = prefix + "/list";
	$.initTreeTable('menuId', 'parentId', columns, url, false);
}

/*菜单管理-新增*/
function add(menuId) {
    var url = prefix + '/add/' + menuId;
    layer_showAuto("新增菜单", url);
}

/*菜单管理-修改*/
function edit(menuId) {
    var url = prefix + '/edit/' + menuId;
    layer_showAuto("修改菜单", url);
}

/*菜单管理-删除*/
function remove(menuId) {
	layer.confirm("确定要删除菜单吗？",{icon: 3, title:'提示'},function(index){
		$.ajax({
			type : 'get',
			url: prefix + "/remove/" + menuId,
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
