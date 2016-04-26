<script src = "js/jquery-1.12.3.js" ></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored = "false" %>
<%@ page import = "java.lang.*" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板　ホーム画面</title>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/ui-lightness/jquery-ui.css" >
</head>
<body>
		<div class = "header">
			<a href = "top">ホーム</a>
			<a href = "settings">設定</a>
			<a href = "logout">ログアウト</a>
		</div><p>
		<div class = "profile">
			<div class = "name">ようこそ<b><c:out value = "${loginUser.name }"></c:out></b>さん</div>

		</div>
		<div class = "newPost"><p>
			<a href = "newPost">新規投稿</a>
		</div>
		<p><hr>
		<form action = "narrowingCategory" method = "post">
		投稿の絞込み検索<br>
		カテゴリー<br>
		<select name = "category">
			<c:forEach items = "${categories }" var = "category">
				<c:if test ="${category == selectedCategory }">
					<option value = "${category }" selected ><c:out value = "${category }"></c:out></option>
				</c:if>
				<c:if test = "${category != selectedCategory }">
					<option value = "${category }" ><c:out value = "${category }"></c:out></option>
				</c:if>
			</c:forEach>
		</select>
		<input type = "submit" value = "カテゴリで絞込み">
		<br>
		日付<br>
		<script>
			$(function() {
				$("#datepickStart").datepicker();
				$("#datepickEnd").datepicker();
			});
		</script>
		開始日時：<input type="text" id="datepickStart">
		終了日時：<input type = "text" id = "datepickEnd">

		</form>

		<hr>
	<div class = "messages">
		<c:forEach items = "${messages }" var = "message">
		<div class = "message">
			<div class = "account-name">
				<span class = "name">投稿者：<c:out value = "${message.name }"></c:out></span><br />
				<span class = "title">件名：<c:out value = "${message.title }"></c:out></span><br />
				<span class = "category">カテゴリ：<c:out value = "${message.category }"></c:out></span>
			</div>
			<%
				String lineSeparator = System.getProperty("line.separator");
			%>
			<div class = "text">本文：<br />
			<c:forTokens var = "splitedMessage" items = "${message.text }" delims = "<%= lineSeparator %>">
				<c:out value = "${splitedMessage }"></c:out><br>
			</c:forTokens>
			</div><br />
			<div class = "date">投稿日時：<fmt:formatDate value="${message.insertDate }" pattern ="yyyy/MM/dd HH:mm:ss" /></div>

		</div>
		<div class = "postComeent">
			<form action = "postComment" method = "post">
				<br />コメント<br />
				<textarea name = "message" cols = "80" rows = "5" class = "post-box" ><c:out value = "${inputValues.text }"></c:out></textarea>
				<br />
				<input type = "submit" value = "投稿する">(500文字まで)
			</form>
		</div>
		<hr>
		</c:forEach>

	</div>

	<div class = "copylight"> Copyright(c) Yoshihiro Suzuki</div>
</body>
</html>