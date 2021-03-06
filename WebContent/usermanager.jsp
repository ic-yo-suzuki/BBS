<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>
<%@ page import="java.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel = "stylesheet" type = "text/css" href = "stylesheet/style.css"> -->
<title>ユーザ管理</title>
<link rel="stylesheet" type="text/css" href="stylesheet/style.css">
</head>
<body>
	<div class="main-contents">
		<div class="header">
			<div class="menu">
				<a href="top">ホーム</a>
				<c:if test="${loginUser.departmentId == 1 }">
					<a href="signup">ユーザ新規登録</a>
				</c:if>

			</div>
		</div>
		<p>

			<c:if test="${not empty errorMessages }">
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages }" var="messages">
							<li><c:out value="${messages }" /></li>
							<br />
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session" />
			</c:if>
		<hr>
		ユーザ一覧
		<div class="userlist">
			<table class="userlist">
				<tbody>
					<th>ユーザ名</th>
					<th>ログインID</th>
					<th colspan="2">所属</th>
					<th>最終ログイン</th>
					<th colspan="3">ユーザの操作</th>


					<c:forEach items="${userList }" var="user">
						<tr>
							<td><c:out value="${user.name }" /></td>
							<td><c:out value="${user.loginId }" /></td>
							<td><c:out value="${user.branchName }" /></td>
							<td><c:out value="${user.departmentName }" /></td>
							<td><abbr
								title="<fmt:formatDate value= "${user.lastLoginDate }" pattern ="yyyy/MM/dd HH:mm:ss" />"><c:out
										value="${user.elapsedTimeText }" /></abbr></td>
							<form action="edit" method="get" style="display: inline">
								<td><button type='submit' name='id' value="${user.id }">編集</button></td>
							</form>

							<form action="changeStatus" method="post" style="display: inline">
								<c:if test="${user.status == true }">
									<td><button type='submit' name='id' value="${user.id }"
											onClick="return confirm('このユーザを停止します。よろしいですか？')">停止</button></td>
								</c:if>
								<c:if test="${user.status == false }">
									<td><button type='submit' name='id' value="${user.id }"
											onClick="return confirm('このユーザを復活します。よろしいですか？')">復活</button></td>
								</c:if>
							</form>
							<form action="deleteUser" method="post" style="display: inline">
								<td><button type='submit' name='id' value="${user.id }"
										onClick="return confirm('このユーザを削除します。よろしいですか？')">削除</button></td>
							</form>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</body>
</html>