<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="${app}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${app}/statics/boot/js/echarts.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>


<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持明法洲用户注册统计'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["近一周","近两周","近三周"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type: 'bar',
            //data: [15, 2, 36]
        },{
            name: '女',
            type: 'bar',
            //data: [5, 20, 96]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    $.ajax({
        url:'${app}/user/selectCount',
        datatype:'json',
        type:'post',
        success:function (result) {
            myChart.setOption(
                {series:[
                    {   name: '男',
                        data: [result.nan1,result.nan2,result.nan3]
                    },{
                        name: '女',
                        data: [result.nv1,result.nv2,result.nv3]
                    }
                ]}
            );
        }
    })

    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io',
        appkey: "BC-e5d4279b789f4f21903f02cf71db6e00",
    });

    //接收消息
    goEasy.subscribe({
        channel: "qwe",
        onMessage: function (message) {
            $.ajax({
                url:'${app}/user/selectCount',
                datatype:'json',
                type:'post',
                success:function (result) {
                    myChart.setOption(
                        {series:[
                                {   name: '男',
                                    data: [result.nan1,result.nan2,result.nan3]
                                },{
                                    name: '女',
                                    data: [result.nv1,result.nv2,result.nv3]
                                }
                            ],title: {
                                text: message.content
                            },
                        }
                    );
                }
            })
        }
    });

</script>
</body>
</html>