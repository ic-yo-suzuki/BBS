
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
<link rel="stylesheet" type="text/css" href="stylesheet/style.css">
</head>

<body>
	<div class="header">
		<a href="./usermanager">戻る</a>
	</div>
	<div class="main-contents">
		<c:if test="${not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages }" var="message">
						<li><c:out value="${message }" /> <br />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>

		<form action="signup" method="post">
			<br />
			<table class="inputvalue">
				<th colspan="2">項目</th>

				<th>備考</th>
				<tr>
					<td><label for="name">名前</label></td>
					<td><input name="name" id="name" value="${inputValues.name }" /></td>
					<td>10文字以下</td>
				</tr>
				<tr>
					<td><label for="loginId">ログインID</label></td>
					<td><input name="loginId" id="loginId"
						value="${inputValues.loginId }" /></td>
					<td>半角英数字(A～Z、a～z、0～9)で6～20文字</td>
				</tr>
				<tr>
					<td><label for="password">パスワード</label></td>
					<td><input name="password" type="password" /></td>
					<td>記号含む半角文字で6～255文字</td>
				</tr>
				<tr>
					<td><label for="password_verify">パスワード(確認用)</label></td>
					<td><input name="password_verify" type="password" /></td>
					<td></td>
				</tr>
				<tr>
					<td><label for="branch">所属支店</label></td>

					<td><select name="branch">
							<c:forEach items="${branches }" var="branch">
								<c:if test="${branch == selectedBranch }">
									<option value="${branch }" selected><c:out
											value="${branch }"></c:out></option>
								</c:if>
								<c:if test="${branch != selectedBranch }">
									<option value="${branch }"><c:out value="${branch }"></c:out></option>
								</c:if>
							</c:forEach>
					</select></td>
					<td></td>
				</tr>
				<tr>

					<td><label for="department">所属部署・役職</label></td>
					<td><select name="department">
							<c:forEach items="${departments }" var="department">
								<c:if test="${department == selectedDepartment }">
									<option value="${department }" selected><c:out
											value="${department }"></c:out></option>
								</c:if>
								<c:if test="${department != selectedDepartment }">
									<option value="${department }"><c:out
											value="${department }"></c:out></option>
								</c:if>
							</c:forEach>

					</select></td>
					<td></td>
				</tr>
			</table>
			<input type="submit" value="登録" /><br />

		</form>
	</div>

</body>
</html>