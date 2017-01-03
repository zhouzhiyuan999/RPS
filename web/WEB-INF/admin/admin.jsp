<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/main-min.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="header">

    <div class="dl-title">
        <!--<img src="/chinapost/Public/assets/img/top.png">-->
    </div>

    <div class="dl-log">欢迎您，<span class="dl-log-user">admin</span><a href="logAction!logout" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
</div>
<div class="content">
    <div class="dl-main-nav">
        <div class="dl-inform"><div class="dl-inform-title"><s class="dl-inform-icon dl-up"></s></div></div>
        <ul id="J_Nav"  class="nav-list ks-clear">
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统管理</div></li>
            <%--<li class="nav-item dl-selected"><div class="nav-item-inner nav-order">业务管理</div></li>--%>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
</div>
<script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="assets/js/bui-min.js"></script>
<script type="text/javascript" src="assets/js/common/main-min.js"></script>
<script type="text/javascript" src="assets/js/config-min.js"></script>
<script>
    BUI.use('common/main',function(){
        var config = [
            {id:'1',homePage:'5',menu: [
                {text:'系统管理',items: [
                    {id:'5',text:'首页',href:'regAction?page=hello'},
                    {id:'2',text:'部门管理',href:'deptAction'},
                    {id:'3',text:'模板管理',href:'tempAction'},
                    {id:'4',text:'用户管理',href:'regAction'}
                ]}
            ]}
        ];
//            {id:'7',homePage : '9',menu:[{text:'业务管理',items:[
//                    {id:'9',text:'查询业务',href:'Node/index.html'}]}]},
//            ]}

        new PageUtil.MainPage({
            modulesConfig : config
        });
    });
</script>
<%--<div style="text-align:center;">--%>
<%--<p>来源：<a href="http://www.mycodes.net/" target="_blank">源码之家</a></p>--%>
<%--</div>--%>
</body>
</html>