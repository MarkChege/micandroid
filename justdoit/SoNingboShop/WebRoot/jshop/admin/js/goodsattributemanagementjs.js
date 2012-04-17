
/**
 * Global variables
 */
var session="true";
var rid="";
var globalrjson="";//post json data to backstage server
/*===========================================Gorgeous split-line==============================================*/

/**
 * Function
 */
/*
 * To obtain random rid
 */
function getIdforradom(){
	var myDate=new Date();
	rid=myDate.getSeconds().toString()+myDate.getMilliseconds().toString();
}
/*
 * Delete Page elements According to rid 
 */
function delParamPChild(rid){
	$('#add'+rid).remove();
}
/*===========================================Gorgeous split-line==============================================*/

/**
 * Required to initialize the page data
 */
/*
 * Get Goods Type for select elements
 */
$(function(){
	$.post("findGoodsTypeTNForSelect.action",function(data){
		if(data.goodstypetnlist!=""){
			var temp="<select id='goodstypetn' name='goodstypetn'>";
			var temp1="</select>";
			$('#goodstypetnselect').append(temp+data.goodstypetnlist+temp1);
		}
	});
});
/*===========================================Gorgeous split-line==============================================*/


/**
 * flexigrid list 
 */
$(function() {
	$("#goodsattributenmanagement").flexigrid( {
		url : 'findAllGoodsAttributeT.action',
		dataType : 'json',
		cache : false,
		colModel : [{ 
			display:'属性名称',
			name:'goodsattributename',
			width:315,
			sortable:true,
			align:'center'
		},{ 
			display:'属性类型',
			name:'attributeType',
			width:115,
			sortable:true,
			align:'center'
		},{ 
			display:'属性列表',
			name:'attributelist',
			width:415,
			sortable:true,
			align:'center'
		},{ 
			display:'排序',
			name:'sort',
			width:115,
			sortable:true,
			align:'center'
		},{ 
			display:'商品类型',
			name:'goodsTypeName',
			width:215,
			sortable:true,
			align:'center'
		},{ display:'创建时间',
			name:'createtime',
			width:300,
			sortable:true,
			align:'center'
		}],
		buttons : [ {
			name : '添加',
			bclass : 'add',
			onpress : action
		}, {
			name : '编辑',
			bclass : 'edit',
			onpress : action
		},{
			name:'删除',
			bclass:'delete',
			onpress:action
		},{
			separator : true
		}],
		searchitems : [{
			display:'请选择搜索条件',
			name:'sc',
			isdefault : true
		}],
		sortname : "createtime",
		sortorder : "desc",
		usepager : true,
		title : '商品属性列表',
		useRp : true,
		rp : 20,
		rpOptions : [ 5, 20, 40, 100 ],
		showTableToggleBtn : true,
		width :'auto',
		height :'auto',
		pagestat : '显示{from}到{to}条，共{total}条记录',
		procmsg : '正在获取数据，请稍候...',
		checkbox:true
	});

	function action(com, grid) {
		if (com == '添加') {
			window.location.href="addgoodsattribute.jsp?session="+session+"#goods";
			return;
		}else if(com=='编辑'){
			if($('.trSelected',grid).length==1){
				var str="";
				$(".trSelected td:nth-child(6) div", $('#goodsattributenmanagement')).each(function(i){
					str=this.innerHTML;
				});
				jConfirm('确定编辑此项吗?','信息提示',function(r){
					if(r){
					window.location.href="addgoodsattribute.jsp?session="+session+"#goods&goodsTypeName="+str;
					return;
				}
				});
			}else{
				jAlert('请选择一条信息', '信息提示');
				return false;
			}
		}else if(com='删除'){
			if($('.trSelected',grid).length>0){
				jConfirm('确定删除此项吗?', '信息提示', function(r) {
					if (r) {
						var str="";
						$('.trSelected',grid).each(function(){
							str+=this.id.substr(3)+",";
						});
						$.post("DelGoodsAttributeT.action",{"goodsattributeid":str},function(data){
							$('#goodsattributenmanagement').flexReload();
						});
					}
				});
				return;
			}else{
				jAlert('请选择要删除的信息!','信息提示');
				return false;
			}
		}
	}
});
/*===========================================Gorgeous split-line==============================================*/



/**
 * Add Function
 */
