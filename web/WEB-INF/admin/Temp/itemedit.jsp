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
<form action="tempAction!itemedit" method="post" class="definewidth m20">
    <input type="hidden" name="fid" value="<s:property value="form._id"/>">
    <input type="hidden" name="mid" value="<s:property value="item.mid"/>">
<table class="table table-bordered table-hover ">
    <tr>
        <td width="10%" class="tableleft">所属模板</td>
        <td><s:property value="form.title"/></td>
    </tr>
    <tr>
        <td class="tableleft">属性名称*</td>
        <td><input type="text" name="name" value="<s:property value="item.name"/>"/></td>
    </tr>
    <tr>
        <td class="tableleft">属性标签*</td>
        <td><input type="text" name="label" value="<s:property value="item.label"/>"/></td>
    </tr>
    <tr>
        <td class="tableleft">数据类型</td>
        <td>
            <select name="datatype">
                <option <s:if test='item.datatype=="text"'>selected</s:if> value="text">text</option>
            </select>
        </td>
    </tr>
    <tr>
        <td class="tableleft">数据内容</td>
        <td><input type="text" name="content" value="<s:property value="item.content"/>"/></td>
    </tr>
    <tr>
        <td class="tableleft">绑定变量</td>
        <td><input type="text" name="bind" value="<s:property value="item.bind"/>"/></td>
    </tr>
    <tr>
        <td class="tableleft">序号</td>
        <td><input type="number" name="seq" value="<s:property value="item.seq"/>"/></td>
    </tr>
    <tr>
        <td class="tableleft">必填</td>
        <td>
            <select name="isneed">
                <option <s:if test='user.isneed=="true"'>selected</s:if> value="true">是</option>
                <option <s:if test='user.isneed=="false"'>selected</s:if> value="false">否</option>
            </select>
        </td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary" type="button">保存</button> &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script>
    $(function () {       
		$('#backid').click(function(){
				window.location.href="tempAction!item?fid=<s:property value="form._id"/>";
		 });

    });
</script>