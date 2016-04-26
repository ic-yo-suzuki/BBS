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
</head>
<body>
	<div class = "main-contents">
		<c:if test = "${not empty errorMessages }">
			<div class = "errorMessages">
				<ul>
					<c:forEach items = "${errorMessages }" var = "message">
						<li><c:out value = "${message }" /> <br />
					</c:forEach>
				</ul>
			</div>
			<c:remove var = "errorMessages" scope = "session"/>
		</c:if>
	<div class = "form-area">
		<p>
		<form action = "newPost" method = "post">
			カテゴリー(必須)
			<select name = "category">
				<c:forEach items = "${categories }" var = "category">
					<c:if test ="${category == inputValues.category }">
						<option value = "${category }" selected ><c:out value = "${category }"></c:out></option>
					</c:if>
					<c:if test = "${category != inputValues.category }">
						<option value = "${category }" ><c:out value = "${category }"></c:out></option>
					</c:if>
				</c:forEach>

			</select>
			カテゴリーの新規作成
			<input name = "newCategory" id = "newCategory" value = "${inputValues.category }">
			<p>
			投稿者：<c:out value = "${loginUser.name }"></c:out>さん(自動で追加されます)<br />
			タイトル(必須)
			<input name = "title" id = "title" value = "${inputValues.title }">
			<br />本文<br />
			<textarea name = "message" cols = "80" rows = "10" class = "post-box" ><c:out value = "${inputValues.text }"></c:out></textarea>
			<br />
			<input type = "submit" value = "投稿する">(1000文字まで)
		</form>
	</div>
	</div>
</body>
</html>