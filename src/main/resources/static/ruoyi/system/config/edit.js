$("#form-config-edit").validate({
	rules:{
		configKey:{
			required:true,
			remote: {
                url: ctx + "system/config/checkConfigKeyUnique",
                type: "post",
                dataType: "json",
                data: {
                	"configId": function() {
                	    return $("input[name='configId']").val();
                	},
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
		update();
	}
});
function update() {
    _ajax_save(ctx + "system/config/save", $('#form-config-edit').serialize());
}