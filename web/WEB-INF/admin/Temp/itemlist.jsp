<%@ page import="com.rps.jdbc.DeptDB" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.sorted.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/ckform.js"></script>
    <script type="text/javascript" src="js/common.js"></script>

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
</head>
<body>
<div class="definewidth m20">模板标题：<s:property value="form.title"/></div>
<form class="form-inline definewidth m20" action="tempAction!item" method="get">
    <input type="hidden" name="fid" value="<s:property value="form._id"/>">
    属性名称：
    <input type="text" name="filter" id="rolename"class="abc input-default" placeholder="搜索" value="<s:property value="filter"/>">&nbsp;&nbsp;
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">添加输入项</button>
</form>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>属性id</th>
        <th>属性名称</th>
        <th>属性标签</th>
        <th>数据类型</th>
        <th>数据内容</th>
        <th>绑定变量</th>
        <th>序号</th>
        <th>必填</th>
        <th>操作</th>
    </tr>
    </thead>
    <s:iterator value="items" id="item">
        <tr>
            <td><s:property value="#item.mid"/></td>
            <td><s:property value="#item.name"/></td>
            <td><s:property value="#item.label"/></td>
            <td><s:property value="#item.datatype"/></td>
            <td><s:property value="#item.content"/></td>
            <td><s:property value="#item.bind"/></td>
            <td><s:property value="#item.seq"/></td>
            <td><s:property value="#item.isneed"/></td>
            <td>
                <%--<a href="tempAction?page=temp&fid=<s:property value="#form._id"/>">查看</a>--%>
                <a href="tempAction!item?page=edit&fid=<s:property value="form._id"/>&mid=<s:property value="#item.mid"/>">编辑</a>
                <a href="javascript:void(0)" onclick="del('<s:property value="form._id"/>','<s:property value="#item.mid"/>');" >删除</a>
            </td>
        </tr>
        <%--<a href="regAction!del?_id=<s:property value="#user._id"/>"><s:property value="#user"/></a> <br>--%>
    </s:iterator>
</table>
<div class="definewidth m20">
    <%--&nbsp;&nbsp;--%>
    <button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
</div>
</body>
</html>
<script>
    $(function () {
        $('#addnew').click(function(){
            window.location.href="tempAction!item?fid=<s:property value="form._id"/>&page=add";
        });
    });
    $(function () {
        $('#backid').click(function(){
            window.location.href="tempAction";
        });
    });
    function del(fid,mid)
    {
        if(window.confirm("确定要删除吗？"))
        {
            window.location.href="tempAction!itemdel?fid="+fid+"&mid="+mid;
        }
    }
</script>