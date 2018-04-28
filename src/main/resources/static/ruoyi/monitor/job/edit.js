$("#form-job-edit").validate({
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
		edit();
	}
});

function edit() {
	_ajax_save(ctx + "/monitor/job/save", $("#form-job-edit").serialize());
}