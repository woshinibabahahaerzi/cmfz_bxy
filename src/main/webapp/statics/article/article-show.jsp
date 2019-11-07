<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<div class="modal fade"  role="dialog" id="article-modal">
    <script>

        $("#article-table").jqGrid(
            {
                url : '${app}/article/selectAllArticles',
                datatype : "json",
                colNames : [ 'ID', '名称', '作者', '内容','创建日期','操作'],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'title',width : 60,editable:true},
                    {name : 'author',width : 40,editable:true},
                    {name : 'content',hidden:true,width:180,editable:true},
                    {name : 'createDate',width : 80},
                    {name : 'option',width : 80,formatter:function (value,options,rows) {
                            return "<a class='btn btn-primary' onclick=\"openModal('edit','"+rows.id+"')\"> 修改</a>";
                        }}
                ],
                styleUI:'Bootstrap',
                rowNum : 4,
                rowList : [ 2, 4, 6,10,20 ],
                pager : '#article-pager',
                viewrecords : true,
                editurl : "${app}/article/edit",
                autowidth:true,
                height:'350px'
            }).navGrid("#article-pager", {edit : false,add : false,del : true,search:false},
            {
                //控制修改
                closeAfterEdit:true
            },{
                //控制添加
                closeAfterAdd:true
            },{
                //控制删除
            });

        //编辑KindEditor相关参数
        KindEditor.create('#editor_id',{
            width : '100%',
            height:'300px',
            resizeType:1,
            //显示图片空间按钮
            allowFileManager:true,
            //图片空间按钮发送的URL路径
            fileManagerJson:'${app}/article/browser',
            //指定上传文件的服务器端程序
            uploadJson:'${app}/article/upload',
            //将编辑器中的内容进行格式转换
            afterBlur:function () {
                this.sync();
            },afterChange(){
                this.sync();
            }
        });

        //打开模态框
        function openModal(oper,id) {
            KindEditor.html("#editor_id","");
            var article = $("#article-table").jqGrid('getRowData',id);
            $("#article-oper").val(oper);
            $("#article-id").val(id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.author);
            KindEditor.html("#editor_id",article.content);
            $("#article-modal").modal('show');
        }



        //添加文章
        function saveArticle() {
            $.ajax({
                url:'${app}/article/edit',
                datatype:'json',
                data:$("#article-form").serialize(),
                type:'POST',
                success:function () {
                    KindEditor.html("#editor_id","");
                    //关闭模态框
                    $("#article-modal").modal('hide');
                    //刷新表格
                    $("#article-table").trigger('reloadGrid');
                }
            })
             KindEditor.create('#editor_id',{
                 //将编辑器中的内容进行格式转换
                 afterBlur:function () {
                     this.sync();
                 }
             });
        }
        //导出文章
        function exportArticle() {
            location.href="${app}/article/exportArticle";
        }


    </script>
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">我的文章</h4>
            </div>
            <div class="modal-body">
                <form class="form-inline" id="article-form">
                    <input type="hidden" name="oper" id="article-oper">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title">标题</label>
                        <input type="text"  name="title" class="form-control" id="article-title" placeholder="请输入标题...">
                    </div>
                    <div class="form-group">
                        <label for="article-author">作者</label>
                        <input type="text" name="author" class="form-control" id="article-author" placeholder="请输入作者...">
                    </div>
                    <div class="form-group">
                        <textarea id="editor_id" name="content">
                        </textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveArticle()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有文章</a></li>
    <li role="presentation"><a onclick="openModal('add')">添加文章</a></li>
    <li role="presentation"><a onclick="exportArticle()">导出文章</a></li>
</ul>
<table id="article-table"></table>
<div id="article-pager"></div>