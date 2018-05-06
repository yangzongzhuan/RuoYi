$("#form-dept-add").validate({
	rules:{
		deptName:{
			required:true,
			remote: {
                url: ctx + "system/dept/checkDeptNameUnique",
                type: "post",
                dataType: "json",
                data: {
                	"deptName" : function() {
                        return $("input[name='deptName']").val();
                    }
                },
                dataFilter: function(data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
		orderNum:{
			required:true,
			digits:true
		},
	},
	messages: {
        "deptName": {
            remote: "部门已经存在"
        }
    },
	submitHandler:function(form){
		update();
	}
});

function update() {
	_ajax_save(ctx + "system/dept/save", $("#form-dept-add").serialize());
}

/*部门管理-新增-选择父部门树*/
function selectDeptTree() {
    var deptId = $("#treeId").val();
	var url = ctx + "system/dept/selectDeptTree/" + deptId;
    layer_show("选择部门", url, '380', '380');
}