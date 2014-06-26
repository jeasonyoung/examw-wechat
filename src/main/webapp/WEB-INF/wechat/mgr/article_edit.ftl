<#--资讯文档编辑-->
<#include "ftl/comm.ftl"/>
<#assign module = "mgr_article"/>
<#assign edit = "${module}_edit" />
<#assign form = "${edit}_form" />
<#macro script>
<script type="text/javascript">
<!--
$(function(){
	var current_catalog_select = false;
	//catalog combobox
	var catalog_cbb = $("#${form} input[name='catalogId']").combobox({
		url:"<@s.url '/settings/catalog/all'/>",
		valueField:"id",
		textField:"name",
		required:true,
		onLoadError:function(e){
			<@error_dialog "e"/>
		},
		onLoadSuccess:function(){
			var c_id = "${CURRENT_CATALOG_ID}";
			if(c_id.trim() == "") return;
			catalog_cbb.combobox("setValue",c_id);
		},
		onSelect:function(record){
			current_catalog_select = true;
			exam_cbb.combobox("clear");
			exam_cbb.combobox("reload", exam_url + record.id);
		}
	});
	var exam_url = "<@s.url '/settings/exam/all'/>?catalogId=";
	//exam combobox
	var exam_cbb = $("#${form} input[name='examId']").combobox({
		url:exam_url +"${CURRENT_CATALOG_ID}",
		valueField:"id",
		textField:"name",
		required:true,
		onLoadError:function(e){
			<@error_dialog "e"/>
		},
		onLoadSuccess:function(){
			if(current_catalog_select) return;
			var e_id = "${CURRENT_EXAM_ID}";
			if(e_id.trim() == "") return;
			exam_cbb.combobox("setValue",e_id);
		}
	});
	//
	<#nested/>
});
//-->
</script>
</#macro>
<#--表单-->
<#macro edit_frm>
<form id="${form}" method="POST" style="padding:10px;">
	<div style="float:left;margin-top:2px;width:100%;">
		<div style="float:left;">
			<label style="width:75px;">所属考试：</label>
			<input name="catalogId" type="text" style="width:198px;"/>
			<input name="examId" type="text" style="width:198px;"/>
		</div>
		<div style="float:left;">
			<label style="width:75px;">所在省市：</label>
			<input name="provinceId" type="text" class="easyui-combobox"  data-options="url:'<@s.url '/settings/province/all'/>',valueField:'id',textField:'name',required:true" style="width:198px;"/>
		</div>
	</div>
	<div style="float:left;margin-top:12px;width:100%;">
		<div style="float:left;">
			<label style="width:75px;">标题：</label>
			<input name="title" type="text" class="easyui-validatebox" data-options="required:true" style="width:396px;"/>
			<input type="hidden" name="id" />
			<input type="hidden" name="type" value="${CURRENT_TYPE_VALUE}"/>
		</div>
		<div style="float:left;" title="(选填)" class="easyui-tooltip" data-options="position:'right'">
			<label style="width:75px;">作者：</label>
			<input name="description" type="text" style="width:196px;"/>
		</div>
	</div>
	<#nested/>
</form>
</#macro>

