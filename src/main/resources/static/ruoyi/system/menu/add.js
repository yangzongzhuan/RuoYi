$("#form-menu-add").validate({
	rules:{
		menuName:{
			required:true,
			remote: {
                url: ctx + "system/menu/checkMenuNameUnique",
                type: "post",
                dataType: "text",
                data: {
                	"menuName" : function() {
                        return $.trim($("#menuName").val());
                    }
                },
                dataFilter: function(data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
		orderNum:{
			required:true,
			digits:true
		},
	},
	messages: {
        "menuName": {
            remote: "菜单已经存在"
        }
    },
	submitHandler:function(form){
		add();
	}
});

$(function() {
	$("input[name='icon']").focus(function() {
        $(".icon-drop").show();
    });
	$("#form-menu-add").click(function(event) {
	    var obj = event.srcElement || event.target;
	    if (!$(obj).is("input[name='icon']")) {
	    	$(".icon-drop").hide();
	    }
	});
	$(".icon-drop").find(".ico-list i").on("click", function() {
		$('#icon').val($(this).attr('class'));
    });
	$('input').on('ifChecked', function(event){  
		var menuType = $(event.target).val();
		if (menuType == "M") {
            $("#url").parents(".form-group").hide();
            $("#perms").parents(".form-group").hide();
            $("#icon").parents(".form-group").show();
        } else if (menuType == "C") {
        	$("#url").parents(".form-group").show();
            $("#perms").parents(".form-group").show();
            $("#icon").parents(".form-group").hide();
        } else if (menuType == "F") {
        	$("#url").parents(".form-group").hide();
            $("#perms").parents(".form-group").show();
            $("#icon").parents(".form-group").hide();
        }
	});  
});

function add() {
	_ajax_save(ctx + "system/menu/save", $("#form-menu-add").serialize());
}

/*菜单管理-新增-选择菜单树*/
function selectMenuTree() {
	var menuId = $("#treeId").val();
	if(menuId > 0)
	{
		var url = ctx + "system/menu/selectMenuTree/" + menuId;
        layer_show("选择菜单", url, '380', '380');
	}
	else
	{
        var url = ctx + "system/menu/selectMenuTree/1";
        layer_show("选择菜单", url, '380', '380');
    }
}