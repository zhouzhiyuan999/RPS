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
<div class="definewidth m20">部门名称：<s:property value="dept.name"/></div>
<form class="form-inline definewidth m20" action="deptAction" method="get">
    <input type="hidden" name="page" value="dept">
    <input type="hidden" name="number" value="<s:property value="dept.number"/>">
    用户名称：
    <input type="text" name="filter" id="rolename"class="abc input-default" placeholder="搜索" value="<s:property value="filter"/>">&nbsp;&nbsp;
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp;
    <%--<button type="button" class="btn btn-success" id="addnew">添加用户</button>--%>
</form>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>用户名称</th>
        <th>真实姓名</th>
        <th>权限</th>
        <th>部门</th>
    </tr>
    </thead>
    <s:iterator value="users" id="user">
        <tr>
            <td><s:property value="#user.username"/></td>
            <td><s:property value="#user.realname"/></td>
            <td><s:property value="#user.role"/></td>
            <td>移动至
                <select style = "width:50px;" id="move" name="move" onchange="move('<s:property value="#user.dept"/>','<s:property value="#user._id"/>')">
                    <s:iterator value="depts" id="dept">
                        <option <s:if test='#user.dept==#dept.number'>selected</s:if> value="<s:property value="#dept.number"/>"><s:property value="#dept.name"/></option>
                    </s:iterator>
                </select>
            </td>
        </tr>
        <%--<a href="regAction!del?_id=<s:property value="#user._id"/>"><s:property value="#user"/></a> <br>--%>
    </s:iterator>
</table>
<div class="definewidth m20">
<%--&nbsp;&nbsp;--%>
    <button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
</div>
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
    $(function () {
        $('#backid').click(function(){
            window.location.href="deptAction";
        });
    });
    function move(number,id){
        window.location.href="deptAction!move?number="+number+"&_id="+id+"&move="+$("#move").val();
    }
    function del(number)
    {
        if(window.confirm("确定要删除吗？"))
        {
            window.location.href="deptAction!del?number="+number;
        }
    }
</script>