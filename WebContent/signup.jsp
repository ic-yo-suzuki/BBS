
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored = "false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
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

		<form action = "signup" method = "post"> <br />
			<label for = "name">名前</label>
			<input name = "name" id = "name" value = "${inputValues.name }" />(10文字以下)<br />

			<label for = "loginId">ログインID</label>
			<input name = "loginId" id = "loginId"  value = "${inputValues.loginId }" />(半角英数字(A～Z、a～z、0～9)で6～20文字<br />

			<label for = "password">パスワード</label>
			<input name = "password" type = "password" />(記号含む半角文字で6～255文字)<br />

			<label for = "password_verify">パスワード(確認用)</label>
			<input name = "password_verify" type = "password" /><br />


			<label for = "branch">所属支店</label>

				<select name = "branch">
					<c:forEach items = "${branches }" var = "branch">
						<c:if test ="${branch == selectedBranch }">
							<option value = "${branch }" selected ><c:out value = "${branch }"></c:out></option>
						</c:if>
						<c:if test = "${branch != selectedBranch }">
							<option value = "${branch }" ><c:out value = "${branch }"></c:out></option>
						</c:if>
					</c:forEach>
				</select>
			<br />

			<label for = "department">所属部署・役職</label>
				<select name = "department">
				<c:forEach items = "${departments }" var = "department">
					<c:if test ="${department == selectedDepartment }">
							<option value = "${department }" selected ><c:out value = "${department }"></c:out></option>
					</c:if>
					<c:if test ="${department != selectedDepartment }">
						<option value = "${department }"><c:out value = "${department }"></c:out></option>
					</c:if>
				</c:forEach>

				</select>
			<br />

			<input type = "submit" value = "登録" /><br />
			<a href="javascript:window.history.back();">戻る</a>
		</form>
		<div class = "copyright"> Copyright (c) Yoshihiro Suzuki</div>
	</div>
</body>
</html>