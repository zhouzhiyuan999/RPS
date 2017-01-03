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

<form id="pass" action="infoAction!update" method="post" class="definewidth m20">
    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td class="tableleft">设置新密码</td>
            <td><input type="password" id="password" name="password" /></td>
        </tr>
        <tr>
            <td class="tableleft">重复新密码</td>
            <td><input type="password" id="repsword" name="repsword" /></td>
        </tr>
        <tr>
            <td class="tableleft"></td>
            <td>
                <button type="submit" class="btn btn-primary">保存</button>
                &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script>
    $(function () {
        $('#upnew').click(function(){
				window.location.href="infoAction?page=upnew";
		 });
    });
    $(function () {
        $('#backid').click(function(){
            window.location.href="infoAction";
        });
    });
    $("#pass").submit(function(){
        var password = $("#password").val();
        var repsword = $("#repsword").val();
        if(password === ''){
            alert('密码不能为空');
            return false;
        }
        if(password != repsword) {
            alert("两次密码不同，请重新输入");
            return false;
        }
    });
</script>