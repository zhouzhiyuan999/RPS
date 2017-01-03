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
<form class="form-inline definewidth m20" action="regAction" method="get">
    用户名称：
    <input type="text" name="filter" id="username" class="abc input-default" placeholder="搜索" value="<s:property value="filter"/>">&nbsp;&nbsp;
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">新增用户</button>
</form>
<%--<s:property value="users.size()" />--%>
<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th>用户id</th>
        <th>用户名称</th>
        <th>真实姓名</th>
        <th>权限</th>
        <%--<th>部门</th>--%>
        <th>最后登录时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <s:iterator value="users" id="user">
        <tr>
            <td><s:property value="#user._id"/></td>
            <td><s:property value="#user.username"/></td>
            <td><s:property value="#user.realname"/></td>
            <td><s:property value="#user.role"/></td>
            <%--<td><s:property value="#user.dept"/></td>--%>
            <td><s:property value="#user.lasttime"/></td>
            <td>
                <a href="regAction?page=edit&_id=<s:property value="#user._id"/>">编辑</a>
                <s:if test='#user.role!="admin"'>
                    <a href="javascript:void(0)" onclick="del('<s:property value="#user._id"/>');" >删除</a>
                </s:if>
            </td>
        </tr>
        <%--<a href="regAction!del?_id=<s:property value="#user._id"/>"><s:property value="#user"/></a> <br>--%>
    </s:iterator>
</table>

</body>
</html>
<script>
    $(function () {
		$('#addnew').click(function(){
				window.location.href="regAction?page=add";
		 });
    });
	function del(id)
	{
		if(window.confirm("确定要删除吗？"))
		{
			window.location.href="regAction!del?_id="+id;
		}
	}
</script>