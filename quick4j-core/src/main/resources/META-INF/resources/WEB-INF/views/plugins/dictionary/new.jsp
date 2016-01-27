<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="static/css/quick4j.css">
<div style="padding: 10px;">
    <form id="editFrm" method="post">
        <div class="inline form-field">
            <label>类别码</label>

            <div>
                <input class="easyui-textbox" type="text" id="code"
                       name="code" data-options="required:true, width:300">
            </div>
        </div>
        <div class="inline form-field">
            <label>类别名称</label>

            <div>
                <input class="easyui-textbox" type="text" id="name"
                       name="name" data-options="required:true,width:300">
            </div>
        </div>
        <div class="inline form-field">
            <label>代码</label>

            <div>
                <input class="easyui-textbox" type="text" id="value"
                       name="value" data-options="required:true, width:300">
            </div>
        </div>
        <div class="inline form-field">
            <label>名称</label>

            <div>
                <input class="easyui-textbox" type="text" id="text"
                       name="text" data-options="required:true, width:300">
            </div>
        </div>
        <div class="inline form-field">
            <label>索引号</label>

            <div>
                <input class="easyui-numberspinner" type="text" id="index"
                       name="index" data-options="min:1,editable:false, width:300" value="1">
            </div>
        </div>
    </form>
</div>
<script>
    $(document).ready(function () {
        $('#code').focus();
    })

    function doSave(win) {
        $('#editFrm').form('submit', {
            url: 'plugins/dictionary/new',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var data = eval('(' + data + ')');
                if (data.status == 200) {
                    win.getData('datagrid').datagrid('reload');
                    win.close();
                }
            }
        });
    }
</script>
