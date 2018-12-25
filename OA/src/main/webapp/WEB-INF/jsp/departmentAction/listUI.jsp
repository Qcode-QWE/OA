<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>部门列表</title>
	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
</head>
<body>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 部门管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>
<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="150px">部门名称</td>
				<td width="150px">上级部门名称</td>
				<td width="200px">职能说明</td>
				<td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="departments">
        
        <s:iterator value="%{departments}">
			<tr class="TableDetail1 template">
				<td><s:a action="departmentAction_listUI?parentId=%{departmentId}">${name}</s:a> &nbsp;</td>
				<td>${parent.name}</td>
				<td>${description}</td>
				<td>
					<shiro:hasPermission name="department/departmentAction_delete">
						<s:a action="departmentAction_delete?department.departmentId=%{departmentId}" onclick="return window.confirm('这将删除所有的下级部门，您确定要删除吗？')">删除</s:a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="department/departmentAction_edit">
						<s:a action="departmentAction_editUI?department.departmentId=%{departmentId}">修改</s:a>
					</shiro:hasPermission>
					
					&nbsp;
				</td>
			</tr>
		</s:iterator>	
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
       		<shiro:hasPermission name="department/departmentAction_add">
				<s:a action="departmentAction_addUI?parentId=%{parentId}"><img src="${pageContext.request.contextPath}/style/images/createNew.png" /></s:a>
			</shiro:hasPermission>
            
           <c:if test="${departments==null||parentId != null }">
           	<s:a action="departmentAction_listUI?parentId=%{parent.parent.departmentId}">返回上一级</s:a>
           </c:if>
           	
        </div>
    </div>
</div>

<!--说明-->	
<div id="Description"> 
	说明：<br />
	1，列表页面只显示一层的（同级的）部门数据，默认显示最顶级的部门列表。<br />
	2，点击部门名称，可以查看此部门相应的下级部门列表。<br />
	3，删除部门时，同时删除此部门的所有下级部门。
</div>

</body>
</html>
