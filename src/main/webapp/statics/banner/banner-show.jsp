<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>

<script>
    $("#banner-table").jqGrid({
        url : '${app}/banner/selectAllBanners',
        datatype : "json",
        colNames : [ 'ID', '名称', '封面', '状态', '描述','创建日期' ],
        colModel : [
            {name : 'id',hidden:true},
            {name : 'name',editable:true},
            {name : 'cover',editable:true,edittype:'file',formatter:function (value,option,row) {
                    return "<img style='width:100px',height:'80px' src='${app}/statics/banner/image/"+value+"'></img>";
                }},
            {name : 'status',editable:true,edittype:'select'
                ,editoptions:{value:'1:正常;0:冻结'},formatter:function (value,option,row) {
                    if (value == 1) return "正常";
                    else return "冻结";
                }},
            {name : 'description',editable:true},
            {name : 'createDate',}
        ],
        styleUI:'Bootstrap',
        autowidth:true,
        //开启子表格
        subGrid : true,
        height:'350px',
        rowNum : 3,
        rowList : [ 3, 6, 10 ],
        pager : '#banner-pager',
        viewrecords : true,
        caption : "轮播图详细信息",
        editurl : "${app}/banner/edit"
    }).navGrid("#banner-pager", {edit : true,add : true,del : true},
        {
            //控制修改操作
            closeAfterEdit:true
        },{
            //控制添加操作
            closeAfterAdd:true,
            afterSubmit:function (response) {
                //Map   code  == 200    500
                var code = response.responseJSON.code;
                var id = response.responseJSON.data;
                if (code == 200){
                    console.log("id         "+id);
                    $.ajaxFileUpload({
                        url:'${app}/banner/upload',
                        datatype:'json',
                        data:{id:id},
                        fileElementId:'cover',
                        success:function (result) {
                            $("#banner-table").trigger("reloadGrid")
                        }
                    })
                }
                return "1236547";
            }
        },{
            //控制删除操作
        });



</script>

<table id="banner-table"></table>
<div id="banner-pager"></div>