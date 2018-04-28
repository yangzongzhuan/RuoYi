$("#form-job-add").validate({
	rules:{
		jobName:{
			required:true,
		},
		jobGroup:{
			required:true,
		},
		cronExpression:{
			required:true,
		},
	},
	submitHandler:function(form){
		add();
	}
});

function add() {
	_ajax_save(ctx + "/monitor/job/save", $("#form-job-add").serialize());
}