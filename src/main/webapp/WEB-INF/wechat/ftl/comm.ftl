<#--
* 通用宏定义。
* @author yangyong.
* @ since 2014-05-21.
-->
<#--错误处理加载-->
<#macro error_dialog e>
var d = $('<div/>').dialog({
	title:'警告',
	width:400,
	height:300,
	modal:true,
	content:${e}.responseText,
	buttons:[{
		text:'关闭',
		iconCls:'icon-cancel',
		handler:function(){
			d.dialog('close');
		}
	}],
	onClose:function(){
		$(this).dialog('destroy');
	}
});
</#macro>