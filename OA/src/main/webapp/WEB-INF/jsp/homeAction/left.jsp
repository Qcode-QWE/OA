<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
	<title>导航菜单</title>
	<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
	<link type="text/css" rel="stylesheet" href="style/blue/menu.css" />
	<script type="text/javascript">
		function menuClick( menuDiv ){
			// $(".MenuLevel2").not( $(menuDiv).next() ).hide();
			$(menuDiv).next().toggle(); // show(), hide(), toggle()
		}
	</script>
	
</head>
<body style="margin: 0">
<div id="Menu"> 

    <ul id="MenuUl">
		<%-- 顶级菜单 --%>
		<s:iterator value="#application.topPrivileges">
		<%-- <s:if test="#session.user.hasPrivilegeByName(name,#session.set)"> --%>
		<shiro:hasPermission name="${url}">
	        <li class="level1">
	            <div onClick="menuClick(this);" class="level1Style">
	            	<img src="${pageContext.request.contextPath}/style/images/MenuIcon/${icon}" class="Icon" /> 
	            	${name}
	            </div>
	            <%-- 二级菜单 display: none; --%>
	            <ul style="" class="MenuLevel2">
	            	<s:iterator value="children">
		            	<%-- <s:if test="#session.user.hasPrivilegeByName(name,#session.set)"> --%>
		            	<shiro:hasPermission name="${url}">
		            		<li class="level2">
			                    <div class="level2Style">
				                    <img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> 
				                    <a target="right" href="${pageContext.request.contextPath}/${url}UI.action"> ${name}</a>
			                 	</div>
			                </li>
		            	</shiro:hasPermission>
			              
		               <%--  </s:if> --%>
	            	</s:iterator>
	            </ul>
	        </li>
	   </shiro:hasPermission>
       <%--  </s:if> --%>
		</s:iterator>        
    </ul>
    
</div>
</body>
</html>