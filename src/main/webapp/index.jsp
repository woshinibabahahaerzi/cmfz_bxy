<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>首页</title>

    <%--引入bootstrap  css--%>
    <link rel="stylesheet" href="${app}/statics/boot/css/bootstrap.min.css">
    <%--引入bootstrap和jqgrid整合的 css--%>
    <link rel="stylesheet" href="${app}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--jq--%>
    <script type="text/javascript" src="${app}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <%--引入bootstrap  js--%>
    <script type="text/javascript" src="${app}/statics/boot/js/bootstrap.min.js"></script>
    <%--引入中文包--%>
    <script type="text/javascript" src="${app}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--引入jqgrid 的js--%>
    <script type="text/javascript" src="${app}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%--引入文件上传--%>
    <script type="text/javascript" src="${app}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <%--引入kindeditor的js--%>
    <script charset="utf-8" src="${app}/statics/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${app}/statics/kindeditor/lang/zh-CN.js"></script>





</head>
<body>
<%--顶部导航条--%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a >欢迎：${sessionScope.admin}</a></li>
                <li><a href="#">安全退出</a></li>

            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<%--左侧栅格--%>
<div class="col-xs-2">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title text-center">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        <h4>轮播图管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body text-center">
                    <a href="javascript:$('#contentLayout').load('statics/banner/banner-show.jsp')" class="btn btn-default">轮播图详情</a>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        <h4>专辑管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                <div class="panel-body text-center">
                    <a href="javascript:$('#contentLayout').load('statics/album/album-show.jsp')" class="btn-default">专辑详情</a>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        <h4>文章管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                <div class="panel-body text-center">
                    <a href="javascript:$('#contentLayout').load('statics/article/article-show.jsp')" class="btn-default" >文章详情</a>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFour">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                        <h4>用户管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                <div class="panel-body text-center">
                    <a href="" class="btn-default">用户详情</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="col-xs-10" id="contentLayout">
    <div class="jumbotron">
        <h2 style="padding-left: 100px">持明法洲后台管理系统!</h2>
    </div>
    <img src="statics/image/shouye.png" alt="">
</div>
<%--底部页脚--%>
<div class="panel panel-footer text-center" style="height: 40px">
    <h4>持明法洲后台管理系统@百知教育2019-10-25</h4>
</div>
</body>
</html>