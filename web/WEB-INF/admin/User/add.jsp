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
<form action="regAction!add" method="post" class="definewidth m20">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">登录名*</td>
        <td><input type="text" name="username"/></td>
    </tr>
    <tr>
        <td class="tableleft">密码*</td>
        <td><input type="password" name="password"/></td>
    </tr>
    <tr>
        <td class="tableleft">真实姓名</td>
        <td><input type="text" name="realname"/></td>
    </tr>
    <tr>
        <td class="tableleft">权限</td>
        <td><select name="role">
            <option value="user">普通用户</option>
            <option value="manager">部门主管</option>
        </select></td>
    </tr>
    <tr>
        <td class="tableleft">部门</td>
        <td><select name="dept">
            <s:iterator value="depts" id="dept">
                <option value="<s:property value="#dept.number"/>"><s:property value="#dept.name"/></option>
            </s:iterator>
        </select></td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary">保存</button> &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script>
    $(function () {
		$('#backid').click(function(){
				window.location.href="regAction";
		 });

    });
</script>