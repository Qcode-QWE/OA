<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户列表</title>
    <%@ include file="/WEB-INF/jsp/public/common.jspf" %>
</head>

<body>
<div id="Title_bar">
    <div id="Title_bar_Head"> 
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 用户管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
	<form action="${pageContext.request.contextPath}/user/userAction_listUI.action">
		<div id="TableTail">
			<div id="TableTail_inside">
				<table border="0" cellspacing="0" cellpadding="0" height="100%" align="left">
					<tr valign=bottom>
						<td></td>
						<td>							
							姓名:<input type="text" name="user.name" value=""/>
							所属部门:
							<select name="departmentId" >
								<option value="0">====请选择====</option>
								<c:forEach items="${departments}" var="department">
									<option value="${department.departmentId}" ${departmentId==department.departmentId?"selected":""}>${department.name}</option>
								</c:forEach>
							</select>
							岗位:
							<select name="RoleId" >
								<option value="0">==请选择==</option>
								<c:forEach items="${roleList}" var="role">
									<option value="${role.roleId}" ${RoleId==role.roleId?"selected":""}>${role.name}</option>
								</c:forEach>
							</select>
						
							<input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG" align="ABSMIDDLE"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
                <td width="100">登录名</td>
                <td width="100">姓名</td>
                <td width="100">所属部门</td>
                <td width="200">岗位</td>
                <td>备注</td>
                <td>相关操作</td>
            </tr>
        </thead>
        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="users">
        
        <s:iterator value="%{page.records}"> 
            <tr class="TableDetail1 template">
                <td>${loginName}&nbsp;</td>
                <td>${name}&nbsp;</td>
                <td>${departmentId.name}&nbsp;</td>
                <td>
                	<s:iterator value="roles">
                		${name}&nbsp;
                	</s:iterator>
                	&nbsp;
                </td>
                <td>${description}&nbsp;</td>
                <td>
                	<shiro:hasPermission name="user/userAction_delete">
                		<s:a action="userAction_delete?user.userId=%{userId}" onclick="return delConfirm()">删除</s:a>
                	</shiro:hasPermission>
                    <shiro:hasPermission name="user/userAction_edit">
                    	<s:a action="userAction_editUI?user.userId=%{userId}">修改</s:a>
                    </shiro:hasPermission>
                    
                    <shiro:hasPermission name="user/userAction_initPassword">
                    	<s:a action="userAction_initPassword?user.userId=%{userId}" onclick="return window.confirm('您确定要初始化密码为123吗？')">初始化密码</s:a>
                    </shiro:hasPermission>
					
                </td>
            </tr>
        </s:iterator> 
         
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
    	
        <div id="TableTail_inside">
            <s:a action="userAction_addUI"><img src="${pageContext.request.contextPath}/style/images/createNew.png" /></s:a>
        </div>
    </div>
</div>
	<!--分页信息-->
	<%@ include file="/WEB-INF/jsp/public/pageView.jspf" %>
	<script type="text/javascript">
		function gotoPage( pageNum ){
			window.location.href = "userAction_listUI.action?pageNum=" + pageNum;
		}
	</script>
</body>
</html>
