$("#form-user-edit").validate({
	rules:{
		userName:{
			required:true,
		},
		deptName:{
			required:true,
		},
		email:{
			required:true,
            email:true,
            remote: {
                url: ctx + "system/user/checkEmailUnique",
                type: "post",
                dataType: "json",
                data: {
                	"userId": function() {
                        return $("input[name='userId']").val();
                    },
        			"email": function() {
                        return $("input[name='email']").val();
                    }
                },
                dataFilter: function (data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
		phonenumber:{
			required:true,
            remote: {
                url: ctx + "system/user/checkPhoneUnique",
                type: "post",
                dataType: "json",
                data: {
                	"userId": function() {
                        return $("input[name='userId']").val();
                    },
        			"phonenumber": function() {
                        return $("input[name='phonenumber']").val();
                    }
                },
                dataFilter: function (data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
	},
	messages: {
		"email": {
            remote: "Email已经存在"
        },
		"phonenumber":{
        	remote: "手机号码已经存在"
		}
    },
	submitHandler:function(form){
		update();
	}
});

function update() {
	var userId = $("input[name='userId']").val();
	var deptId = $("input[name='deptId']").val();
	var userName = $("input[name='userName']").val();
	var email = $("input[name='email']").val();
	var phonenumber = $("input[name='phonenumber']").val();
	var sex = $("input[name='sex']:checked").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var roleIds = $.getCheckeds("role");
	var postIds = $.getSelects("post");
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "system/user/save",
		data : {
			"userId": userId,
			"deptId": deptId,
			"userName": userName,
			"email": email,
			"phonenumber": phonenumber,
			"sex": sex,
			"status": status,
			"roleIds": roleIds,
			"postIds": postIds
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", "error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改成功,正在刷新数据请稍后……",{icon:1,time: 500,shade: [0.1,'#fff']},function(){
					$.parentReload();
				});
			} else {
				$.modalAlert(data.msg, "error");
			}
		}
	});
}

/*用户管理-修改-选择部门树*/
function selectDeptTree() {
	var deptId = $("#treeId").val();
    var url = ctx + "system/dept/selectDeptTree/" + deptId;
    layer_show("选择部门", url, '380', '380');
}
