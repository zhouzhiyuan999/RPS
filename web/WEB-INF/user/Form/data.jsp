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
<div class="definewidth m20"><button type="button" class="btn btn-success" id="backid">返回列表</button></div>
<%--<form class="form-inline definewidth m20" action="formAction" method="get">--%>
    <%--模板名称：--%>
    <%--<input type="text" name="filter" id="rolename"class="abc input-default" placeholder="搜索" value="<s:property value="filter"/>">&nbsp;&nbsp;--%>
    <%--<button type="submit" class="btn btn-primary">查询</button>--%>
<%--</form>--%>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>序号</th>
        <s:iterator value="form.items" id="item">
            <th><s:property value="#item.name"/></th>
        </s:iterator>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <s:iterator value="datas" id="data" status="st">
        <tr>
            <td><s:property value="%{#st.index+1}"/></td>
            <s:iterator value="#data.data" id="item">
                <td><s:property value="#item"/></td>
            </s:iterator>
            <td><s:property value="#data.created"/></td>
            <td><s:property value="#data.updated"/></td>
            <td>
                <%--<a href="formAction?page=add&fid=<s:property value="#form._id"/>">添加数据</a>--%>
                <a href="formAction?page=edit&did=<s:property value="#data._id"/>">编辑</a>
                <a href="javascript:void(0)" onclick="del('<s:property value="#data._id"/>');" >删除</a>
            </td>
        </tr>
        <%--<a href="regAction!del?_id=<s:property value="#user._id"/>"><s:property value="#user"/></a> <br>--%>
    </s:iterator>
</table>
</body>
</html>
<script>
    function del(did)
    {
        if(window.confirm("确定要删除吗？"))
        {
            window.location.href="formAction!del?did="+did;
        }
    }
    $(function () {
        $('#backid').click(function(){
            window.location.href="formAction";
        });
    });
</script>