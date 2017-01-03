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
<form action="formAction!add" method="post">
    <input type="hidden" name="fid" value="<s:property value="form._id"/>">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">标题</td>
        <td><s:property value="form.title"/></td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">描述</td>
        <td><s:property value="form.desc"/></td>
    </tr>
    <%--<s:bean name="com.rps.comparator.SeqComparator" id="seqComparator">--%>
        <%--<s:sort source="form.items" comparator="seqComparator">--%>
            <s:iterator value="form.items" id="item">
                <tr>
                    <td class="tableleft"><s:property value="#item.name"/><s:if test="#item.isneed==true">*</s:if></td>
                    <td><input type="<s:property value="#item.datatype"/>" name="<s:property value="#item.label"/>" value="<s:property value="#item.content"/>"/></td>
                </tr>
            </s:iterator>
        <%--</s:sort>--%>
    <%--</s:bean>--%>
    <%--<s:iterator value="form.items" id="item">--%>
        <%--<tr>--%>
            <%--<td class="tableleft"><s:property value="#item.name"/><s:if test="#item.isneed==true">*</s:if></td>--%>
            <%--<td><input type="<s:property value="#item.datatype"/>" name="<s:property value="#item.label"/>" value="<s:property value="#item.content"/>"/></td>--%>
        <%--</tr>--%>
    <%--</s:iterator>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">属性名称*</td>--%>
        <%--<td><input type="text" name="name"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">属性标签*</td>--%>
        <%--<td><input type="text" name="label"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">数据类型</td>--%>
        <%--<td>--%>
            <%--<select name="datatype">--%>
                <%--<option selected value="text">text</option>--%>
            <%--</select>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">数据内容</td>--%>
        <%--<td><input type="text" name="content"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">绑定变量</td>--%>
        <%--<td><input type="text" name="bind"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">序号</td>--%>
        <%--<td><input type="number" value=0 name="seq"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td class="tableleft">必填</td>--%>
        <%--<td>--%>
            <%--<select name="isneed">--%>
                <%--<option value="true">是</option>--%>
                <%--<option value="false">否</option>--%>
            <%--</select>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary" type="button">提交</button>
            &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回</button>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script>
    $(function () {       
		$('#backid').click(function(){
				window.location.href="formAction";
		 });
    });
</script>