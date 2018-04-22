$("#form-dict-edit").validate({
	rules:{
		dictName:{
			required:true,
		},
		dictType:{
			required:true,
			minlength: 5,
			remote: {
                url: "/system/dict/checkDictTypeUnique",
                type: "post",
                dataType: "text",
                data: {
                	dictId : function() {
                        return $.trim($("#dictId").val());
                    },
                	dictType : function() {
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
		update();
	}
});

function update() {
	_ajax_save("/system/dict/save", $("#form-dict-edit").serialize());
}