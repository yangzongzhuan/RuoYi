// 树结构初始化加载
var setting = {
	check:{enable:true,nocheckInherit:true,chkboxType:{"Y":"ps","N":"ps"}},
	view:{selectedMulti:false,nameIsHTML: true},
	data:{simpleData:{enable:true},key:{title:"title"}},
	callback:{
		beforeClick: function (treeId, treeNode, clickFlag) {
			var menuTrees = $.fn.zTree.getZTreeObj(treeId);
			menuTrees.checkNode(treeNode, !treeNode.checked, true, true);
			return false;
		}
	}
}, menuTrees, loadTree = function(){
	$.get(ctx + "system/menu/roleMenuTreeData", function(data) {
		menuTrees = $.fn.zTree.init($("#menuTrees"), setting, data); //.expandAll(true);
	}, null, null, "正在加载，请稍后...");
};loadTree();

$("#form-role-add").validate({
	rules:{
		roleName:{
			required:true,
			remote: {
                url: ctx + "system/role/checkRoleNameUnique",
                type: "post",
                dataType: "json",
                data: {
                	"roleName" : function() {
                        return $.trim($("#roleName").val());
                    }
                },
                dataFilter: function(data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
		roleKey:{
			required:true,
		},
		roleSort:{
			required:true,
		},
	},
	messages: {
        "roleName": {
            remote: "角色已经存在"
        }
    },
	submitHandler:function(form){
		add();
	}
});

function getCheckeds() {
    var menuIds = "";
    var treeNodes = menuTrees.getCheckedNodes(true);
    for (var i = 0; i < treeNodes.length; i++) {
        if (0 == i) {
        	menuIds = treeNodes[i].id;
        } else {
        	menuIds += ("," + treeNodes[i].id);
        }
    }
    return menuIds;
}

function add() {
	var roleName = $("input[name='roleName']").val();
	var roleKey = $("input[name='roleKey']").val();
	var roleSort = $("input[name='roleSort']").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	var menuIds = getCheckeds();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "system/role/save",
		data : {
			"roleName": roleName,
			"roleKey": roleKey,
			"roleSort": roleSort,
			"status": status,
			"remark": remark,
			"menuIds": menuIds
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("新增成功,正在刷新数据请稍后……",{icon:1,time: 500,shade: [0.1,'#fff']},function(){
					$.parentReload();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}
