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
<form class="form-inline definewidth m20" action="deptAction" method="get">
    部门名称：
    <input type="text" name="filter" id="rolename"class="abc input-default" placeholder="搜索" value="<s:property value="filter"/>">&nbsp;&nbsp;
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">新增部门</button>
</form>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>人员个数</th>
        <th>操作</th>
    </tr>
    </thead>
    <s:iterator value="depts" id="dept">
        <tr>
            <td><s:property value="#dept.number"/></td>
            <td><s:property value="#dept.name"/></td>
            <td><s:property value="#dept.users.size()"/></td>
            <td>
                <a href="deptAction?page=dept&number=<s:property value="#dept.number"/>">查看</a>
                <a href="deptAction?page=edit&number=<s:property value="#dept.number"/>">编辑</a>
                <s:if test='#dept.number!=DEFAULT'>
                    <a href="javascript:void(0)" onclick="del('<s:property value="#dept.number"/>');" >删除</a>
                </s:if>
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
            window.location.href="deptAction?page=add";
        });
    });
    function del(number)
    {
        if(window.confirm("确定要删除吗？"))
        {
            window.location.href="deptAction!del?number="+number;
        }
    }
</script>