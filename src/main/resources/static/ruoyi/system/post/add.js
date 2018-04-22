$("#form-post-add").validate({
	rules:{
		postCode:{
			required:true,
		},
		postName:{
			required:true,
		},
		postSort:{
			required:true,
		},
	},
	submitHandler:function(form){
		add();
	}
});

/** 岗位管理-新增岗位 */
function add() {
	_ajax_save("/system/post/save", $("#form-post-add").serialize());
}