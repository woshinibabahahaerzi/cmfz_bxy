<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- 引入 echarts.js -->
    <script src="${app}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
    <script>
        var goEasy = new goEasy({
            host:'hangzhou.goeasy.io',
            appkey: "BC-e5d4279b789f4f21903f02cf71db6e00",
        });
        //接收消息
        goEasy.subscribe({
            channel: "myTest",
            onMessage: function (message) {
                console.log("Channel:" + message.channel + " content:" + message.content);
            }
        });
    </script>
</head>
<body>

</body>
</html>