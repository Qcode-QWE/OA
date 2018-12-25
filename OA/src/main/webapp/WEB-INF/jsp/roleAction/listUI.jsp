<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>岗位列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>
 
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 岗位管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<form action="${pageContext.request.contextPath}/role/roleAction_listUI.action">
	<div id="TableTail">
		<div id="TableTail_inside">
			<table border="0" cellspacing="0" cellpadding="0" height="100%" align="left">
				<tr valign=bottom>
					<td></td>
					<td>							
						岗位名称:<input type="text" name="roleName" value="${roleName}"/>
						岗位说明:<input type="text" name="roleDescription" value="${roleDescription}"/>
					
						<input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG" align="ABSMIDDLE"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            	<td width="200px">岗位名称</td>
                <td width="300px">岗位说明</td>
                <td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="roleList">
        
        <s:iterator value="%{page.records}">
			<tr class="TableDetail1 template">
				<td>${name}&nbsp;</td>
				<td>${description}&nbsp;</td>
				<td>
					<shiro:hasPermission name="role/roleAction_delete">
						<s:a action="role/roleAction_delete?roleId=%{roleId}" onclick="return delConfirm()">删除</s:a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="role/roleAction_edit">
						<s:a action="role/roleAction_editUI?roleId=%{roleId}">修改</s:a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="role/roleAction_edit">
						<s:a action="role/roleAction_setPrivilegeUI?roleId=%{roleId}">设置权限</s:a>
					</shiro:hasPermission>
					
				</td>
			</tr>
		</s:iterator>
			
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <s:a action="roleAction_addUI"><img src="${pageContext.request.contextPath}/style/images/createNew.png" /></s:a>
        </div>
    </div>
    
    <!--分页信息-->
	<%@ include file="/WEB-INF/jsp/public/pageView.jspf" %>
	<script type="text/javascript">
		function gotoPage( pageNum ){
			window.location.href = "roleAction_listUI.action?pageNum=" + pageNum;
		}
	</script>
</div>
</body>
</html>
