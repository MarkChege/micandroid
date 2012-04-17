
/**
 * Global variables
 */
var session ="true";
/*===========================================Gorgeous split-line==============================================*/

/**
 * Required to initialize the page data
 */
/**
 * 读取商品一级分类
 */
$(function() {
	$('#addfl').show();
	$('#modfl').hide();
	var goodsCategoryTid = $.query.get('goodsCategoryTid');
	if (goodsCategoryTid == "") {
		$("#fater").hide();
		$.post("findGoodsCategoryByGradeZeroone.action", function(data) {
			if (data.sucflag) {
				if (data.goodscategoryzero == "") {
					$('#parentId').append("<option value='0'>顶级分类</option>");
					$('#parentId1').hide();
				} else {
					$('#parentId').append(data.goodscategoryzero);
					$('#parentId1').hide();
				}

			}
		});
	}else{
		$("#fater").show();
	}

	/**
	 * 级联读取二级栏目
	 */
	$('#parentId').change(function() {
		var parentId = $('#parentId').val();
		if (parentId != "0" && parentId != "-1") {
			$.post("findGoodsCategoryByGradeTwo.action", function(data) {
				if (data.sucflag) {
					$('#parentId1').html(data.goodscategorytwo);
				}
			});
		}
	});
	/**
	 * 获取商品类型下拉框
	 */
	$.post("findGoodsTypeTNForSelect.action", function(data) {
		if (data.goodstypetnlist != "") {
			var temp = "<select id='goodsTypeId' name='goodsTypeId'>";
			var temp2 = "<option value='1'>通用商品类型</option>";
			var temp1 = "</select>";
			$('#goodstypetnselect').html(temp + data.goodstypetnlist + temp2 + temp1);
		}
	});
	/**
	 * 验证分类选择
	 */
	$("#parentId").change(function() {
		var parentId = $('#parentId').val();
		var parentId1 = $('#parentId1').val();
		var parentId2 = $('#parentId2').val();
		if (parentId == '0') {
			$('#parentId1').hide();
			$('#parentId2').hide();
		} else {
			$('#parentId1').show();
		}
		if (parentId1 == "-1") {
			$('#parentId2').hide();
		}
	});
	
});
/*===========================================Gorgeous split-line==============================================*/

/**
 * flexigrid list 
 */
$(function() {
	$("#goodscategorymanagement").flexigrid( {
		url : 'findAllGoodsCategoryT.action',
		dataType : 'json',
		cache : false,
		colModel : [{
			display : '分类名称',
			name : 'name',
			width : 215,
			sortable : true,
			align : 'center'
		}, {
			display : '上级分类',
			name : 'parentName',
			width : 215,
			sortable : true,
			align : 'center'
		}, {
			display : '分类等级',
			name : 'grade',
			width : 115,
			sortable : true,
			align : 'center'
		}, {
			display : '标示',
			name : 'sign',
			width : 215,
			sortable : true,
			align : 'center'
		}, {
			display : '排序',
			name : 'sort',
			width : 115,
			sortable : true,
			align : 'center'
		}, {
			display : '创建时间',
			name : 'createtime',
			width : 214,
			sortable : true,
			align : 'center'

		}, {
			display : '创建者编号',
			name : 'creatorid',
			width : 200,
			sortable : true,
			align : 'center'
		}, {
			display : '操作',
			name : 'operating',
			width : 200,
			sortable : true,
			align : 'center'
		} ],
		buttons : [ {
			name : '添加',
			bclass : 'add',
			onpress : action
		}, {
			name : '编辑',
			bclass : 'edit',
			onpress : action
		}, {
			name : '删除',
			bclass : 'delete',
			onpress : action
		}, {
			separator : true
		} ],

		searchitems : [ {
			display : '请选择搜索条件',
			name : 'sc',
			isdefault : true
		}, {
			display : '分类名称',
			name : 'name',
			isdefault : true
		}, {
			display : '分类等级',
			name : 'grade',
			isdefault : true
		} ],
		sortname : "createtime",
		sortorder : "desc",
		usepager : true,
		title : '商品分类列表',
		useRp : true,
		rp : 20,
		rpOptions : [ 5, 20, 40, 100 ],
		showTableToggleBtn : true,
		width : 'auto',
		height : 'auto',
		pagestat : '显示{from}到{to}条，共{total}条记录',
		procmsg : '正在获取数据，请稍候...',
		checkbox : true
	});

	function action(com, grid) {
		if (com == '添加') {
			window.location.href = "addgoodscategory.jsp?session="+session+"#goods";
			return;
		} else if (com == '编辑') {
			if ($('.trSelected', grid).length == 1) {
				jConfirm('确定编辑此项吗?', '信息提示', function(r) {
					if (r) {
						var str = $('.trSelected', grid)[0].id.substr(3);
						window.location.href = "addgoodscategory.jsp?session="+session+"#goods&goodsCategoryTid=" + str;
						return;
					}
				});
			} else {
				jAlert('请选择一条信息', '信息提示');
				return false;
			}
		} else if (com == '删除') {
			if ($('.trSelected', grid).length > 0) {
				jConfirm('确定删除此项吗?', '信息提示', function(r) {
					if (r) {
						var str = "";
						$('.trSelected', grid).each(function() {
							str += this.id.substr(3) + ",";
						});
						$.post("delGoodscategoryT.action", {
							"goodsCategoryTid" : str
						}, function(data) {
							$('#goodscategorymanagement').flexReload();
						});
					}
				});
				return;
			} else {
				jAlert('请选择要删除的信息!', '信息提示');
				return false;
			}
			
		}

	}

});
/*===========================================Gorgeous split-line==============================================*/
/**
 * Add Function
 */

