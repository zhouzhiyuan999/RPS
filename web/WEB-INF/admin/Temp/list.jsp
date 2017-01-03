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
<form class="form-inline definewidth m20" action="tempAction" method="get">
    模板名称：
    <input type="text" name="filter" id="rolename"class="abc input-default" placeholder="搜索" value="<s:property value="filter"/>">&nbsp;&nbsp;
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">添加模板</button>
</form>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>模板id</th>
        <th>标题</th>
        <th>描述</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <s:iterator value="forms" id="form">
        <tr>
            <td><s:property value="#form._id"/></td>
            <td><s:property value="#form.title"/></td>
            <td><s:property value="#form.desc"/></td>
            <td><s:property value="#form.created"/></td>
            <td><s:property value="#form.updated"/></td>
            <td>
                <a href="tempAction!item?fid=<s:property value="#form._id"/>">查看</a>
                <a href="tempAction?page=edit&fid=<s:property value="#form._id"/>">编辑</a>
                <a href="javascript:void(0)" onclick="del('<s:property value="#form._id"/>');" >删除</a>
            </td>
        </tr>
        <%--<a href="regAction!del?_id=<s:property value="#user._id"/>"><s:property value="#user"/></a> <br>--%>
    </s:iterator>
</table>
<%--<div class="inline pull-right page">--%>
         <%--10122 条记录 1/507 页  <a href='#'>下一页</a>     <span class='current'>1</span><a href='#'>2</a><a href='/chinapost/index.php?m=Label&a=index&p=3'>3</a><a href='#'>4</a><a href='#'>5</a>  <a href='#' >下5页</a> <a href='#' >最后一页</a>    </div>--%>
</body>
</html>
<script>
    $(function () {
        $('#addnew').click(function(){
            window.location.href="tempAction?page=add";
        });
    });
    function del(id)
    {
        if(window.confirm("确定要删除吗？"))
        {
            window.location.href="tempAction!del?fid="+id;
        }
    }
</script>