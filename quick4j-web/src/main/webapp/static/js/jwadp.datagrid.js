/**
 * @author zhaojh
 */
;(function($){
    if(typeof jwadp.ui.datagrid === 'undefined'){
        $.namespace("jwadp.ui.datagrid");
    }

    var decorateToolbar = function(options){
        if(options.toolbar){
            var tb = [];
            for(var i=0; i<options.toolbar.length; i++){
                tb.push(options.toolbar[i]);
                tb.push('-');
            }

            $.extend(options, {toolbar: tb});
        }
    },

    buildDataGrid = function(target, options){
        var opts = $.extend({}, options, getDefaultOptions(options.name));
        string2ObjectForFormatterProperty(opts);
        decorateToolbar(opts);
        $(target).datagrid(opts);
    },

    string2ObjectForFormatterProperty = function(options){
        $.each(options.columns, function(i, row){
            $.each(row, function(i, column){
                if(column.formatter && typeof column.formatter == 'string' && $.trim(column.formatter)!=''){
                    column.formatter = window[column.formatter];
                }
            });
        });
    },

    unselectRowWhenClickCellForOperationColumn = function(rowIndex, field, value){
        if(field == 'operation'){
            setTimeout(function(){
                $('#datagrid').datagrid('unselectRow', rowIndex);
            }, 1);
        }
    },

    customJsonLoader = function(param, successFn, errorFn){
        var opts = $(this).datagrid("options");
        if (!opts.url) {
            return false;
        }

        $.ajax({
            type: 'POST',
            url: opts.url,
            data: param,
            dataType: 'json',
            success: function(result){
                if(result.status == '200'){
                    successFn(result.data);
                }else{
                    alert(result.message);
                }
            },
            error: function(){
                errorFn.apply(this, arguments);
            }
        });
    },

    getDefaultOptions = function(dgname){
        return {
            pageSize: 20,
            pageList: [10, 20, 50, 100],
            url: 'api/rest/datagrid/' + dgname,
            loader: customJsonLoader,
            onClickCell: unselectRowWhenClickCellForOperationColumn
        }
    };

    $.extend(jwadp.ui.datagrid, {
        parse: function(target){
            var dgName = $.parser.parseOptions(target).name;
            $.ajax({
                url: 'api/rest/datagrid/' + dgName + '/options',
                success: function(result){
                    if(result.status != '200'){
                        alert('==>'+result.message)
                    }

                    var options = $.extend(
                        {},
                        getDefaultOptions(),
                        result.data || {columns:[[{field: 'chk', checkbox: true}]]}
                    );
                    buildDataGrid(target, $.extend({}, options, {name: dgName}));
                },
                error: function(){
                    alert('error')
                }
            })
        }
    });
})(jQuery);