$(function(){
	$('#addparam').click(function(){
		getIdforradom();
		var html="<tr id='add"+rid+"'>" +
				"<td class='title'><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='paramlistname"+rid+"' name='paramlistname"+rid+"' value='' type='text'/></div></div></div></div></td>"+
				"<td><div class='form' style='padding-top:25px;'><div class='fields'><div class='field field-first'><div class='typeinput'><select class='attribute' id='attributetype"+rid+"' name='attributetype"+rid+"'><option value='0'>筛选项</option><option value='1'>输入项</option></select></div></div></div></div></td>"+
				"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='attributelists"+rid+"' name='attributelists'"+rid+"' value='' type='text'></input></div></div></div></div></td>"+
				"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='paramlistsort"+rid+"' name='paramlistsort"+rid+"' value='' type='text'/></div></div></div></div></td>"+
				"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input  class='attribute' id='delbutton"+rid+"' name='delbutton"+rid+"' type='button' value='删除' onClick='delParamPChild("+rid+")'/></div></div></div></div></td>"+
				"</tr>";
		$('.table tbody').append(html);
	});
	
	
	$('#submitparam').click(function(){
		var goodstypetn=$('#goodstypetn').val();
		if(goodstypetn==0){
			jAlert('商品类型必须选择','信息提示');
			return false;
		}
		var goodsTypeId=$('#goodstypetn').val();
		var goodsTypeName=$('#goodstypetn').find("option:selected").text();
		var paramlistname="paramlistname";
		var attributetype="attributetype";
		var attributelists="attributelists";
		var paramlistsort="paramlistsort";
		var delbutton="delbutton";
		var goodsattributename;
		var attributeType;
		var attributelist;
		var sort;
		var attributeIndex=1;
		var addflag=0;
		var sucflag=false;
		var sub="";
		var rjson="";
		$(".attribute").each(function(){
			if(this.name.substring(0,13)==paramlistname){
				if(this.value==""){
					jAlert('商品属性名称必须填写', '信息提示');
					return false;
				}
				goodsattributename=this.value;
				addflag=1;
				return true;
			}
			if(this.name.substring(0,13)==attributetype){
				if(this.value==""){
					jAlert('商品类型必须选择', '信息提示');
					return false;
				}
				attributeType=this.value;
				addflag=2;
				return true;
			}
			if(this.name.substring(0,14)==attributelists){
				if(this.value==""){
					jAlert('商品可选项必须填写', '信息提示');
					return false;
				}
				attributelist=this.value;
				addflag=3;
				return true;
			}
			if(this.name.substring(0,13)==paramlistsort){
				if(this.value==""){
					jAlert('商品属性排序必须填写', '信息提示');
					return false;
				}
				sort=this.value;
				addflag=4;
				return true;
			}
			if(this.name.substring(0,9)==delbutton){
				if(addflag==4){
					sub+="{\"goodsattributename\":\""+goodsattributename+"\",\"attributeType\":\""+attributeType+"\",\"attributelist\":\""+attributelist+"\",\"sort\":\""+sort+"\"}-";
					addflag=0;
					goodsattributename="";
					attributeType="";
					attributelist="";
					sort="";
						
				}
			}
		});
		rjson=sub.toString().substring(0, sub.length-1);
		
		//插入商品属性数据
		//"goodsattributename":goodsattributename,"goodsTypeId":goodsTypeId,"goodsTypeName":goodsTypeName,"attributeType":attributeType,"attributelist":attributelist,"sort":sort,"attributeIndex":attributeIndex
		$.post("addGoodsAttributeT.action",{"rjson":rjson,"goodsTypeId":goodsTypeId,"goodsTypeName":goodsTypeName,"attributeIndex":attributeIndex},function(data){
			if(data.sucflag){
				sucflag=true;
				window.location.href="goodsattributemanagement.jsp?session="+session+"#goods";
				
			}else{
				sucflag=false;
			}
		});
		
	});
});
/*===========================================Gorgeous split-line==============================================*/


/**
 * Update Function
 */
