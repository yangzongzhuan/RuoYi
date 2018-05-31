$("#form-dict-add").validate({
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
		add();
	}
});

function add() {
	_ajax_save(ctx + "system/dict/data/save", $("#form-dict-add").serialize());
}