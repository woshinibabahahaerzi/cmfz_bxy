<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html lang="en">
<head>
    <title>KindEditor基础使用</title>
    <script charset="utf-8" src="${app}/statics/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${app}/statics/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                width : '500px',
                height:'300px',
                resizeType:2,
                //显示图片空间按钮
                allowFileManager:true,
                //图片空间按钮发送的URL路径
                fileManagerJson:'${app}/article/browser',
                //指定上传文件的服务器端程序
                uploadJson:'${app}/article/upload',
                //filePostName:'aa'
            });
        });
    </script>
</head>
<body>
    <textarea id="editor_id" name="content" >

    </textarea>
</body>
</html>