<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>GoEasy</title>
    <!-- 引入 echarts.js -->
    <script src="${app}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
</head>
<body>

<script type="text/javascript">


    $(function () {
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io',
            appkey: "BC-e5d4279b789f4f21903f02cf71db6e00",
        });

        goEasy.publish({
            channel: "qwe",
            message: "Hello, GoEasy!"
        });
    })

</script>
</body>
</html>