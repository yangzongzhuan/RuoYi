/**
 * 基于bootstrap-table-mobile修改
 * 修正部分iPhone手机不显示卡片视图
 * Copyright (c) 2019 ruoyi
 */
!function ($) {
    
    'use strict';
    
    var resetView = function (that) {
        if (that.options.height || that.options.showFooter) {
            setTimeout(that.resetView(), 1);
        }
    };
    
    // 判断是否 iphone
    var isIPhone = function () {
	    var browserName = navigator.userAgent.toLowerCase();
	    return /(iPhone|iPad|iPod|iOS)/i.test(browserName);
	};

    var changeView = function (that, width, height) {
        if (that.options.minHeight) {
            if (checkValuesLessEqual(width, that.options.minWidth) && checkValuesLessEqual(height, that.options.minHeight)) {
                conditionCardView(that);
            } else if (checkValuesGreater(width, that.options.minWidth) && checkValuesGreater(height, that.options.minHeight)) {
                conditionFullView(that);
            }
        } else {
            if (checkValuesLessEqual(width, that.options.minWidth) || isIPhone()) {
                conditionCardView(that);
            } else if (checkValuesGreater(width, that.options.minWidth)) {
                conditionFullView(that);
            }
        }

        resetView(that);
    };

    var checkValuesLessEqual = function (currentValue, targetValue) {
        return currentValue <= targetValue;
    };

    var checkValuesGreater = function (currentValue, targetValue) {
        return currentValue > targetValue;
    };

    var conditionCardView = function (that) {
        changeTableView(that, false);
    };

    var conditionFullView = function (that) {
        changeTableView(that, true);
    };

    var changeTableView = function (that, cardViewState) {
        that.options.cardView = cardViewState;
        that.toggleView();
    };

    $.extend($.fn.bootstrapTable.defaults, {
        mobileResponsive: false,
        minWidth: 562,
        minHeight: undefined,
        checkOnInit: true,
        toggled: false
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _init = BootstrapTable.prototype.init;

    BootstrapTable.prototype.init = function () {
        _init.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.mobileResponsive) {
            return;
        }

        if (!this.options.minWidth) {
            return;
        }

        var that = this;
        $(window).resize(function () {
            changeView(that, $(this).width(), $(this).height())
        });

        if (this.options.checkOnInit) {
            changeView(this, $(window).width(), $(window).height());
        }
    };
}(jQuery);