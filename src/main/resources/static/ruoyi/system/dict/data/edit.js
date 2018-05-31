$("#form-dict-edit").validate({
	rules:{
		dictLabel:{
			required:true,
		},
		dictValue:{
			required:true,
		},
		dictSort:{
			required:true,
			digits:true
		},
	},
	submitHandler:function(form){
		update();
	}
});

function update() {
	_ajax_save(ctx + "system/dict/data/save", $("#form-dict-edit").serialize());
}