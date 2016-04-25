<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored = "false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板　ホーム画面</title>
</head>
<body>



		<div class = "header">
			<a href = "./">ホーム</a>
			<a href = "settings">設定</a>
			<a href = "logout">ログアウト</a>
		</div>
		<div class = "profile">
			<div class = "name">ようこそ<c:out value = "${loginUser.name }"></c:out>さん</div>

		</div>
		<div class = "newpost">
			<a href = "newpost">新規投稿</a>
		</div>

	<div class = "messages">
		<c:forEach items = "${messages }" var = "message">
		<div class = "message">
			<div class = "account-name">
				<span class = "account"><c:out value = "${message.account }"></c:out></span>
				<span class = "name"><c:out value = "${message.name }"></c:out></span>
			</div>
			<div class = "text"><c:out value = "${message.text }"></c:out></div>
			<div class = "date"><fmt:formatDate value="${message.insertDate }" pattern ="yyyy/MM/dd/ HH:mm:ss" /></div>
		</div>
		</c:forEach>

	</div>


	<div class = "copylight"> Copyright(c) Yoshihiro Suzuki</div>
</body>
</html>