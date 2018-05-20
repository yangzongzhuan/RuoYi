$("#form-config-add").validate({
	rules:{
		configKey:{
			required:true,
			remote: {
                url: ctx + "system/config/checkConfigKeyUnique",
                type: "post",
                dataType: "json",
                data: {
                	"configKey" : function() {
                        return $("input[name='configKey']").val();
                    }
                },
                dataFilter: function(data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
		configName:{
			required:true
		},
		configValue:{
			required:true
		},
	},
	messages: {
        "configKey": {
            remote: "参数键名已经存在"
        }
    },
	submitHandler:function(form){
		add();
	}
});

function add() {
    _ajax_save(ctx + "system/config/save", $('#form-config-add').serialize());
}