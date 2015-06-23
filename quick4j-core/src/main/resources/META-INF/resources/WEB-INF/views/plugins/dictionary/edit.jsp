<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="static/css/quick4j.css">
<div style="padding: 10px;">
    <form id="editFrm" method="post">
        <input type="hidden" name="id" id="id">
        <div class="inline form-field">
            <label>类别码</label>
            <div>
                <input class="easyui-textbox" type="text" id="code"
                       name="code" data-options="required:true" readonly>
            </div>
        </div>
        <div class="inline form-field">
            <label>类别名称</label>
            <div>
                <input class="easyui-textbox" type="text" id="name"
                       name="name" data-options="required:true">
            </div>
        </div>
        <div class="inline form-field">
            <label>代码</label>
            <div>
                <input class="easyui-textbox" type="text" id="value"
                       name="value" data-options="required:true">
            </div>
        </div>
        <div class="inline form-field">
            <label>名称</label>
            <div>
                <input class="easyui-textbox" type="text" id="text"
                       name="text" data-options="required:true">
            </div>
        </div>
        <div class="inline form-field">
            <label>索引号</label>
            <div>
                <input class="easyui-numberspinner" type="text" id="index"
                       name="index" data-options="min:1,editable:false" value="1">
            </div>
        </div>
    </form>
</div>
<script>
    function doInit(dialog){
        $('#code').textbox('textbox').focus();

        var selected = dialog.getData('selected');
        $('#code').textbox('setValue',selected.code);
        $('#name').textbox('setValue',selected.name);
        $('#value').textbox('setValue',selected.value);
        $('#text').textbox('setValue',selected.text);
        $('#index').numberspinner('setValue',selected.index);
    }

    function doSave(dialog){
        var selected = dialog.getData('selected');
        $('#editFrm').form('submit', {
            url: 'plugins/dictionary/'+selected.id+'/edit',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(data){
                var data = eval('(' + data + ')');
                var callback = dialog.getData('callback');
                callback(data, dialog);
            }
        });
    }
</script>