$(function(){
	var goodsTypeName=$.query.get('goodsTypeName');
	if(goodsTypeName==""){
		return false;
	}
	//这里需要绑定商品类型。有待商榷
	$.post("findGoodsAttributeTByGoodsTypeName.action",{"goodsTypeName":goodsTypeName},function(data){
		if(data.beanlist.length>0){
			var html="";
			var rid="";
			
			$('#goodstypetn').val(data.beanlist[0].goodsTypeId);
			$.each(data.beanlist,function(n,value){
				if(value.attributeType=="1"){
					rid=value.goodsattributeid;
					html+="<tr id='add"+rid+"'>" +
					"<td class='title'><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='paramlistname"+rid+"' name='paramlistname"+rid+"' value='"+value.goodsattributename+"'/></div></div></div></div></td>"+
					"<td><div class='form' style='padding-top:25px;'><div class='fields'><div class='field field-first'><div class='typeinput'><select class='attribute' id='attributetype"+rid+"' name='attributetype"+rid+"'><option value='0'>筛选项</option><option value='1' selected>输入项</option></select></div></div></div></div></td>"+
					"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='attributelists"+rid+"' name='attributelists'"+rid+"' value='"+value.attributelist+"'></input></div></div></div></div></td>"+
					"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='paramlistsort"+rid+"' name='paramlistsort"+rid+"' value='"+value.sort+"'/></div></div></div></div></td>"+
					"<input type='hidden' class='attribute' id='paramattributeid' name='paramattributeid' value='"+value.goodsattributeid+"'></input>"+
					"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input  class='attribute' id='delbutton"+rid+"' name='delbutton"+rid+"' type='button' value='删除' onClick='delParamPChild("+rid+")' disabled /></div></div></div></div></td>"+
					"</tr>";
				}else{
					rid=value.goodsattributeid;
					html+="<tr id='add"+rid+"'>" +
					"<td class='title'><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='paramlistname"+rid+"' name='paramlistname"+rid+"' value='"+value.goodsattributename+"'/></div></div></div></div></td>"+
					"<td><div class='form' style='padding-top:25px;'><div class='fields'><div class='field field-first'><div class='typeinput'><select class='attribute' id='attributetype"+rid+"' name='attributetype"+rid+"'><option value='0' selected>筛选项</option><option value='1' >输入项</option></select></div></div></div></div></td>"+
					"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='attributelists"+rid+"' name='attributelists'"+rid+"' value='"+value.attributelist+"'></input></div></div></div></div></td>"+
					"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input class='attribute' id='paramlistsort"+rid+"' name='paramlistsort"+rid+"' value='"+value.sort+"'/></div></div></div></div></td>"+
					"<input type='hidden' class='attribute' id='paramattributeid' name='paramattributeid' value='"+value.goodsattributeid+"'></input>"+
					"<td><div class='form'><div class='fields'><div class='field field-first'><div class='typeinput'><input  class='attribute' id='delbutton"+rid+"' name='delbutton"+rid+"' type='button' value='删除' onClick='delParamPChild("+rid+")' disabled /></div></div></div></div></td>"+
					"</tr>";
				}
				
			});
			$('.table tbody').append(html);
			//显示修改按钮
			$('#modify').show();
			$('#modifyparam').show();
			//隐藏增加按钮
			$('#submit').hide();
			$('#submitparam').hide();
			$('#addparam').hide();
		}
		
	});
	
	//更新商品属性，完善商品品牌处理
	/**
	 * 修改商品类型数据
	 */
	
	$('#modifyparam').click(function(){
		var goodstypetn=$('#goodstypetn').val();
		if(goodstypetn==0){
			jAlert('商品类型必须选择', '信息提示');
			return false;
		}
		var goodsTypeId=$('#goodstypetn').val();
		var goodsTypeName=$('#goodstypetn').find("option:selected").text();
		var paramlistname="paramlistname";
		var attributetype="attributetype";
		var attributelists="attributelists";
		var paramlistsort="paramlistsort";
		var paramattributeid="paramattributeid";
		var delbutton="delbutton";
		var goodsattributename;
		var attributeType;
		var attributelist;
		var goodsattributeid;
		var sort;
		var attributeIndex=1;
		var addflag=0;
		var sucflag=true;
		var sub="";
		var rjson="";
		$(".attribute").each(function(){
			if(this.name.substring(0,13)==paramlistname){
				if(this.value==""){
					jAlert('商品属性名称必须填写', '信息提示');
					sucflag=false;
				}
				goodsattributename=this.value;
				addflag=1;
			}
			if(this.name.substring(0,13)==attributetype){
				if(this.value==""){
					jAlert('商品类型必须选择', '信息提示');
					sucflag=false;
				}
				attributeType=this.value;
				addflag=2;
			}
			if(this.name.substring(0,14)==attributelists){
				if(this.value==""){
					jAlert('商品可选项必须填写', '信息提示');
					sucflag=false;
				}
				attributelist=this.value;
				addflag=3;
			}
			if(this.name.substring(0,13)==paramlistsort){
				if(this.value==""){
					jAlert('商品属性排序必须填写', '信息提示');
					sucflag=false;
				}
				sort=this.value;
				addflag=4;
			}
			if(this.name.substring(0,16)==paramattributeid){
				if(this.value==""){
					jAlert('商品属性ID异常', '信息提示');
					sucflag=false;
				}
				goodsattributeid=this.value;
				addflag=5;
			}
			if(this.name.substring(0,9)==delbutton){
				if(addflag==5){
					sub+="{\"goodsattributename\":\""+goodsattributename+"\",\"attributeType\":\""+attributeType+"\",\"attributelist\":\""+attributelist+"\",\"sort\":\""+sort+"\",\"goodsattributeid\":\""+goodsattributeid+"\"}-";
					addflag=0;
					goodsattributename="";
					attributeType="";
					attributelist="";
					goodsattributeid="";
					sort="";
						
				}
			}
		});
		
		if(sucflag==true){
			rjson=sub.toString().substring(0, sub.length-1);
			$.post("UpdateGoodsAttributeT.action",{"rjson":rjson,"goodsTypeId":goodsTypeId,"goodsTypeName":goodsTypeName,"attributeIndex":attributeIndex},function(data){
				if(data.sucflag){
					sucflag=true;
					window.location.href="goodsattributemanagement.jsp?session="+session+"#goods";
				}else{
					sucflag=false;
				}
			});
		}else{
			return false;
		}
		
	});
});

/*===========================================Gorgeous split-line==============================================*/







