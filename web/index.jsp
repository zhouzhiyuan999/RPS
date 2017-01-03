<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 2016/12/7
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
  //重定向到登录的Action
  response.sendRedirect(basePath + "logAction");
%>