/**
 * 增加商品分类
 */
$(function() {
	$('#submit').click(function() {
		var name = $('#name').val();
		if (name == "") {
			jAlert('分类名称必须填写', '信息提示');
			return false;
		}
		var parentId = $('#parentId').val();
		var parentName = $('#parentId').find("option:selected").text();
		var parentId1 = $('#parentId1').val();
		var parentName1 = $('#parentId1').find("option:selected").text();
		if (parentId == "0") {
			var grade = "0";
		} else if (parentId != "0" && parentId != "-1" && parentId1 == "-1") {
			var grade = "1";

		} else if (parentId != "0" && parentId != "-1" && parentId1 != "-1") {
			var grade = "2";
		} else if (parentId == "-1") {
			jAlert('请选择分类', '信息提示');
			return false;
		}
		var goodsTypeId = $('#goodsTypeId').val();
		if (goodsTypeId == "0") {
			jAlert('商品类型必须选择', '信息提示');
			return false;
		}
		var sort = $('#sort').val();
		if (sort == "") {
			jAlert('排序必须填写', '信息提示');
			return false;
		}
		var sign = $('#sign').val();
		if (sign == "") {
			jAlert('标示必须填写', '信息提示');
			return false;
		}
		var metaKeywords = $('#metaKeywords').val();
		var metaDes = $('#metaDes').val();
		if (parentId == "0") {
			$.post("addGoodsCategory.action", {
				"grade" : grade,
				"metaKeywords" : metaKeywords,
				"metaDes" : metaDes,
				"name" : name,
				"sort" : sort,
				"sign" : sign,
				"goodsTypeId" : goodsTypeId
			}, function(data) {
				if (data.sucflag) {
					window.location.href = "goodscategorymanagement.jsp?session="+session+"#goods";
				} else {
					jAlert('分类名称或者标示不能和其他分类和标示重复', '信息提示');
					return false;
				}
			});
		} else if (parentId != "0" && parentId != "-1" && parentId1 == "-1") {
			$.post("addGoodsCategoryTwo.action", {
				"parentName" : parentName,
				"parentId" : parentId,
				"grade" : grade,
				"metaKeywords" : metaKeywords,
				"metaDes" : metaDes,
				"name" : name,
				"sort" : sort,
				"sign" : sign,
				"goodsTypeId" : goodsTypeId
			}, function(data) {
				if (data.sucflag) {
					window.location.href = "goodscategorymanagement.jsp?session="+session+"#goods";
				} else {
					jAlert('分类名称或者标示不能和其他分类和标示重复', '信息提示');
					return false;
				}
			});
		} else if (parentId != "0" && parentId != "-1" && parentId1 != "-1") {
			$.post("addGoodsCategoryThree.action", {
				"parentName1" : parentName1,
				"parentId" : parentId,
				"parentId1" : parentId1,
				"grade" : grade,
				"metaKeywords" : metaKeywords,
				"metaDes" : metaDes,
				"name" : name,
				"sort" : sort,
				"sign" : sign,
				"goodsTypeId" : goodsTypeId
			}, function(data) {
				if (data.sucflag) {
					window.location.href = "goodscategorymanagement.jsp?session="+session+"#goods";
				} else {
					jAlert('分类名称或者标示不能和其他分类和标示重复', '信息提示');
					return false;
				}
			});
		}
	});
});
/*===========================================Gorgeous split-line==============================================*/

/**
 * Update Function
 */
/*
 * 修改商品分类
 */
$(function() {
	var goodsCategoryTid = $.query.get('goodsCategoryTid');
	if (goodsCategoryTid == "") {
		return false;
	}
	$.post("findGoodscategoryBygoodscategoryId.action", {
		"goodsCategoryTid" : goodsCategoryTid
	}, function(data) {
		if (data.bean != null) {
			$('#submit').hide();
			$('#addfl').hide();
			$('#modfl').show();
			$('#modify').show();
			$('#name').attr("value", data.bean.name);
			$('#parentName').attr("value", data.bean.parentName);
			$('#goodsTypeId').val(data.bean.goodsTypeId);
			$('#sign').attr("value", data.bean.sign);
			$('#sort').attr("value", data.bean.sort);
			$('#metaKeywords').attr("value", data.bean.metaKeywords);
			$('#metaDes').attr("value", data.bean.metaDes);
			$('#hidgoodsCategoryTid').attr("value", data.bean.goodsCategoryTid);
		}
	});

	$('#modify').click(function() {
		var name = $('#name').val();
		if (name == "") {
			jAlert('分类名称必须填写', '信息提示');
			return false;
		}
		var goodsTypeId = $('#goodsTypeId').val();
		if (goodsTypeId == "0") {
			jAlert('商品类型必须选择', '信息提示');
			return false;
		}
		var sort = $('#sort').val();
		var sign = $('#sign').val();
		var metaKeywords = $('#metaKeywords').val();
		var metaDes = $('#metaDes').val();
		var goodsCategoryTid = $('#hidgoodsCategoryTid').val();
		$.post("updateGoodscategoryT.action", {
			"metaKeywords" : metaKeywords,
			"metaDes" : metaDes,
			"name" : name,
			"sort" : sort,
			"sign" : sign,
			"goodsTypeId" : goodsTypeId,
			"goodsCategoryTid" : goodsCategoryTid
		}, function(data) {
			if (data.sucflag) {
				window.location.href = "goodscategorymanagement.jsp?session="+session+"#goods";
			} else {
				jAlert('分类名称或者标示不能和其他分类和标示重复', '信息提示');
				return false;
			}
		});
	});
});
