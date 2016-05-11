<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored = "false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
<link rel = "stylesheet" type = "text/css" href = "stylesheet/style.css">
</head>
<body>
	<div class = "header">
			<a href="./top">戻る</a>
	</div>
	<div class = "main-contents">
		<c:if test = "${not empty errorMessages }">
			<div class = "errorMessages">
				<ul>
					<c:forEach items = "${errorMessages }" var = "message">
						<li><c:out value = "${message }" /></li> <br />
					</c:forEach>
				</ul>
			</div>
			<c:remove var = "errorMessages" scope = "session"/>
		</c:if>
	<div class = "form-area">
		<p>
		<table class = "newpost">
		<form action = "newpost" method = "post">
			<tr><td>カテゴリー(必須)<br>カテゴリーの新規作成</td><td>
			<select name = "category">
				<c:forEach items = "${categories }" var = "category">
					<c:if test ="${category == inputValues.category }">
						<option value = "${category }" selected ><c:out value = "${category }"></c:out></option>
					</c:if>
					<c:if test = "${category != inputValues.category }">
						<option value = "${category }" ><c:out value = "${category }"></c:out></option>
					</c:if>
				</c:forEach>

			</select><br>

			<input name = "newCategory" id = "newCategory" value = "${inputValues.category }">(新たに追加するカテゴリ名を10文字以内で入力してください)
			</td></tr>
			<tr><td>投稿者</td><td><c:out value = "${loginUser.name }"></c:out>さん(自動で追加されます)</td></tr>
			<tr><td>タイトル(必須)(50文字まで)</td><td>
			<input name = "title" id = "title" value = "${inputValues.title }"></td></tr>
			<tr><td>本文(1000文字まで)</td><td>
			<textarea name = "message" cols = "80" rows = "10" class = "post-box" ><c:out value = "${inputValues.text }"></c:out></textarea>
			</td></tr>

			<tr><td></td><td><input type = "submit" value = "投稿する"></td></tr>

		</form>
					</table>

	</div>
	</div>
	</body>
</html>