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

<div class="definewidth m20">
    正在开发中...
</div>
<%--<form action="infoAction!update" method="post" class="definewidth m20">--%>
    <%--<div class="definewidth"><button type="button" class="btn btn-success" id="upnew">更改密码</button></div>--%>
    <%--<table class="table table-bordered table-hover definewidth m10">--%>
        <%--<tr>--%>
            <%--<td class="tableleft">用户id</td>--%>
            <%--<td><s:property value="data._id"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td class="tableleft">用户名</td>--%>
            <%--<td><s:property value="data.username"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td class="tableleft">真实姓名</td>--%>
            <%--<td><input type="text" name="realname" value="<s:property value="data.realname"/>"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td class="tableleft">部门</td>--%>
            <%--<td><s:property value="data.dept"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td class="tableleft"></td>--%>
            <%--<td>--%>
                <%--<button type="submit" class="btn btn-primary" type="button">保存</button>--%>
                <%--&lt;%&ndash;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>&ndash;%&gt;--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</form>--%>
</body>
</html>
<script>
    $(function () {
        $('#upnew').click(function(){
				window.location.href="infoAction?page=upnew";
		 });
    });
</script>