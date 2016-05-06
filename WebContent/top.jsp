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
<link rel = "stylesheet" type = "text/css" href = "stylesheet/style.css">

</head>
<body>
	<div class = "main-contents">
		<div class = "header">

			<a href = "top">ホーム</a>
			<c:if test = "${loginUser.departmentId == 1 }">
				<a href = "usermanager">ユーザの管理</a>
			</c:if>
			<a href = "logout">ログアウト</a>
		</div><p>
		<div class = "profile">
			<div class = "name">ようこそ<b><c:out value = "${loginUser.name }" /></b>さん</div>


		</div>
		<div class = "newPost"><p>
			<a href = "newPost">新規投稿</a>
		</div>
		<p>
			<c:if test="${not empty errorMessages }">
			<div class = "errorMessages">
				<ul>
					<c:forEach items = "${errorMessages }" var = "messages">
						<li><c:out value = "${messages }" /><br />
					</c:forEach>
				</ul>
			</div>
			<c:remove var = "errorMessages" scope = "session"/>
		</c:if>
		<p>

		<div class = "narrowing">
		<form action = "narrowing" method = "post">
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
			<script>
			$(function() {
				$("#dateStart").datepicker({maxDate: 0});
				$("#dateEnd").datepicker({maxDate: 0});
			});
			</script>
			<p>
			日付<br />
			開始日時：<input type = "text" name = "dateStart" id = "dateStart" value = "${dates[0] }">
			終了日時：<input type = "text" name = "dateEnd"   id = "dateEnd"   value = "${dates[1] }">
			<input type = "submit" value = "指定した条件で検索">
		</form>
		<form action = "top" method = "get">
			<input type = "submit" value = "条件をクリア">
		</form>

		</div>

		<div class = "messages">
			<c:forEach items = "${messages }" var = "message">
				<table class = "message">

						<c:set var = "id" scope = "request" value = "${message.id }" />
						<tr><td>投稿者</td><td><c:out value = "${message.name }"></c:out></td></tr>
						<c:set var = "name" scope = "request" value = "${message.name }" />
						<tr><td>件名</td><td><c:out value = "${message.title }"></c:out></td></tr>
						<tr><td>カテゴリ</td><td><c:out value = "${message.category }"></c:out></td></tr>

					<tr><td>本文</td><td>
						<%
							String lineSeparator = System.getProperty("line.separator");
						%>
						<c:forTokens var = "splitedMessage" items = "${message.text }" delims = "<%= lineSeparator %>">
							<c:out value = "${splitedMessage }"></c:out><br>
						</c:forTokens>

					</td></tr>
					<tr><td>投稿日時</td><td><fmt:formatDate value="${message.insertDate }" pattern ="yyyy/MM/dd HH:mm:ss" /></td></tr>

					<c:if test = "${(message.userId == loginUser.id) || (loginUser.departmentId == 2) || (message.branchId == loginUser.branchId && loginUser.departmentId == 3) }">
						<form action = "delete" method = "post">
							<tr><td></td><td><input type = "submit" value = "投稿を削除する" onClick = "return confirm('この投稿を削除します。よろしいですか？')" /></td></tr>
							<input type = "hidden" name = "id" value = "${message.id }" />
							<input type = "hidden" name = "permission" value = "1" >
						</form>
					</c:if>

				</table>

				<div class = comments>
					<br />コメント一覧
					<c:forEach items = "${comments }" var = "comment">
						<table class = "comment">
							<c:if test = "${message.id == comment.postId }">

									<tr><td>投稿者</td><td><c:out value = "${comment.name }"></c:out></td></tr>
									<tr><td>本文</td><td>
										<c:forTokens var = "splitedMessage" items = "${comment.text }" delims = "<%= lineSeparator %>">
											<c:out value = "${splitedMessage }"></c:out><br>
										</c:forTokens></td></tr>


									<tr><td>投稿日時</td><td><fmt:formatDate value= "${comment.insertDate }" pattern ="yyyy/MM/dd HH:mm:ss" /></td></tr>

									<c:if test = "${(comment.userId == loginUser.id) || (loginUser.departmentId == 2) || (comment.branchId == loginUser.branchId && loginUser.departmentId == 3) }">
										<form action = "delete" method = "post">
											<tr><td></td><td><input type = "submit" value = "コメントを削除する" onClick = "return confirm('このコメントを削除します。よろしいですか？')" /></td></tr>
											<input type = "hidden" name = "id" value = "${comment.id }" />
											<input type = "hidden" name = "permission" value = "2" >
										</form>
									</c:if>

							</c:if>
						</table>
					</c:forEach>
					<div class = "postComeent">
						<form action = "postComment" method = "post">
							<br />コメントの投稿<br />
							<textarea name = "comment" cols = "80" rows = "5" class = "post-box" ><c:out value = "${inputValues.text }"></c:out></textarea>
							<br />
							<input type = "submit" value = "投稿する">(500文字まで)
							<input type = "hidden" name = "postId" value = "${message.id }" />
							<input type = "hidden" name = "userId" value = "${loginUser.id }" />

						</form>
					</div>
				</div>
				<hr>
			</c:forEach>

		</div>
	</div>


	<div class = "copylight"> Copyright(c) Yoshihiro Suzuki</div>

</body>
</html>