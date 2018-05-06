$("#form-dict-add").validate({
	rules:{
		dictName:{
			required:true,
		},
		dictType:{
			required:true,
			minlength: 5,
			remote: {
                url: ctx + "system/dict/checkDictTypeUnique",
                type: "post",
                dataType: "json",
                data: {
                	name : function() {
                        return $.trim($("#dictType").val());
                    }
                },
                dataFilter: function(data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
	},
	messages: {
        "dictType": {
            remote: "该字典类型已经存在"
        }
    },
	submitHandler:function(form){
		add();
	}
});

function add() {
	_ajax_save(ctx + "system/dict/save", $("#form-dict-add").serialize());
}