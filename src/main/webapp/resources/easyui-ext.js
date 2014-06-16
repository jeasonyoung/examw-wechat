/**
 * easyui扩展
 */
//easyui form 扩展
$.extend($.fn.form.methods,{
	serialize:function(jq){
		var arrayValue = $(jq[0]).serializeArray();
		var json = {};
		$.each(arrayValue,function(){
			var item = this;
			if(json[item["name"]]){
				json[item["name"]] = json[item["name"]] + "," + item["value"];
			}else{
				json[item["name"]] = item["value"];
			}
		});
		return json;
	},
	getValue:function(jq,name){
		var jsonValue = $(jq[0]).form("serialize");
		return jsonValue[name];
	},
	setValue:function(jq,data){
		return jq.each(function(){
			$(this).form("load",data);
		});
	}
});
//easyui验证扩展
$.extend($.fn.validatebox.defaults.rules,{
	idcard:{//验证身份证
		validator:function(value){
			return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
		},
		message:"身份证号码格式不正确"
	},
	phone:{//验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : "格式不正确,请使用下面格式:020-88888888"
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value);
		},
		message : "手机号码格式不正确"
	},
	chinese : {// 验证中文
		validator : function(value) {
			return /^[\Α-\￥]+$/i.test(value);
		},
		message : "请输入中文"
	},
	english : {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : "请输入英文"
	},
	account:{//验证账号
		validator : function(value) {
			return /^[A-Za-z0-9|\-|_]+$/i.test(value);
		},
		message : "请输入字母、数字、下划线或连结线字符"
	},
	zip : {// 验证邮政编码
		validator : function(value) {
		return /^[1-9]\d{5}$/i.test(value);
		},
		message : "邮政编码格式不正确"
	},
	ip : {// 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : 'IP地址格式不正确'
	},
	same:{
		validator : function(value, param){
			if($("#"+param[0]).val() != "" && value != ""){
				return $("#"+param[0]).val() == value;
			}else{
				return true;
			}
		},
		message : '两次输入的密码不一致！'
	}
});