/**
 * Bootstrap Search Suggest
 * @desc    这是一个基于 bootstrap 按钮式下拉菜单组件的搜索建议插件，必须使用于按钮式下拉菜单组件上。
 * @author  renxia <lzwy0820#qq.com>
 * @github  https://github.com/lzwme/bootstrap-suggest-plugin.git
 * @since   2014-10-09
 *===============================================================================
 * (c) Copyright 2014-2019 http://lzw.me All Rights Reserved.
 ********************************************************************************/
(function (factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports === 'object' && typeof module === 'object') {
        factory(require('jquery'));
    } else if (window.jQuery) {
        factory(window.jQuery);
    } else {
        throw new Error('Not found jQuery.');
    }
})(function($) {
    var VERSION = 'VERSION_PLACEHOLDER';
    var $window = $(window);
    var isIe = 'ActiveXObject' in window; // 用于对 IE 的兼容判断
    var inputLock; // 用于中文输入法输入时锁定搜索

    // ie 下和 chrome 51 以上浏览器版本，出现滚动条时不计算 padding
    var chromeVer = navigator.userAgent.match(/Chrome\/(\d+)/);
    if (chromeVer) {
        chromeVer = +chromeVer[1];
    }
    var notNeedCalcPadding = isIe || chromeVer > 51;

    // 一些常量
    var BSSUGGEST = 'bsSuggest';
    var onDataRequestSuccess = 'onDataRequestSuccess';
    var DISABLED = 'disabled';
    var TRUE = true;
    var FALSE = false;

    function isUndefined(val) {
        return val === void(0);
    }

    /**
     * 错误处理
     */
    function handleError(e1, e2) {
        if (!window.console || !window.console.trace) {
            return;
        }
        console.trace(e1);
        if (e2) {
            console.trace(e2);
        }
    }
    /**
     * 获取当前 tr 列的关键字数据
     */
    function getPointKeyword($list) {
        return $list.data();
    }
    /**
     * 设置或获取输入框的 alt 值
     */
    function setOrGetAlt($input, val) {
        return isUndefined(val) ? $input.attr('alt') : $input.attr('alt', val);
    }
    /**
     * 设置或获取输入框的 data-id 值
     */
    function setOrGetDataId($input, val) {
        return val !== (void 0) ? $input.attr('data-id', val) : $input.attr('data-id');
    }
    /**
     * 设置选中的值
     */
    function setValue($input, keywords, options) {
        if (!keywords || !keywords.key) {
            return;
        }

        var separator = options.separator || ',',
            inputValList,
            inputIdList,
            dataId = setOrGetDataId($input);

        if (options && options.multiWord) {
            inputValList = $input.val().split(separator);
            inputValList[inputValList.length - 1] = keywords.key;

            //多关键字检索支持设置id --- 存在 bug，不建议使用
            if (!dataId) {
                inputIdList = [keywords.id];
            } else {
                inputIdList = dataId.split(separator);
                inputIdList.push(keywords.id);
            }

            setOrGetDataId($input, inputIdList.join(separator))
                .val(inputValList.join(separator))
                .focus();
        } else {
            setOrGetDataId($input, keywords.id || '').val(keywords.key).focus();
        }

        $input.data('pre-val', $input.val())
            .trigger('onSetSelectValue', [keywords, (options.data.value || options._lastData.value)[keywords.index]]);
    }
    /**
     * 调整选择菜单位置
     * @param {Object} $input
     * @param {Object} $dropdownMenu
     * @param {Object} options
     */
    function adjustDropMenuPos($input, $dropdownMenu, options) {
        if (!$dropdownMenu.is(':visible')) {
            return;
        }

        var $parent = $input.parent();
        var parentHeight = $parent.height();
        var parentWidth = $parent.width();

        if (options.autoDropup) {
            setTimeout(function() {
                var offsetTop = $input.offset().top;
                var winScrollTop = $window.scrollTop();
                var menuHeight = $dropdownMenu.height();

                if ( // 自动判断菜单向上展开
                    ($window.height() + winScrollTop - offsetTop) < menuHeight && // 假如向下会撑长页面
                    offsetTop > (menuHeight + winScrollTop) // 而且向上不会撑到顶部
                ) {
                    $parent.addClass('dropup');
                } else {
                    $parent.removeClass('dropup');
                }
            }, 10);
        }

        // 列表对齐方式
        var dmcss = {};
        if (options.listAlign === 'left') {
            dmcss = {
                'left': $input.siblings('div').width() - parentWidth,
                'right': 'auto'
            };
        } else if (options.listAlign === 'right') {
            dmcss = {
                'left': 'auto',
                'right': 0
            };
        }

        // ie 下，不显示按钮时的 top/bottom
        if (isIe && !options.showBtn) {
            if (!$parent.hasClass('dropup')) {
                dmcss.top = parentHeight;
                dmcss.bottom = 'auto';
            } else {
                dmcss.top = 'auto';
                dmcss.bottom = parentHeight;
            }
        }

        // 是否自动最小宽度
        if (!options.autoMinWidth) {
            dmcss.minWidth = parentWidth;
        }
        /* else {
            dmcss['width'] = 'auto';
        }*/

        $dropdownMenu.css(dmcss);

        return $input;
    }
    /**
     * 设置输入框背景色
     * 当设置了 indexId，而输入框的 data-id 为空时，输入框加载警告色
     */
    function setBackground($input, options) {
        var inputbg, bg, warnbg;
        if ((options.indexId === -1 && !options.idField) || options.multiWord) {
            return $input;
        }

        bg = options.inputBgColor;
        warnbg = options.inputWarnColor;

        var curVal = $input.val();
        var preVal = $input.data('pre-val');

        if (setOrGetDataId($input) || !curVal) {
            $input.css('background', bg || '');

            if (!curVal && preVal) {
                $input.trigger('onUnsetSelectValue').data('pre-val', '');
            }

            return $input;
        }

        inputbg = $input.css('backgroundColor').replace(/ /g, '').split(',', 3).join(',');
        // 自由输入的内容，设置背景色
        if (!~warnbg.indexOf(inputbg)) {
            $input.trigger('onUnsetSelectValue') // 触发取消data-id事件
                .data('pre-val', '')
                .css('background', warnbg);
        }

        return $input;
    }
    /**
     * 调整滑动条
     */
    function adjustScroll($input, $dropdownMenu, options) {
        // 控制滑动条
        var $hover = $input.parent().find('tbody tr.' + options.listHoverCSS),
            pos, maxHeight;

        if ($hover.length) {
            pos = ($hover.index() + 3) * $hover.height();
            maxHeight = +$dropdownMenu.css('maxHeight').replace('px', '');

            if (pos > maxHeight || $dropdownMenu.scrollTop() > maxHeight) {
                pos = pos - maxHeight;
            } else {
                pos = 0;
            }

            $dropdownMenu.scrollTop(pos);
        }
    }
    /**
     * 解除所有列表 hover 样式
     */
    function unHoverAll($dropdownMenu, options) {
        $dropdownMenu.find('tr.' + options.listHoverCSS).removeClass(options.listHoverCSS);
    }
    /**
     * 验证 $input 对象是否符合条件
     *   1. 必须为 bootstrap 下拉式菜单
     *   2. 必须未初始化过
     */
    function checkInput($input, $dropdownMenu, options) {
        if (
            !$dropdownMenu.length || // 过滤非 bootstrap 下拉式菜单对象
            $input.data(BSSUGGEST) // 是否已经初始化的检测
        ) {
            return FALSE;
        }

        $input.data(BSSUGGEST, {
            options: options
        });

        return TRUE;
    }
    /**
     * 数据格式检测
     * 检测 ajax 返回成功数据或 data 参数数据是否有效
     * data 格式：{"value": [{}, {}...]}
     */
    function checkData(data) {
        var isEmpty = TRUE, o;

        for (o in data) {
            if (o === 'value') {
                isEmpty = FALSE;
                break;
            }
        }
        if (isEmpty) {
            handleError('返回数据格式错误!');
            return FALSE;
        }
        if (!data.value.length) {
            // handleError('返回数据为空!');
            return FALSE;
        }

        return data;
    }
    /**
     * 判断字段名是否在 options.effectiveFields 配置项中
     * @param  {String} field   要判断的字段名
     * @param  {Object} options
     * @return {Boolean}        effectiveFields 为空时始终返回 true
     */
    function inEffectiveFields(field, options) {
        var effectiveFields = options.effectiveFields;

        return !(field === '__index' ||
            effectiveFields.length &&
            !~$.inArray(field, effectiveFields));
    }
    /**
     * 判断字段名是否在 options.searchFields 搜索字段配置中
     */
    function inSearchFields(field, options) {
        return ~$.inArray(field, options.searchFields);
    }
    /**
     * 通过下拉菜单显示提示文案
     */
    function showTip(tip, $input, $dropdownMenu, options) {
        $dropdownMenu.html('<div style="padding:10px 5px 5px">' + tip + '</div>').show();
        adjustDropMenuPos($input, $dropdownMenu, options);
    }
    /**
     * 显示下拉列表
     */
    function showDropMenu($input, options) {
        var $dropdownMenu = $input.parent().find('ul:eq(0)');
        if (!$dropdownMenu.is(':visible')) {
            // $dropdownMenu.css('display', 'block');
            $dropdownMenu.show();
            $input.trigger('onShowDropdown', [options ? options.data.value : []]);
        }
    }
    /**
     * 隐藏下拉列表
     */
    function hideDropMenu($input, options) {
        var $dropdownMenu = $input.parent().find('ul:eq(0)');
        if ($dropdownMenu.is(':visible')) {
            // $dropdownMenu.css('display', '');
            $dropdownMenu.hide();
            $input.trigger('onHideDropdown', [options ? options.data.value : []]);
        }
    }
    /**
     * 下拉列表刷新
     * 作为 fnGetData 的 callback 函数调用
     */
    function refreshDropMenu($input, data, options) {
        var $dropdownMenu = $input.parent().find('ul:eq(0)'),
            len, i, field, index = 0,
            tds,
            html = ['<table class="table table-condensed table-sm" style="margin:0">'],
            idValue, keyValue; // 作为输入框 data-id 和内容的字段值
        var dataList = data.value;

        if (!data || !(len = dataList.length)) {
            if (options.emptyTip) {
                showTip(options.emptyTip, $input, $dropdownMenu, options);
            } else {
                $dropdownMenu.empty();
                hideDropMenu($input, options);
            }
            return $input;
        }

        // 相同数据，不用继续渲染了
        if (
            options._lastData &&
            JSON.stringify(options._lastData) === JSON.stringify(data) &&
            $dropdownMenu.find('tr').length === len
        ) {
            showDropMenu($input, options);
            return adjustDropMenuPos($input, $dropdownMenu, options);
        }
        options._lastData = data;

        // 生成表头
        if (options.showHeader) {
            html.push('<thead><tr>');
            for (field in dataList[0]) {
                if (!inEffectiveFields(field, options)) {
                    continue;
                }

                html.push('<th>', (options.effectiveFieldsAlias[field] || field),
                    index === 0 ? ('(' + len + ')') : '' , // 表头第一列记录总数
                    '</th>');

                index++;
            }
            html.push('</tr></thead>');
        }
        html.push('<tbody>');

        // console.log(data, len);
        // 按列加数据
        var dataI;
        for (i = 0; i < len; i++) {
            index = 0;
            tds = [];
            dataI = dataList[i];
            idValue = dataI[options.idField];
            keyValue = dataI[options.keyField];

            for (field in dataI) {
                // 标记作为 value 和 作为 id 的值
                if (isUndefined(keyValue) && options.indexKey === index) {
                    keyValue = dataI[field];
                }
                if (isUndefined(idValue) && options.indexId === index) {
                    idValue = dataI[field];
                }

                index++;

                // 列表中只显示有效的字段
                if (inEffectiveFields(field, options)) {
                    tds.push('<td data-name="', field, '">', dataI[field], '</td>');
                }
            }

            html.push('<tr data-index="', (dataI.__index || i),
                '" data-id="', idValue,
                '" data-key="', keyValue, '">',
                tds.join(''), '</tr>');
        }
        html.push('</tbody></table>');

        $dropdownMenu.html(html.join(''));
        showDropMenu($input, options);
        //.show();

        // scrollbar 存在时，延时到动画结束时调整 padding
        setTimeout(function() {
            if (notNeedCalcPadding) {
                return;
            }

            var $table = $dropdownMenu.find('table:eq(0)'),
                pdr = 0,
                mgb = 0;

            if (
                $dropdownMenu.height() < $table.height() &&
                +$dropdownMenu.css('minWidth').replace('px', '') < $dropdownMenu.width()
            ) {
                pdr = 18;
                mgb = 20;
            }

            $dropdownMenu.css('paddingRight', pdr);
            $table.css('marginBottom', mgb);
        }, 301);

        adjustDropMenuPos($input, $dropdownMenu, options);

        return $input;
    }
    /**
     * ajax 获取数据
     * @param  {Object} options
     * @return {Object}         $.Deferred
     */
    function ajax(options, keyword) {
        keyword = keyword || '';

        var preAjax = options._preAjax;

        if (preAjax && preAjax.abort && preAjax.readyState !== 4) {
            // console.log('abort pre ajax');
            preAjax.abort();
        }

        var ajaxParam = {
            type: 'GET',
            dataType: options.jsonp ? 'jsonp' : 'json',
            timeout: 5000,
        };

        // jsonp
        if (options.jsonp) {
            ajaxParam.jsonp = options.jsonp;
        }

        // 自定义 ajax 请求参数生成方法
        var adjustAjaxParam,
            fnAdjustAjaxParam = options.fnAdjustAjaxParam;

        if ($.isFunction(fnAdjustAjaxParam)) {
            adjustAjaxParam = fnAdjustAjaxParam(keyword, options);

            // options.fnAdjustAjaxParam 返回false，则终止 ajax 请求
            if (FALSE === adjustAjaxParam) {
                return;
            }

            $.extend(ajaxParam, adjustAjaxParam);
        }

        // url 调整
        ajaxParam.url = function() {
            if (!keyword || ajaxParam.data) {
                return ajaxParam.url || options.url;
            }

            var type = '?';
            if (/=$/.test(options.url)) {
                type = '';
            } else if (/\?/.test(options.url)) {
                type = '&';
            }

            return options.url + type + encodeURIComponent(keyword);
        }();

        return options._preAjax = $.ajax(ajaxParam).done(function(result) {
            options.data = options.fnProcessData(result);
        }).fail(function(err) {
            if (options.fnAjaxFail) {
                options.fnAjaxFail(err, options);
            }
        });
    }
    /**
     * 检测 keyword 与 value 是否存在互相包含
     * @param  {String}  keyword 用户输入的关键字
     * @param  {String}  key     匹配字段的 key
     * @param  {String}  value   key 字段对应的值
     * @param  {Object}  options
     * @return {Boolean}         包含/不包含
     */
    function isInWord(keyword, key, value, options) {
        value = $.trim(value);

        if (options.ignorecase) {
            keyword = keyword.toLocaleLowerCase();
            value = value.toLocaleLowerCase();
        }

        return value &&
            (inEffectiveFields(key, options) || inSearchFields(key, options)) && // 必须在有效的搜索字段中
            (
                ~value.indexOf(keyword) || // 匹配值包含关键字
                options.twoWayMatch && ~keyword.indexOf(value) // 关键字包含匹配值
            );
    }
    /**
     * 通过 ajax 或 json 参数获取数据
     */
    function getData(keyword, $input, callback, options) {
        var data, validData, filterData = {
                value: []
            },
            i, key, len,
            fnPreprocessKeyword = options.fnPreprocessKeyword;

        keyword = keyword || '';
        // 获取数据前对关键字预处理方法
        if ($.isFunction(fnPreprocessKeyword)) {
            keyword = fnPreprocessKeyword(keyword, options);
        }

        // 给了url参数，则从服务器 ajax 请求
        // console.log(options.url + keyword);
        if (options.url) {
            var timer;
            if (options.searchingTip) {
                timer = setTimeout(function() {
                    showTip(options.searchingTip, $input, $input.parent().find('ul'), options);
                }, 600);
            }

            ajax(options, keyword).done(function(result) {
                callback($input, options.data, options); // 为 refreshDropMenu
                $input.trigger(onDataRequestSuccess, result);
                if (options.getDataMethod === 'firstByUrl') {
                    options.url = null;
                }
            }).always(function() {
                timer && clearTimeout(timer);
            });
        } else {
            // 没有给出 url 参数，则从 data 参数获取
            data = options.data;
            validData = checkData(data);
            // 本地的 data 数据，则在本地过滤
            if (validData) {
                if (keyword) {
                    // 输入不为空时则进行匹配
                    len = data.value.length;
                    for (i = 0; i < len; i++) {
                        for (key in data.value[i]) {
                            if (
                                data.value[i][key] &&
                                isInWord(keyword, key, data.value[i][key] + '', options)
                            ) {
                                filterData.value.push(data.value[i]);
                                filterData.value[filterData.value.length - 1].__index = i;
                                break;
                            }
                        }
                    }
                } else {
                    filterData = data;
                }
            }

            callback($input, filterData, options);
        } // else
    }
    /**
     * 数据处理
     * url 获取数据时，对数据的处理，作为 fnGetData 之后的回调处理
     */
    function processData(data) {
        return checkData(data);
    }
    /**
     * 取得 clearable 清除按钮
     */
    function getIClear($input, options) {
        var $iClear = $input.prev('i.clearable');

        // 是否可清除已输入的内容(添加清除按钮)
        if (options.clearable && !$iClear.length) {
                $iClear = $('<i class="clearable glyphicon glyphicon-remove fa fa-plus"></i>')
                    .prependTo($input.parent());
        }

        return $iClear.css({
            position: 'absolute',
            top: 'calc(50% - 6px)',
            transform: 'rotate(45deg)',
            // right: options.showBtn ? Math.max($input.next('.input-group-btn').width(), 33) + 2 : 12,
            zIndex: 4,
            cursor: 'pointer',
            width: '14px',
            lineHeight: '14px',
            textAlign: 'center',
            fontSize: 12
        }).hide();
    }
    /**
     * 默认的配置选项
     * @type {Object}
     */
    var defaultOptions = {
        url: null,                      // 请求数据的 URL 地址
        jsonp: null,                    // 设置此参数名，将开启jsonp功能，否则使用json数据结构
        data: {
            value: []
        },                              // 提示所用的数据，注意格式
        indexId: 0,                     // 每组数据的第几个数据，作为input输入框的 data-id，设为 -1 且 idField 为空则不设置此值
        indexKey: 0,                    // 每组数据的第几个数据，作为input输入框的内容
        idField: '',                    // 每组数据的哪个字段作为 data-id，优先级高于 indexId 设置（推荐）
        keyField: '',                   // 每组数据的哪个字段作为输入框内容，优先级高于 indexKey 设置（推荐）

        /* 搜索相关 */
        autoSelect: TRUE,               // 键盘向上/下方向键时，是否自动选择值
        allowNoKeyword: TRUE,           // 是否允许无关键字时请求数据
        getDataMethod: 'firstByUrl',    // 获取数据的方式，url：一直从url请求；data：从 options.data 获取；firstByUrl：第一次从Url获取全部数据，之后从options.data获取
        delayUntilKeyup: FALSE,         // 获取数据的方式 为 firstByUrl 时，是否延迟到有输入时才请求数据
        ignorecase: FALSE,              // 前端搜索匹配时，是否忽略大小写
        effectiveFields: [],            // 有效显示于列表中的字段，非有效字段都会过滤，默认全部有效。
        effectiveFieldsAlias: {},       // 有效字段的别名对象，用于 header 的显示
        searchFields: [],               // 有效搜索字段，从前端搜索过滤数据时使用，但不一定显示在列表中。effectiveFields 配置字段也会用于搜索过滤
        twoWayMatch: TRUE,              // 是否双向匹配搜索。为 true 即输入关键字包含或包含于匹配字段均认为匹配成功，为 false 则输入关键字包含于匹配字段认为匹配成功
        multiWord: FALSE,               // 以分隔符号分割的多关键字支持
        separator: ',',                 // 多关键字支持时的分隔符，默认为半角逗号
        delay: 300,                     // 搜索触发的延时时间间隔，单位毫秒
        emptyTip: '',                   // 查询为空时显示的内容，可为 html
        searchingTip: '搜索中...',       // ajax 搜索时显示的提示内容，当搜索时间较长时给出正在搜索的提示
        hideOnSelect: FALSE,            // 鼠标从列表单击选择了值时，是否隐藏选择列表

        /* UI */
        autoDropup: FALSE,              // 选择菜单是否自动判断向上展开。设为 true，则当下拉菜单高度超过窗体，且向上方向不会被窗体覆盖，则选择菜单向上弹出
        autoMinWidth: FALSE,            // 是否自动最小宽度，设为 false 则最小宽度不小于输入框宽度
        showHeader: FALSE,              // 是否显示选择列表的 header。为 true 时，有效字段大于一列则显示表头
        showBtn: TRUE,                  // 是否显示下拉按钮
        inputBgColor: '',               // 输入框背景色，当与容器背景色不同时，可能需要该项的配置
        inputWarnColor: 'rgba(255,0,0,.1)', // 输入框内容不是下拉列表选择时的警告色
        listStyle: {
            'padding-top': 0,
            'max-height': '375px',
            'max-width': '800px',
            'overflow': 'auto',
            'width': 'auto',
            'transition': '0.3s',
            '-webkit-transition': '0.3s',
            '-moz-transition': '0.3s',
            '-o-transition': '0.3s',
            'word-break': 'keep-all',
            'white-space': 'nowrap'
        },                              // 列表的样式控制
        listAlign: 'left',              // 提示列表对齐位置，left/right/auto
        listHoverStyle: 'background: #07d; color:#fff', // 提示框列表鼠标悬浮的样式
        listHoverCSS: 'jhover',         // 提示框列表鼠标悬浮的样式名称
        clearable: FALSE,               // 是否可清除已输入的内容

        /* key */
        keyLeft: 37,                    // 向左方向键，不同的操作系统可能会有差别，则自行定义
        keyUp: 38,                      // 向上方向键
        keyRight: 39,                   // 向右方向键
        keyDown: 40,                    // 向下方向键
        keyEnter: 13,                   // 回车键

        /* methods */
        fnProcessData: processData,     // 格式化数据的方法，返回数据格式参考 data 参数
        fnGetData: getData,             // 获取数据的方法，无特殊需求一般不作设置
        fnAdjustAjaxParam: null,        // 调整 ajax 请求参数方法，用于更多的请求配置需求。如对请求关键字作进一步处理、修改超时时间等
        fnPreprocessKeyword: null,      // 搜索过滤数据前，对输入关键字作进一步处理方法。注意，应返回字符串
        fnAjaxFail: null,               // ajax 失败时回调方法
    };

    var methods = {
        init: function(options) {
            // 参数设置
            var self = this;
            options = options || {};

            // 默认配置有效显示字段多于一个，则显示列表表头，否则不显示
            if (isUndefined(options.showHeader) && options.effectiveFields && options.effectiveFields.length > 1) {
                options.showHeader = TRUE;
            }

            options = $.extend(TRUE, {}, defaultOptions, options);

            // 旧的方法兼容
            if (options.processData) {
                options.fnProcessData = options.processData;
            }

            if (options.getData) {
                options.fnGetData = options.getData;
            }

            if (options.getDataMethod === 'firstByUrl' && options.url && !options.delayUntilKeyup) {
                ajax(options).done(function(result) {
                    options.url = null;
                    self.trigger(onDataRequestSuccess, result);
                });
            }

            // 鼠标滑动到条目样式
            if (!$('#' + BSSUGGEST).length) {
                $('head:eq(0)').append('<style id="' + BSSUGGEST + '">.' + options.listHoverCSS + '{' + options.listHoverStyle + '}</style>');
            }

            return self.each(function() {
                var $input = $(this),
                    $parent = $input.parent(),
                    $iClear = getIClear($input, options),
                    isMouseenterMenu,
                    keyupTimer, // keyup 与 input 事件延时定时器
                    $dropdownMenu = $parent.find('ul:eq(0)');

                // 兼容 bs4
                $dropdownMenu.parent().css('position', 'relative');

                // 验证输入框对象是否符合条件
                if (!checkInput($input, $dropdownMenu, options)) {
                    console.warn('不是一个标准的 bootstrap 下拉式菜单或已初始化:', $input);
                    return;
                }

                // 是否显示 button 按钮
                if (!options.showBtn) {
                    $input.css('borderRadius', 4);
                    $parent.css('width', '100%')
                        .find('.btn:eq(0)').hide();
                }

                // 移除 disabled 类，并禁用自动完成
                $input.removeClass(DISABLED).prop(DISABLED, FALSE).attr('autocomplete', 'off');
                // dropdown-menu 增加修饰
                $dropdownMenu.css(options.listStyle);

                // 默认背景色
                if (!options.inputBgColor) {
                    options.inputBgColor = $input.css('backgroundColor');
                }

                // 开始事件处理
                $input.on('keydown', function(event) {
                    var currentList, tipsKeyword; // 提示列表上被选中的关键字

                    // 当提示层显示时才对键盘事件处理
                    if (!$dropdownMenu.is(':visible')) {
                        setOrGetDataId($input, '');
                        return;
                    }

                    currentList = $dropdownMenu.find('.' + options.listHoverCSS);
                    tipsKeyword = ''; // 提示列表上被选中的关键字

                    unHoverAll($dropdownMenu, options);

                    if (event.keyCode === options.keyDown) { // 如果按的是向下方向键
                        if (!currentList.length) {
                            // 如果提示列表没有一个被选中,则将列表第一个选中
                            tipsKeyword = getPointKeyword($dropdownMenu.find('tbody tr:first').mouseover());
                        } else if (!currentList.next().length) {
                            // 如果是最后一个被选中,则取消选中,即可认为是输入框被选中，并恢复输入的值
                            if (options.autoSelect) {
                                setOrGetDataId($input, '').val(setOrGetAlt($input));
                            }
                        } else {
                            // 选中下一行
                            tipsKeyword = getPointKeyword(currentList.next().mouseover());
                        }
                        // 控制滑动条
                        adjustScroll($input, $dropdownMenu, options);

                        if (!options.autoSelect) {
                            return;
                        }
                    } else if (event.keyCode === options.keyUp) { // 如果按的是向上方向键
                        if (!currentList.length) {
                            tipsKeyword = getPointKeyword($dropdownMenu.find('tbody tr:last').mouseover());
                        } else if (!currentList.prev().length) {
                            if (options.autoSelect) {
                                setOrGetDataId($input, '').val(setOrGetAlt($input));
                            }
                        } else {
                            // 选中前一行
                            tipsKeyword = getPointKeyword(currentList.prev().mouseover());
                        }

                        // 控制滑动条
                        adjustScroll($input, $dropdownMenu, options);

                        if (!options.autoSelect) {
                            return;
                        }
                    } else if (event.keyCode === options.keyEnter) {
                        tipsKeyword = getPointKeyword(currentList);
                        hideDropMenu($input, options);
                    } else {
                        setOrGetDataId($input, '');
                    }

                    // 设置值 tipsKeyword
                    // console.log(tipsKeyword);
                    setValue($input, tipsKeyword, options);
                }).on('compositionstart', function(event) {
                    // 中文输入开始，锁定
                    // console.log('compositionstart');
                    inputLock = TRUE;
                }).on('compositionend', function(event) {
                    // 中文输入结束，解除锁定
                    // console.log('compositionend');
                    inputLock = FALSE;
                }).on('keyup input paste', function(event) {
                    var word;

                    if (event.keyCode) {
                        setBackground($input, options);
                    }

                    // 如果弹起的键是回车、向上或向下方向键则返回
                    if (~$.inArray(event.keyCode, [options.keyDown, options.keyUp, options.keyEnter])) {
                        $input.val($input.val()); // 让鼠标输入跳到最后
                        return;
                    }

                    clearTimeout(keyupTimer);
                    keyupTimer = setTimeout(function() {
                        // console.log('input keyup', event);

                        // 锁定状态，返回
                        if (inputLock) {
                            return;
                        }

                        word = $input.val();

                        // 若输入框值没有改变则返回
                        if ($.trim(word) && word === setOrGetAlt($input)) {
                            return;
                        }

                        // 当按下键之前记录输入框值,以方便查看键弹起时值有没有变
                        setOrGetAlt($input, word);

                        if (options.multiWord) {
                            word = word.split(options.separator).reverse()[0];
                        }

                        // 是否允许空数据查询
                        if (!word.length && !options.allowNoKeyword) {
                            return;
                        }

                        options.fnGetData($.trim(word), $input, refreshDropMenu, options);
                    }, options.delay || 300);
                }).on('focus', function() {
                    // console.log('input focus');
                    adjustDropMenuPos($input, $dropdownMenu, options);
                }).on('blur', function() {
                    if (!isMouseenterMenu) { // 不是进入下拉列表状态，则隐藏列表
                        hideDropMenu($input, options);
                    }
                }).on('click', function() {
                    // console.log('input click');
                    var word = $input.val();

                    if (
                        $.trim(word) &&
                        word === setOrGetAlt($input) &&
                        $dropdownMenu.find('table tr').length
                    ) {
                        return showDropMenu($input, options);
                    }

                    if ($dropdownMenu.is(':visible')) {
                        return;
                    }

                    if (options.multiWord) {
                        word = word.split(options.separator).reverse()[0];
                    }

                    // 是否允许空数据查询
                    if (!word.length && !options.allowNoKeyword) {
                        return;
                    }

                    // console.log('word', word);
                    options.fnGetData($.trim(word), $input, refreshDropMenu, options);
                });

                // 下拉按钮点击时
                $parent.find('.btn:eq(0)').attr('data-toggle', '').click(function() {
                    if (!$dropdownMenu.is(':visible')) {
                        if (options.url) {
                            $input.click().focus();
                            if (!$dropdownMenu.find('tr').length) {
                                return FALSE;
                            }
                        } else {
                            // 不以 keyword 作为过滤，展示所有的数据
                            refreshDropMenu($input, options.data, options);
                        }
                        showDropMenu($input, options);
                    } else {
                        hideDropMenu($input, options);
                    }

                    return FALSE;
                });

                // 列表中滑动时，输入框失去焦点
                $dropdownMenu.mouseenter(function() {
                        // console.log('mouseenter')
                        isMouseenterMenu = 1;
                        $input.blur();
                    }).mouseleave(function() {
                        // console.log('mouseleave')
                        isMouseenterMenu = 0;
                        $input.focus();
                    }).on('mouseenter', 'tbody tr', function() {
                        // 行上的移动事件
                        unHoverAll($dropdownMenu, options);
                        $(this).addClass(options.listHoverCSS);

                        return FALSE; // 阻止冒泡
                    })
                    .on('mousedown', 'tbody tr', function() {
                        var keywords = getPointKeyword($(this));
                        setValue($input, keywords, options);
                        setOrGetAlt($input, keywords.key);
                        setBackground($input, options);

                        if (options.hideOnSelect) {
                            hideDropMenu($input, options);
                        }
                    });

                // 存在清空按钮
                if ($iClear.length) {
                    $iClear.click(function () {
                        setOrGetDataId($input, '').val('');
                        setBackground($input, options);
                    });

                    $parent.mouseenter(function() {
                        if (!$input.prop(DISABLED)) {
                            $iClear.css('right', options.showBtn ? Math.max($input.next().width(), 33) + 2 : 12)
                                .show();
                        }
                    }).mouseleave(function() {
                        $iClear.hide();
                    });
                }

            });
        },
        show: function() {
            return this.each(function() {
                $(this).click();
            });
        },
        hide: function() {
            return this.each(function() {
                hideDropMenu($(this));
            });
        },
        disable: function() {
            return this.each(function() {
                $(this).attr(DISABLED, TRUE)
                    .parent().find('.btn:eq(0)').prop(DISABLED, TRUE);
            });
        },
        enable: function() {
            return this.each(function() {
                $(this).attr(DISABLED, FALSE)
                    .parent().find('.btn:eq(0)').prop(DISABLED, FALSE);
            });
        },
        destroy: function() {
            return this.each(function() {
                $(this).off().removeData(BSSUGGEST).removeAttr('style')
                    .parent().find('.btn:eq(0)').off().show().attr('data-toggle', 'dropdown').prop(DISABLED, FALSE) // .addClass(DISABLED);
                    .next().css('display', '').off();
            });
        },
        version: function() {
            return VERSION;
        }
    };

    $.fn[BSSUGGEST] = function(options) {
        // 方法判断
        if (typeof options === 'string' && methods[options]) {
            var inited = TRUE;
            this.each(function() {
                if (!$(this).data(BSSUGGEST)) {
                    return inited = FALSE;
                }
            });
            // 只要有一个未初始化，则全部都不执行方法，除非是 init 或 version
            if (!inited && 'init' !== options && 'version' !== options) {
                return this;
            }

            // 如果是方法，则参数第一个为函数名，从第二个开始为函数参数
            return methods[options].apply(this, [].slice.call(arguments, 1));
        } else {
            // 调用初始化方法
            return methods.init.apply(this, arguments);
        }
    }
});
