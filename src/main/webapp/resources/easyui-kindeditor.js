/**
 * easyui-kindeditor整合。
 */
(function($,K){
	if(!K) throw "KindEditor未定义！";
	
	function create(target){
		var opts = $.data(target,'kindeditor').options;
		var editor = K.create(target,opts);
		$.data(target,'kindeditor').options.editor = editor;
	};
	
	function setValue(target,value){
		//$(target).html(value);
		K.html(target,value);
	};
	
	$.fn.kindeditor = function(options,param){
		if(typeof options == 'string'){
			var method = $.fn.kindeditor.methods[options];
			if(method){
				return method(this,param);
			}
		}
		options = options || {};
		return this.each(function(){
			var state = $.data(this,'kindeditor');
			if(state){
				$.extend(state.options, options);
			}else{
				state = $.data(this,'kindeditor',{
					options : $.extend({}, $.fn.kindeditor.defaults, $.fn.kindeditor.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	$.fn.kindeditor.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target, []));
	};
	
	$.fn.kindeditor.methods = {
			editor : function(jq){
				return $.data(jq[0], 'kindeditor').options.editor;
			},
			setValue : function(jq,value){
				 return jq.each(function(){
					 setValue(this,value);
				 });
			}
	};
	
	$.fn.kindeditor.defaults = {
			resizeType : 1,
			allowPreviewEmoticons : true,
			//allowImageUpload : true,
			//allowFileManager : true,
			items : ['source','|','fontname','fontsize','|','forecolor','hilitecolor','bold','italic','underline','removeformat','|',
			            'justifyleft','justifycenter','justifyright','insertorderedlist','insertunorderedlist','|',
			            'emoticons','image','link'],
			afterChange:function(){
				this.sync();
			}
	};
	
	$.parser.plugins.push("kindeditor");
})(jQuery,KindEditor);