<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <title></title>
        <link rel="stylesheet" href="static/js/vender/easyui/themes/bootstrap/easyui.css">
        <link rel="stylesheet" href="static/js/vender/easyui/themes/icon.css">
        <style>
            .datagrid-operation-button{
                width: 16px;
                height: 16px;
                display: inline-block;
                padding: 0 5px;
                vertical-align:middle;
                cursor: pointer;
            }
        </style>
    </head>
    <body class="easyui-layout">
        <div data-options="region:'center', border: false" style="padding: 2px;">
            <table class="quick4j-datagrid" id="datagrid"
                   data-options="name: 'dictionary',fit: true, striped: true"></table>
        </div>


        <script src="static/js/vender/jquery-1.11.1.min.js"></script>
        <script src="static/js/vender/easyui/jquery.easyui.min.js"></script>
        <script src="static/js/vender/easyui/locale/easyui-lang-zh_CN.js"></script>
        <script src="static/js/jquery.easyui.extend.min.js"></script>
        <script src="static/js/jwadp.parser.js"></script>
        <script src="static/js/jwadp.datagrid.js"></script>
        <script>
            function doNew(){
                $.showModalDialog({
                    title: '新建',
                    content: 'url:dictionary/new',
                    data: {operation: 'new', datagrid: $('#datagrid')},
                    buttons:[{
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: 'doSave'
                    },{
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function(win){
                            win.close();
                        }
                    }]
                });
            }

            function doEdit(index){
                var target = $('#datagrid');
                var row = target.datagrid('getRows')[index];

                $.showModalDialog({
                    title: '新建',
                    content: 'url:dictionary/'+row.id+'/edit',
                    data: {operation: 'edit', datagrid: target, row: row},
                    locate: 'document',
                    buttons:[{
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: 'doSave'
                    },{
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function(win){
                            win.close();
                        }
                    }],
                    onLoad: function(win, body){
                        record = win.getData('row');
                        console.log($.param(record));
                        $('#id').val(record.id);
                        $('#code').val(record.code);
                        $('#name').val(record.name);
                        $('#value').val(record.value);
                        $('#text').val(record.text);
                        $('#index').val(record.orderNum);
                    }
                });
            }

            function doDelete(index){
                $.messager.confirm('确认', '确认删除此条记录?', function(r){
                    if(r){
                        var target = $('#datagrid');
                        var id = target.datagrid('getRows')[index].id;

                        $.ajax({
                            url: 'dictionary/' + id + '/delete',
                            success: function(data){
                                if(data.status == 200){
                                    target.datagrid('reload');
                                }else{
                                    $.messager.alert('错误', data.status + '<br>' + data.message, 'error');
                                }
                            },
                            error: function(){
                                $.messager.alert('错误', '操作过程中发生错误。', 'error');
                            }
                        })
                    }
                });
            }

            function doBuildButton(value,row,index){
                return '<div class="icon-edit datagrid-operation-button" title="编辑" onclick="doEdit('+index+')"></div>' +
                       '<div class="icon-remove datagrid-operation-button" title="删除" onclick="doDelete('+index+')"></div>';
            }

        </script>
    </body>
</html>
