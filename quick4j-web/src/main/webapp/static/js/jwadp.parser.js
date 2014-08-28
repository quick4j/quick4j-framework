/**
 * Created by zhaojh on 14-7-1.
 */
(function($){
    $.extend({
        namespace: function(ns){
            if(typeof ns != 'string'){
                throw new Error('namespace must be a string');
            }

            var ns_arr = ns.split('.');
            var parent = window;
            for(var i in ns_arr){
                parent[ns_arr[i]] = parent[ns_arr[i]] || {};
                parent = parent[ns_arr[i]];
            }
        }
    });

    if(typeof jwadp === 'undefined'){
        $.namespace("jwadp");
    }

    if(typeof jwadp.ui === 'undefined'){
        $.namespace("jwadp.ui");
    }

    $.extend(jwadp, {
        plugins: ['datagrid'],
        parse: function(context){
            var aa = [];
            for(var i=0; i<jwadp.plugins.length; i++){
                var name = jwadp.plugins[i];
                var r = $('.quick4j-' + name, context);
                if(r.length){
                    aa.push({name: name, jq: r});
                }
            }

            if(aa.length){
                for(var i=0; i<aa.length; i++){
                    var name = aa[i].name;
                    $.each(aa[i].jq, function (i, n) {
                        jwadp.ui[name].parse(n);
                    })
                }
            }
        }
    });

    $(function(){
        jwadp.parse();
    });
})(jQuery);