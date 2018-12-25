<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/forum.css" />
</head>
<body>
<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 主题搜索
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<form action="${pageContext.request.contextPath}/topic/topicAction_find.action">
	<s:hidden name="forum.forumId" value="%{forum.forumId}"></s:hidden>
	<div id="TableTail">
		<div id="TableTail_inside">
			<table border="0" cellspacing="0" cellpadding="0" height="100%" align="left">
				<tr valign=bottom>
					<td></td>
					<td>
						请输入查询内容:&nbsp;&nbsp;&nbsp; <input name="findName" value="${findName}">
						<input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG" align="ABSMIDDLE"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
<!--内容显示-->	
<div id="MainArea">
	<div id="PageHead"></div>
		<!-- <center> -->
		<div class="ItemBlock_Title1" style="width: 98%">
			<font class="MenuPoint">  </font>
			<font class="MenuPoint">  </font>
			<font class="MenuPoint">  </font>
			<span style="margin-left:30px;">
			</span>
		</div>
		<s:if test="%{page!=null&&page.records!=null}">
		<s:iterator value="%{page.records}">
			<div class="ForumPageTableBorder dataContainer" datakey="replyList">
			
				<!--显示主题标题等-->
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr valign="bottom">
						<td width="3" class="ForumPageTableTitleRight">&nbsp;</td>
					</tr>
					<tr height="1" class="ForumPageTableTitleLine"><td colspan="4"></td></tr>
				</table>
	
				<div class="ListArea">
					<table border="0" cellpadding="0" cellspacing="1" width="100%">
						<tr>
							<td rowspan="3" width="130" class="PhotoArea" align="center" valign="top">
								<!--作者头像-->
								<div class="AuthorPhoto">
									<img border="0" width="110" height="110" src="${pageContext.request.contextPath}/style/images/defaultAvatar.gif" 
										onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/style/images/defaultAvatar.gif';" />
								</div>
								<!--作者名称-->
								<div class="AuthorName">${author.name}</div>
							</td>
							<td align="center">
								<ul class="TopicFunc">
									<!--操作列表-->
									<li class="TopicFuncLi">
										<a class="detail" href="${pageContext.request.contextPath}/topic/topicAction_show.action?topic.topicId=${topic.topicId}">
											<img border="0" src="${pageContext.request.contextPath}/style/images/edit.gif" />查看该主题
										</a>
									</li>
									<!-- 文章表情与标题 -->
									<li class="TopicSubject">
										<img width="19" height="19" src="${pageContext.request.contextPath}/style/images/face/${topic.faceIcon}.gif"/>
										${title}
									</li>
								</ul>
							</td>
						</tr>
						<tr><!-- 文章内容 -->
							<td valign="top" align="center">
								<div class="Content">${content}</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</s:iterator>
		</s:if>
		<!--分页信息-->
		<s:if test="%{page!=null&&page.records!=null}">
		<%@ include file="/WEB-INF/jsp/public/pageView.jspf" %>
		<script type="text/javascript">
			function gotoPage( pageNum ){
				window.location.href = "topicAction_find.action?forum.forumId=${forum.forumId}&findName=${findName}&pageNum=" + pageNum;
			}
		</script>
		</s:if>
</div>
</body>
</html>