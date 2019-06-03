/*this is basic form validation using for validation person's basic information author:Clara Guo data:2017/07/20*/
$(document).ready(function(){
	$.validator.setDefaults({       
		  submitHandler: function(form) {    
		 		form.submit();    
		}       
	});  
	//手机号码验证身份证正则合并：(^\d{15}$)|(^\d{17}([0-9]|X)$)
	jQuery.validator.addMethod("isPhone",function(value,element){
		var length = value.length;
		var phone=/^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
		return this.optional(element)||(length == 11 && phone.test(value));
	},"请填写正确的11位手机号");
	//电话号码验证
	jQuery.validator.addMethod("isTel",function(value,element){
		var tel = /^(0\d{2,3}-)?\d{7,8}$/g;//区号3,4位,号码7,8位
		return this.optional(element) || (tel.test(value));
	},"请填写正确的座机号码");
	//姓名校验
	jQuery.validator.addMethod("isName",function(value,element){
		var name=/^[\u4e00-\u9fa5]{2,6}$/;
		return this.optional(element) || (name.test(value));
	},"姓名只能用汉字,长度2-4位");
	//校验用户名
	jQuery.validator.addMethod("isUserName",function(value,element){
		var userName=/^[a-zA-Z0-9]{2,13}$/;
		return this.optional(element) || (userName).test(value);
	},'请输入数字或者字母,不包含特殊字符');
	
	//校验身份证
	jQuery.validator.addMethod("isIdentity",function(value,element){
		var id= /^(\d{15}$|^\d{18}$|^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"请输入正确的15或18位身份证号,末尾为大写X");
	//校验出生日期
	jQuery.validator.addMethod("isBirth",function(value,element){
		var birth = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		return this.optional(element) || (birth).test(value);
	},"出生日期格式示例2000-01-01");
	//校验IP地址
	jQuery.validator.addMethod("isIp",function(value,element){
		var ip = /^(?:(?:2[0-4][0-9]\.)|(?:25[0-5]\.)|(?:1[0-9][0-9]\.)|(?:[1-9][0-9]\.)|(?:[0-9]\.)){3}(?:(?:2[0-4][0-9])|(?:25[0-5])|(?:1[0-9][0-9])|(?:[1-9][0-9])|(?:[0-9]))$/;
		return this.optional(element) || (ip).test(value);
	},"IP地址格式示例127.0.0.1");
	jQuery.validator.addMethod("notEqual", function(value, element, param) {
        return value != param;
    }, $.validator.format("输入值不允许为{0}"));
	jQuery.validator.addMethod("gt", function(value, element, param) {
        return value > param;
    }, $.validator.format("输入值必须大于{0}"));
	//校验新旧密码是否相同
	jQuery.validator.addMethod("isdiff",function(){
		var p1=$("#pwdOld").val();
		var p2=$("#pwdNew").val();
		if(p1==p2){
			return false;
		}else{
			 return true;
		}
		});
	//校验新密码和确认密码是否相同
	jQuery.validator.addMethod("issame",function(){
		var p3=$("#confirm_password").val();
		var p4=$("#pwdNew").val();
		if(p3==p4){
			return true;
		}else{
			 return false;
		}
		});
	//校验基础信息表单
	$("#basicInfoForm").validate({
		errorElement:'span',
		errorClass:'help-block error-mes',
		rules:{
			name:{
				required:true,
				isName:true
			},
			sex:"required",
			birth:"required",
            mobile:{
				required:true,
				isPhone:true
			},
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			name:{
				required:"请输入中文姓名",
				isName:"姓名只能为汉字"
			},
			sex:{
				required:"请输入性别"
			},
			birth:{
				required:"请输入出生年月"
			},
            mobile:{
				required:"请输入手机号",
				isPhone:"请填写正确的11位手机号"
			},
			email:{
				required:"请输入邮箱",
				email:"请填写正确的邮箱格式"
			}
		},
	
		errorPlacement:function(error,element){
			element.next().remove();
			element.closest('.gg-formGroup').append(error);
		},
		
		highlight:function(element){
			$(element).closest('.gg-formGroup').addClass('has-error has-feedback');
		},
		success:function(label){
			var el = label.closest('.gg-formGroup').find("input");
			el.next().remove();
			label.closest('.gg-formGroup').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler:function(form){
			alert("保存成功!");
		}
	});
	
	//校验修改密码表单
	$("#modifyPwd").validate({
		onfocusout: function(element) { $(element).valid()},
		 debug:false, //表示校验通过后是否直接提交表单
		 onkeyup:false, //表示按键松开时候监听验证
		rules:{
			pwdOld:{
				required:true,
				minlength:6
			},
            pwdNew:{
			   required:true,
			   minlength:6,
			   isdiff:true,
			   //issame:true,
		   },
			confirm_password:{
			  required:true,
			  minlength:6,
			  issame:true,
			}
		  
		   },
		messages:{
			 	pwdOld : {
					 required:'必填',
					 minlength:$.validator.format('密码长度要大于6')
				},
            	pwdNew:{
				   required:'必填',
				   minlength:$.validator.format('密码长度要大于6'),
				   isdiff:'原密码与新密码不能重复',
				  
			   },
				confirm_password:{
				   required:'必填',
				   minlength:$.validator.format('密码长度要大于6'),
				   issame:'新密码要与确认新密码一致',
				}
		
		},
		errorElement:"mes",
		errorClass:"gg-star",
		errorPlacement: function(error, element) 
		{ 
			element.closest('.gg-formGroup').append(error);

		}
	});
});