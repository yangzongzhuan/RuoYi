$("#form-post-edit").validate({
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
		edit();
	}
});

/** 岗位管理-修改岗位 */
function edit() {
	_ajax_save(ctx + "system/post/save", $("#form-post-edit").serialize());
}