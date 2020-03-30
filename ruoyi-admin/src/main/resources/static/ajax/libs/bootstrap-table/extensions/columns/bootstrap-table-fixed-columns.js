/**
 * 基于bootstrap-table-fixed-columns修改
 * 支持左右列冻结、支持固定高度
 * Copyright (c) 2019 ruoyi
 */
(function ($) {
    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        fixedColumns: false,
        fixedNumber: 1,
        rightFixedColumns: false,
        rightFixedNumber: 1
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initHeader = BootstrapTable.prototype.initHeader,
        _initBody = BootstrapTable.prototype.initBody,
        _resetView = BootstrapTable.prototype.resetView;

    BootstrapTable.prototype.initFixedColumns = function () {
        this.timeoutHeaderColumns_ = 0;
        this.timeoutBodyColumns_ = 0;
        if (this.options.fixedColumns) {
            this.$fixedHeader = $([
                '<div class="left-fixed-table-columns">',
                '<table>',
                '<thead></thead>',
                '</table>',
                '</div>'].join(''));
            
            this.$fixedHeader.find('table').attr('class', this.$el.attr('class'));
            this.$fixedHeaderColumns = this.$fixedHeader.find('thead');
            this.$tableHeader.before(this.$fixedHeader);

            this.$fixedBody = $([
                '<div class="left-fixed-body-columns">',
                '<table>',
                '<tbody></tbody>',
                '</table>',
                '</div>'].join(''));

            this.$fixedBody.find('table').attr('class', this.$el.attr('class'));
            this.$fixedBodyColumns = this.$fixedBody.find('tbody');
            this.$tableBody.before(this.$fixedBody);
        }
        if (this.options.rightFixedColumns) {
            this.$rightfixedBody = $([
                '<div class="right-fixed-table-columns">',
                '<table>',
                '<thead></thead>',
                '<tbody style="background-color: #fff;"></tbody>',
                '</table>',
                '</div>'].join(''));
            this.$rightfixedBody.find('table').attr('class', this.$el.attr('class'));
            this.$rightfixedHeaderColumns = this.$rightfixedBody.find('thead');
            this.$rightfixedBodyColumns = this.$rightfixedBody.find('tbody');
            this.$tableBody.before(this.$rightfixedBody);
            if (this.options.fixedColumns) {
                $('.right-fixed-table-columns').attr('style','right:0px');
            }
        }
    };

    BootstrapTable.prototype.initHeader = function () {
        _initHeader.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns && !this.options.rightFixedColumns){
            return;
        }
        this.initFixedColumns();

        var $ltr = this.$header.find('tr:eq(0)').clone(true),
            $rtr = this.$header.find('tr:eq(0)').clone(),
            $lths = $ltr.clone(true).find('th'),
            $rths = $rtr.clone().find('th');

        $ltr.html('');
        $rtr.html('');
        //右边列冻结
        if (this.options.rightFixedColumns) {
            for (var i = 0; i < this.options.rightFixedNumber; i++) {
                $rtr.append($rths.eq($rths.length - this.options.rightFixedNumber + i).clone());
            }
            this.$rightfixedHeaderColumns.html('').append($rtr);
        }

        //左边列冻结
        if (this.options.fixedColumns) {
            for (var i = 0; i < this.options.fixedNumber; i++) {
                $ltr.append($lths.eq(i).clone(true));
            }
            this.$fixedHeaderColumns.html('').append($ltr);
            this.$selectAll = $ltr.find('[name="btSelectAll"]');
            this.$selectAll.on('click', function () {
            	var checked = $(this).prop('checked');
            	$(".left-fixed-body-columns input[name=btSelectItem]").filter(':enabled').prop('checked', checked);
            	$('.fixed-table-body input[name=btSelectItem]').closest('tr').removeClass('selected');
            });
        }
    };

    BootstrapTable.prototype.initBody = function () {
        _initBody.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns && !this.options.rightFixedColumns) {
            return;
        }

        var that = this;
        if (this.options.fixedColumns) {
            this.$fixedBodyColumns.html('');
            this.$body.find('> tr[data-index]').each(function () {
                var $tr = $(this).clone(true),
                    $tds = $tr.clone(true).find('td');

                $tr.html('');
                for (var i = 0; i < that.options.fixedNumber; i++) {
                    $tr.append($tds.eq(i).clone(true));
                }
                that.$fixedBodyColumns.append($tr);
            });
        }
        if (this.options.rightFixedColumns) {
            this.$rightfixedBodyColumns.html('');
            this.$body.find('> tr[data-index]').each(function () {
                var $tr = $(this).clone(),
                    $tds = $tr.clone().find('td');

                $tr.html('');
                for (var i = 0; i < that.options.rightFixedNumber; i++) {
                    var indexTd = $tds.length - that.options.rightFixedNumber + i;
                    var oldTd = $tds.eq(indexTd);
                    var fixTd = oldTd.clone();
                    var buttons = fixTd.find('button');
                    //事件转移：冻结列里面的事件转移到实际按钮的事件
                    buttons.each(function (key, item) {
                        $(item).click(function () {
                            that.$body.find("tr[data-index=" + $tr.attr('data-index') + "] td:eq(" + indexTd + ") button:eq(" + key + ")").click();
                        });
                    });
                    $tr.append(fixTd);
                }
                that.$rightfixedBodyColumns.append($tr);
            });
        }
    };

    BootstrapTable.prototype.resetView = function () {
        _resetView.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns && !this.options.rightFixedColumns) {
            return;
        }

        clearTimeout(this.timeoutHeaderColumns_);
        this.timeoutHeaderColumns_ = setTimeout($.proxy(this.fitHeaderColumns, this), this.$el.is(':hidden') ? 100 : 0);

        clearTimeout(this.timeoutBodyColumns_);
        this.timeoutBodyColumns_ = setTimeout($.proxy(this.fitBodyColumns, this), this.$el.is(':hidden') ? 100 : 0);
    };

    BootstrapTable.prototype.fitHeaderColumns = function () {
        var that = this,
            visibleFields = this.getVisibleFields(),
            headerWidth = 0;
        if (that.options.fixedColumns) {
            this.$body.find('tr:first-child:not(.no-records-found) > *').each(function (i) {
                var $this = $(this),
                    index = i;

                if (i >= that.options.fixedNumber) {
                    return false;
                }

                if (that.options.detailView && !that.options.cardView) {
                    index = i - 1;
                }
                that.$fixedHeader.find('thead th[data-field="' + visibleFields[index] + '"]')
                    .find('.fht-cell').width($this.innerWidth());
                headerWidth += $this.outerWidth();
            });
            this.$fixedHeader.width(headerWidth + 2).show();
        }
        if (that.options.rightFixedColumns) {
            this.$body.find('tr:first-child:not(.no-records-found) > *').each(function (i) {
                var $this = $(this),
                    index = i;

                if (i >= visibleFields.length - that.options.rightFixedNumber) {
                    return false;


                    if (that.options.detailView && !that.options.cardView) {
                       index = i - 1;
                    }
                    that.$rightfixedBody.find('thead th[data-field="' + visibleFields[index] + '"]')
                        .find('.fht-cell').width($this.innerWidth() - 1);
                    headerWidth += $this.outerWidth();
                }
            });
            this.$rightfixedBody.width(headerWidth - 1).show();
        }
    };

    BootstrapTable.prototype.fitBodyColumns = function () {
        var that = this,
            top = -(parseInt(this.$el.css('margin-top'))),
            height = this.$tableBody.height();

        if (that.options.fixedColumns) {
            if (!this.$body.find('> tr[data-index]').length) {
                this.$fixedBody.hide();
                return;
            }
            
            if (!this.options.height) {
                top = this.$fixedHeader.height()- 1;
                height = height - top;
            }

            this.$fixedBody.css({
                width: this.$fixedHeader.width(),
                height: height,
                top: top + 1
            }).show();

            this.$body.find('> tr').each(function (i) {
            	that.$fixedBody.find('tr:eq(' + i + ')').height($(this).height() - 0.5);
                var thattds = this;
                that.$fixedBody.find('tr:eq(' + i + ')').find('td').each(function (j) {
                    $(this).width($($(thattds).find('td')[j]).width() + 1);
                });
            });
            
            $("#" + table.options.id).on("check.bs.table uncheck.bs.table", function (e, rows, $element) {
        	    var index= $element.data('index');
                $(this).find('.bs-checkbox').find('input[data-index="' + index + '"]').prop("checked", true);
                var selectFixedItem = $('.left-fixed-body-columns input[name=btSelectItem]');
                var checkAll = selectFixedItem.filter(':enabled').length &&
                    selectFixedItem.filter(':enabled').length ===
                	selectFixedItem.filter(':enabled').filter(':checked').length;
                $(".left-fixed-table-columns input[name=btSelectAll]").prop('checked', checkAll);
                $('.fixed-table-body input[name=btSelectItem]').closest('tr').removeClass('selected');
        	});
            
            $("#" + table.options.id).off('click', '.fixed-table-body').on('click', '.th-inner', function (event) {
            	$.each(that.$fixedHeader.find('th'), function (i, th) {
        			$(th).find('.sortable').removeClass('desc asc').addClass('both');
        		});
            });

            // events
            this.$fixedHeader.off('click', '.th-inner').on('click', '.th-inner', function (event) {
            	var target = $(this);
            	var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(), $this_ = that.$header.find('th').eq($this.index());
            	
            	var sortOrder = "";
            	if (table.options.sortName === $this.data('field')) {
            		sortOrder = table.options.sortOrder === 'asc' ? 'desc' : 'asc';
                } else {
                    sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
                }
            	table.options.sortOrder = sortOrder;
            	var sortName = $this.data('sortName') ? $this.data('sortName') : $this.data('field');
            	if (target.parent().data().sortable) {
            		$.each(that.$fixedHeader.find('th'), function (i, th) {
            			$(th).find('.sortable').removeClass('desc asc').addClass(($(th).data('field') === sortName || $(th).data('sortName') === sortName) ? sortOrder : 'both');
            		});
                }
            });
            
            this.$tableBody.on('scroll', function () {
                that.$fixedBody.find('table').css('top', -$(this).scrollTop());
            });
            this.$body.find('> tr[data-index]').off('hover').hover(function () {
                var index = $(this).data('index');
                that.$fixedBody.find('tr[data-index="' + index + '"]').addClass('hover');
            }, function () {
                var index = $(this).data('index');
                that.$fixedBody.find('tr[data-index="' + index + '"]').removeClass('hover');
            });
            this.$fixedBody.find('tr[data-index]').off('hover').hover(function () {
                var index = $(this).data('index');
                that.$body.find('tr[data-index="' + index + '"]').addClass('hover');
            }, function () {
                var index = $(this).data('index');
                that.$body.find('> tr[data-index="' + index + '"]').removeClass('hover');
            });
        }
        if (that.options.rightFixedColumns) {
            if (!this.$body.find('> tr[data-index]').length) {
                this.$rightfixedBody.hide();
                return;
            }

            this.$body.find('> tr').each(function (i) {
                that.$rightfixedBody.find('tbody tr:eq(' + i + ')').height($(this).height());
            });

            //// events
            this.$tableBody.on('scroll', function () {
                that.$rightfixedBody.find('table').css('top', -$(this).scrollTop());
            });
            this.$body.find('> tr[data-index]').off('hover').hover(function () {
                var index = $(this).data('index');
                that.$rightfixedBody.find('tr[data-index="' + index + '"]').addClass('hover');
            }, function () {
                var index = $(this).data('index');
                that.$rightfixedBody.find('tr[data-index="' + index + '"]').removeClass('hover');
            });
            this.$rightfixedBody.find('tr[data-index]').off('hover').hover(function () {
                var index = $(this).data('index');
                that.$body.find('tr[data-index="' + index + '"]').addClass('hover');
            }, function () {
                var index = $(this).data('index');
                that.$body.find('> tr[data-index="' + index + '"]').removeClass('hover');
            });
        }
    };

})(jQuery);