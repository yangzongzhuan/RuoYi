(function($) {
    "use strict";

    $.fn.bootstrapTreeTable = function(options, param) {
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
        var _main_div = $("<div class='fixed-table-container'></div>");
        target.before(_main_div);
        _main_div.append(target);
        target.addClass("table table-hover treegrid-table table-bordered");
        if (options.striped) {
            target.addClass('table-striped');
        }
        // 工具条在外层包装一下div，样式用的bootstrap-table的
        if(options.toolbar){
            var _tool_div = $("<div class='fixed-table-toolbar' style='display:none;'></div>");
            var _tool_left_div = $("<div class='bs-bars pull-left'></div>");
            _tool_left_div.append($(options.toolbar));
            _tool_div.append(_tool_left_div);
            _main_div.before(_tool_div);
        }
        // 得到根节点
        target.getRootNodes = function(data) {
            // 指定Root节点值
            var _root = options.rootCodeValue?options.rootCodeValue:null
            var result = [];
            $.each(data, function(index, item) {
                // 这里兼容几种常见Root节点写法
                // 默认的几种判断
                var _defaultRootFlag = item[options.parentCode] == '0'
                    || item[options.parentCode] == 0
                    || item[options.parentCode] == null
                    || item[options.parentCode] == '';
                if (!item[options.parentCode] || (_root?(item[options.parentCode] == options.rootCodeValue):_defaultRootFlag)){
                    result.push(item);
                }
                // 添加一个默认属性，用来判断当前节点有没有被显示
                item.isShow = false;
            });
            return result;
        };
        var j = 0;
        // 递归获取子节点并且设置子节点
        target.getChildNodes = function(data, parentNode, parentIndex, tbody) {
            $.each(data, function(i, item) {
                if (item[options.parentCode] == parentNode[options.code]) {
                    var tr = $('<tr></tr>');
                    var nowParentIndex = (parentIndex + (j++) + 1);
                    tr.addClass('treegrid-' + nowParentIndex);
                    tr.addClass('treegrid-parent-' + parentIndex);
                    target.renderRow(tr,item);
                    item.isShow = true;
                    tbody.append(tr);
                    target.getChildNodes(data, item, nowParentIndex, tbody)

                }
            });
        };
        // 绘制行
        target.renderRow = function(tr,item){
            $.each(options.columns, function(index, column) {
                // 判断有没有选择列
                if(index==0&&column.field=='selectItem'){
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
                    var td = $('<td style="text-align:'+column.align+';'+((column.width)?('width:'+column.width):'')+'"></td>');
                    // 增加formatter渲染
                    if (column.formatter) {
                        td.html(column.formatter.call(this, item, index));
                    } else {
                        td.text(item[column.field]);
                    }
                    tr.append(td);
                }
            });
        }
        // 加载数据
        target.load = function(parms){
            // 加载数据前先清空
            target.html("");
            // 构造表头
            var thr = $('<tr></tr>');
            $.each(options.columns, function(i, item) {
                var th = null;
                // 判断有没有选择列
                if(i==0&&item.field=='selectItem'){
                    hasSelectItem = true;
                    th = $('<th style="text-align:'+item.valign+';width:36px"></th>');
                }else{
                    th = $('<th style="text-align:'+item.valign+';padding:10px;'+((item.width)?('width:'+item.width):'')+'"></th>');
                }
                th.text(item.title);
                thr.append(th);
            });
            var thead = $('<thead class="treegrid-thead"></thead>');
            thead.append(thr);
            target.append(thead);
            // 构造表体
            var tbody = $('<tbody class="treegrid-tbody"></tbody>');
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
                        var _empty = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">没有记录</div></td></tr>'
                        tbody.html(_empty);
                        return;
                    }
                    var rootNode = target.getRootNodes(data);
                    $.each(rootNode, function(i, item) {
                        var tr = $('<tr></tr>');
                        tr.addClass('treegrid-' + (j + "_" + i));
                        target.renderRow(tr,item);
                        item.isShow = true;
                        tbody.append(tr);
                        target.getChildNodes(data, item, (j + "_" + i), tbody);
                    });
                    // 下边的操作主要是为了查询时让一些没有根节点的节点显示
                    $.each(data, function(i, item) {
                        if(!item.isShow){
                            var tr = $('<tr></tr>');
                            tr.addClass('treegrid-' + (j + "_" + i));
                            target.renderRow(tr,item);
                            tbody.append(tr);
                        }
                    });
                    target.append(tbody);
                    // 初始化treegrid
                    target.treegrid({
                        treeColumn: options.expandColumn?options.expandColumn:(hasSelectItem?1:0),//如果有radio或checkbox默认第二列层级显示，当前是在用户未设置的提前下
                        expanderExpandedClass : options.expanderExpandedClass,
                        expanderCollapsedClass : options.expanderCollapsedClass
                    });
                    if (!options.expandAll) {
                        target.treegrid('collapseAll');
                    }
                    //动态设置表头宽度
                    //thead.css("width", tbody.children(":first").css("width"));
                    // 行点击选中事件
                    target.find("tbody").find("tr").click(function(){
                        if(hasSelectItem){
                            var _ipt = $(this).find("input[name='select_item']");
                            if(_ipt.attr("type")=="radio"){
                                _ipt.prop('checked',true);
                                target.find("tbody").find("tr").removeClass("treegrid-selected");
                                $(this).addClass("treegrid-selected");
                            }else{
                                if(_ipt.prop('checked')){
                                    _ipt.prop('checked',false);
                                    $(this).removeClass("treegrid-selected");
                                }else{
                                    _ipt.prop('checked',true);
                                    $(this).addClass("treegrid-selected");
                                }
                            }
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
                chk_value.push({id:_ipt.val()});
            }else{
                _ipt.each(function(_i,_item){
                    chk_value.push({id:$(_item).val()});
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
        // 重置表格视图
        resetHeight : function(target, height) {
        	target.find("tbody").css("height", height + 'px');
        }
        // 组件的其他方法也可以进行类似封装........
    };

    $.fn.bootstrapTreeTable.defaults = {
        id : 'menuId',// 选取记录返回的值
        code : 'menuId',// 用于设置父子关系
        parentCode : 'parentId',// 用于设置父子关系
        rootCodeValue: null,//设置根节点code值----可指定根节点，默认为null,"",0,"0"
        data : [], // 构造table的数据集合
        type : "GET", // 请求数据的ajax类型
        url : null, // 请求数据的ajax的url
        ajaxParams : {}, // 请求数据的ajax的data属性
        expandColumn : null,// 在哪一列上面显示展开按钮
        expandAll : true, // 是否全部展开
        striped : false, // 是否各行渐变色
        columns : [],
        toolbar: null,//顶部工具条
        height: 0,
        expanderExpandedClass : 'glyphicon glyphicon-chevron-down',// 展开的按钮的图标
        expanderCollapsedClass : 'glyphicon glyphicon-chevron-right'// 缩起的按钮的图标

    };
})(jQuery);