<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>

<script>
    $("#album-table").jqGrid({
        url: '${app}/album/selectAllAlbums',
        datatype: "json",
        colNames: ['ID', '专辑名称', '专辑封面', '作者', '描述', '集数','创建日期'],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'title', editable: true},
            {
                name: 'cover', editable: true, edittype: 'file', formatter: function (value, option, row) {
                    return "<img style='width:100px',height:'80px' src='${app}/statics/album/image/" + value + "'></img>";
                }
            },
            {name: 'author', editable: true},
            {name: 'brief', editable: true},
            {name: 'chapterCount', editable: true},
            {name: 'createDate',}
        ],
        styleUI: 'Bootstrap',
        autowidth: true,
        //开启子表格
        subGrid: true,
        height: 460,
        rowNum: 3,
        rowList: [3, 6, 10],
        pager: '#album-pager',
        viewrecords: true,
        caption: "专辑详细信息",
        editurl: "${app}/album/edit",
        //子表格相关
        subGridRowExpanded: function (subgrid_id, albumId) {//承载二级表格容器的id  专辑的id
            var subgrid_table_id = subgrid_id + "_t";
            var pager_id = "p_" + subgrid_table_id;


            $("#" + subgrid_id).html(
                "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                "<div id='" + pager_id + "' class='scroll'></div>");

            $("#" + subgrid_table_id).jqGrid(
                {
                    url: "${app}/chapter/selectAllChaptersByAlbumId?albumId=" + albumId,
                    editurl:'${app}/chapter/edit?albumId='+albumId,
                    datatype: "json",
                    colNames: ['ID', '章节名称', '章节大小', '时长', '创建时间', '音频'],
                    colModel: [
                        {name: "id",hidden:true},
                        {name: "title",width : 55,editable:true},
                        {name: "size",width : 55},
                        {name: "duration",width : 55},
                        {name: "createDate",width : 55},
                        {name: "url",width:100,editable:true,edittype:'file',formatter:function (value,options,rows) {
                            return "<audio controls>\n" +
                                "  <source src='${app}/statics/chapter/music/"+value+"' type=\"audio/mpeg\">\n" +
                                "</audio>";
                        }}
                    ],
                    styleUI:'Bootstrap',
                    autowidth:true,
                    rowNum: 2,
                    pager: pager_id,
                    height: 280,
                }).jqGrid('navGrid', "#" + pager_id, {edit: true, add: true, del: true},
                {
                    //控制章节的修改操作
                    closeAfterEdit:true
                },{
                    //控制章节的添加操作
                    closeAfterAdd:true,
                    afterSubmit:function (response) {
                        var code =  response.responseJSON.code;
                        var id =  response.responseJSON.data;
                        if (code == 200){
                            $.ajaxFileUpload({
                                url:'${app}/chapter/upload',
                                datatype:'json',
                                data:{id:id},
                                fileElementId:'url',
                                // type:'post',
                                success:function () {
                                    $("#album-table").trigger("reloadGrid");
                                }
                            })
                        }
                        return "123";
                    }

                },{
                    //控制章节的删除操作
                    afterSubmit:function () {
                        $("#album-table").trigger("reloadGrid");
                        return "123";
                    }
                }
            );}
    }).navGrid("#album-pager", {edit: true, add: true, del: true},
                {
                    //控制修改操作
                    closeAfterEdit: true
                }, {
                    //控制添加操作
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        //Map   code  == 200    500
                        var code = response.responseJSON.code;
                        var id = response.responseJSON.data;
                        if (code == 200) {
                            console.log("id         " + id);
                            $.ajaxFileUpload({
                                url: '${app}/album/upload',
                                datatype: 'json',
                                data: {id: id},
                                fileElementId: 'cover',
                                success: function (result) {
                                    $("#banner-table").trigger("reloadGrid")
                                }
                            })
                        }
                        return "1236547";
                    }
                }, {
                    //控制删除操作
                });


</script>

<table id="album-table"></table>
<div id="album-pager"></div>