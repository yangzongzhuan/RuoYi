/**
 * bootstrapTreeTable
 *
 * @author swifly
 */
(function($) {
	"use strict";

	$.fn.bootstrapTreeTable = function(options, param) {
		var allData = null;//用于存放格式化后的数据
		// 如果是调用方法
		if (typeof options == 'string') {
			return $.fn.bootstrapTreeTable.methods[options](this, param);
		}
		// 如果是初始化组件
		options = $.extend({}, $.fn.bootstrapTreeTable.defaults, options || {});
		// 是否有radio或checkbox
		var hasSelectItem = false;
		var target = $(this);
		// 在外层包装一下div，样式用的bootstrap-table的
		var _main_div = $("<div class='bootstrap-tree-table fixed-table-container'></div>");
		target.before(_main_div);
		_main_div.append(target);
		target.addClass("table table-hover treetable-table");
		if (options.striped) {
			target.addClass('table-striped');
		}
		// 工具条在外层包装一下div，样式用的bootstrap-table的
		if(options.toolbar){
			var _tool_div = $("<div class='fixed-table-toolbar'></div>");
			var _tool_left_div = $("<div class='bs-bars pull-left'></div>");
			_tool_left_div.append($(options.toolbar));
			_tool_div.append(_tool_left_div);
			_main_div.before(_tool_div);
		}
		// 格式化数据，优化性能
		target.formatData=function(data){
			var _root = options.rootCodeValue?options.rootCodeValue:null
			$.each(data, function(index, item) {
				// 添加一个默认属性，用来判断当前节点有没有被显示
				item.isShow = false;
				// 这里兼容几种常见Root节点写法
				// 默认的几种判断
				var _defaultRootFlag = item[options.parentCode] == '0'
					|| item[options.parentCode] == 0
					|| item[options.parentCode] == null
					|| item[options.parentCode] == '';
				if (!item[options.parentCode] || (_root?(item[options.parentCode] == options.rootCodeValue):_defaultRootFlag)){
					if(!allData["_root_"]){allData["_root_"]=[];}
					allData["_root_"].push(item);
				}else{
					if(!allData["_n_"+item[options.parentCode]]){allData["_n_"+item[options.parentCode]]=[];}
					allData["_n_"+item[options.parentCode]].push(item);
				}
			});
			data=null;//回收
		}
		// 得到根节点
		target.getRootNodes = function() {
			return allData["_root_"];
		};
		// 递归获取子节点并且设置子节点
		target.handleNode = function(parentNode, lv, row_id, p_id, tbody) {
			var _ls = allData["_n_"+parentNode[options.code]];
			var tr = target.renderRow(parentNode,_ls?true:false,lv,row_id,p_id);
			tbody.append(tr);
			if(_ls){
				$.each(_ls, function(i, item) {
					var _row_id = row_id+"_"+i
					target.handleNode(item, (lv + 1), _row_id,row_id, tbody)
				});
			}
		}; 
		// 绘制行
		target.renderRow = function(item,isP,lv,row_id,p_id){
			// 标记已显示
			item.isShow = true;
			var tr = $('<tr id="'+row_id+'" pid="'+p_id+'"></tr>');
			var _icon = options.expanderCollapsedClass;
        	if(options.expandAll){
            	tr.css("display","table");
            	_icon = options.expanderExpandedClass;
        	}else if(options.expandFirst&&lv<=1){
            	tr.css("display","table");
            	_icon=(lv==0)?options.expanderExpandedClass:options.expanderCollapsedClass;
        	}else{
            	tr.css("display","none");
            	_icon = options.expanderCollapsedClass;
        	}
			$.each(options.columns, function(index, column) {
				// 判断有没有选择列
				if(column.field=='selectItem'){
					hasSelectItem = true;
					var td = $('<td style="text-align:center;width:36px"></td>');
					if(column.radio){
						var _ipt = $('<input name="select_item" type="radio" value="'+item[options.id]+'"></input>');
						td.append(_ipt);
					} 
					if(column.checkbox){
						var _ipt = $('<input name="select_item" type="checkbox" value="'+item[options.id]+'"></input>');
						td.append(_ipt);
					} 
					tr.append(td);
				}else{
					var td = $('<td title="'+item[column.field]+'" name="'+column.field+'" style="text-align:'+column.align+';'+((column.width)?('width:'+column.width):'')+'"></td>');
					// 增加formatter渲染
					if (column.formatter) {
						td = $('<td style="text-align:'+column.align+';'+((column.width)?('width:'+column.width):'')+'"></td>');
						td.html(column.formatter.call(this, item[column.field], item, index));
					} else {
						td.text(item[column.field]);
					}
					if(options.expandColumn==index){
						if(!isP){
							td.prepend('<span class="treetable-expander"></span>')
						}else{
							td.prepend('<span class="treetable-expander '+_icon+'"></span>')
						}
						for (var int = 0; int < (lv-1); int++) {
							td.prepend('<span class="treetable-indent"></span>')
						}
					}
					tr.append(td);
				}
			});
			return tr;
		}
		// 加载数据
		target.load = function(parms){
			// 加载数据前先清空
			allData = {};
			// 加载数据前先清空
			target.html("");
			// 构造表头
			var thr = $('<tr></tr>');
			$.each(options.columns, function(i, item) {
				var th = null;
				// 判断有没有选择列
				if(i==0 && item.field=='selectItem'){
					hasSelectItem = true;
					th = $('<th style="width:36px"></th>');
				}else{
					th = $('<th style="'+((item.width)?('width:'+item.width):'')+'"></th>');
				}
				th.text(item.title);
				thr.append(th);
			});
			var thead = $('<thead class="treetable-thead"></thead>');
			thead.append(thr);
			target.append(thead);
			// 构造表体
			var tbody = $('<tbody class="treetable-tbody"></tbody>');
			target.append(tbody);
			// 添加加载loading
			var _loading = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">正在努力地加载数据中，请稍候……</div></td></tr>'
			tbody.html(_loading);
			// 默认高度
			if(options.height){
				tbody.css("height",options.height);
			}
			$.ajax({
				type : options.type,
				url : options.url,
				data : parms?parms:options.ajaxParams,
				dataType : "JSON",
				success : function(data, textStatus, jqXHR) {
					// 加载完数据先清空
					tbody.html("");
					if(!data||data.length<=0){
						var _empty = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">没有找到匹配的记录</div></td></tr>'
						tbody.html(_empty);
						return;
					}
					// 格式化数据
					target.formatData(data);
					// 开始绘制
					var rootNode = target.getRootNodes();
					if(rootNode){
						$.each(rootNode, function(i, item) {
							var _row_id = "row_id_"+i
							target.handleNode(item, 1, _row_id,"row_root", tbody);
						});
					}
					// 下边的操作主要是为了查询时让一些没有根节点的节点显示
					$.each(data, function(i, item) {
						if(!item.isShow){
							var tr = target.renderRow(item,false,1);
							tbody.append(tr);
						}
					});
					target.append(tbody);
					//动态设置表头宽度
					thead.css("width", tbody.children(":first").css("width"));
					// 行点击选中事件
					target.find("tbody").find("tr").click(function(){
						if(hasSelectItem){
							var _ipt = $(this).find("input[name='select_item']");
							if(_ipt.attr("type")=="radio"){
								_ipt.prop('checked',true);
								target.find("tbody").find("tr").removeClass("treetable-selected");
								$(this).addClass("treetable-selected");
							}else{
								if(_ipt.prop('checked')){
									_ipt.prop('checked',false);
									$(this).removeClass("treetable-selected");
								}else{
									_ipt.prop('checked',true);
									$(this).addClass("treetable-selected");
								}
							}
						}
					});
					// 小图标点击事件--展开缩起
					target.find("tbody").find("tr").find(".treetable-expander").click(function(){
						var _flag = $(this).hasClass(options.expanderExpandedClass);
						var tr = $(this).parent().parent();
						var row_id = tr.attr("id");
						if(_flag){
							var _ls = target.find("tbody").find("tr[id^='"+row_id+"_']");//下所有
							if(_ls&&_ls.length>0){
								$.each(_ls, function(index, item) {
									$(item).css("display","none");
									var _icon = $(item).children().eq(options.expandColumn).find(".treetable-expander");
									if(_icon.hasClass(options.expanderExpandedClass)){
										_icon.removeClass(options.expanderExpandedClass)
										_icon.addClass(options.expanderCollapsedClass)
									}
								});
							}
							$(this).removeClass(options.expanderExpandedClass)
							$(this).addClass(options.expanderCollapsedClass)
						}else{
							var _ls = target.find("tbody").find("tr[pid='"+row_id+"']");//下一级
							if(_ls&&_ls.length>0){
								$.each(_ls, function(index, item) {
									$(item).css("display","table");
								});
							}
							$(this).removeClass(options.expanderCollapsedClass)
							$(this).addClass(options.expanderExpandedClass)
						}
					});
				},
			    error:function(xhr,textStatus){
					var _errorMsg = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">'+xhr.responseText+'</div></td></tr>'
					tbody.html(_errorMsg);
					debugger;
			    },
			});
		}
		if (options.url) {
			target.load();
		} else {
			// 也可以通过defaults里面的data属性通过传递一个数据集合进来对组件进行初始化....有兴趣可以自己实现，思路和上述类似
		}
		
		return target;
	};

	// 组件方法封装........
	$.fn.bootstrapTreeTable.methods = {
		// 返回选中记录的id（返回的id由配置中的id属性指定）
		// 为了兼容bootstrap-table的写法，统一返回数组，这里只返回了指定的id
		getSelections : function(target, data) {
			// 所有被选中的记录input
			var _ipt = target.find("tbody").find("tr").find("input[name='select_item']:checked");
			var chk_value =[]; 
			// 如果是radio
			if(_ipt.attr("type")=="radio"){
				var _data = {id:_ipt.val()};
				var _tds = _ipt.parent().parent().find("td");
				_tds.each(function(_i,_item){ 
					if(_i!=0){
						_data[$(_item).attr("name")]=$(_item).attr("title");
					}
				}); 
				chk_value.push(_data); 
			}else{
				_ipt.each(function(_i,_item){ 
					var _data = {id:$(_item).val()};
					var _tds = $(_item).parent().parent().find("td");
					_tds.each(function(_ii,_iitem){ 
						if(_ii!=0){
							_data[$(_iitem).attr("name")]=$(_iitem).attr("title");
						}
					}); 
					chk_value.push(_data); 
				}); 
			}
			return chk_value;
		},
		// 刷新记录
		refresh : function(target, parms) {
			if(parms){
				target.load(parms);
			}else{
				target.load();
			}
		},
	    // 组件的其他方法也可以进行类似封装........
	};

	$.fn.bootstrapTreeTable.defaults = {
		id : 'id',// 选取记录返回的值
		code : 'id',// 用于设置父子关系
		parentCode : 'parentId',// 用于设置父子关系
		rootCodeValue: null,//设置根节点code值----可指定根节点，默认为null,"",0,"0"
		data : [], // 构造table的数据集合
		type : "GET", // 请求数据的ajax类型
		url : null, // 请求数据的ajax的url
		ajaxParams : {}, // 请求数据的ajax的data属性
		expandColumn : 0,// 在哪一列上面显示展开按钮
		expandAll : false, // 是否全部展开
        expandFirst : true, // 是否默认第一级展开--expandAll为false时生效
		striped : false, // 是否各行渐变色
		columns : [],
        toolbar: '#toolbar',//顶部工具条
        height: 0,
		expanderExpandedClass : 'glyphicon glyphicon-chevron-down',// 展开的按钮的图标
		expanderCollapsedClass : 'glyphicon glyphicon-chevron-right'// 缩起的按钮的图标

	};
})(jQuery);