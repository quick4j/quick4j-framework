/**
 * @author zhaojh
 */
;(function($){
    if(typeof quick4j.ui.datagrid === 'undefined'){
        $.namespace("quick4j.ui.datagrid");
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
            dataType: 'json'
        })
        .done(function(response){
            if(response.meta.success){
                successFn(response.data);
            }else{
                alert(response.meta.message);
            }
        })
        .fail(function(jqXHR, textStatus, errorThrown){
            errorFn.apply(this, arguments);
        });
    },

    getDefaultOptions = function(dgname){
        return {
            pageSize: 20,
            pageList: [10, 20, 50, 100],
            url: 'api/datagrid/' + dgname,
            loader: customJsonLoader,
            onClickCell: unselectRowWhenClickCellForOperationColumn
        }
    };

    $.extend(quick4j.ui.datagrid, {
        parse: function(target){
            var dgName = $.parser.parseOptions(target).name;

            $.ajax({
                url: 'api/datagrid/' + dgName + '/options'
            })
            .done(function(response){
                if(!response.meta.success){
                    alert('==>'+response.meta.message);
                    return;
                }

                var options = $.extend(
                    {},
                    getDefaultOptions(),
                    response.data || {columns:[[{field: 'chk', checkbox: true}]]}
                );
                buildDataGrid(target, $.extend({}, options, {name: dgName}));
            })
            .fail(function(jqXHR, textStatus, errorThrown){
                alert('error');
            });
        }
    });
})(jQuery);