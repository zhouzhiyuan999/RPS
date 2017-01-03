<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.rps.action.LogAction" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String user = (String)session.getAttribute(LogAction.UNAME);
if(user==null || user.equals("admin"))
	 response.sendRedirect(basePath);
%>