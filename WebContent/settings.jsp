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
<!-- <link rel = "stylesheet" type = "text/css" href = "stylesheet/style.css"> -->
<title>ユーザ管理</title>
</head>
<body>
	<div class = "header">
		<a href = "top">ホーム</a>
		<c:if test = "${loginUser.departmentId == 1 }">
			<a href = "signup">ユーザ新規登録</a>
		</c:if>

	</div><p>
	<hr>
	ユーザ一覧
	<div class = "userlist">
		<c:forEach items = "${userList }" var = "user">
			<span>
				<c:out value = "${user.name }" />&nbsp;&nbsp;
				<c:out value = "${user.loginId }" />&nbsp;&nbsp;
				<c:out value = "${user.branchName }" />
				<c:out value = "${user.departmentName }" />&nbsp;&nbsp;
				<form action = "edit" method = "get" style = "display:inline">
					<button type = 'submit' name = 'editUserId' value = "${user.id }" >このユーザの情報を編集する</button>&nbsp;&nbsp;
				</form>

				<form action = "changeStatus" method = "post" style = "display:inline">
				<c:if test = "${user.status == true }">
						<button type = 'submit' name = 'changeStatusUserId'' value = "${user.id }" onClick = "return confirm('このユーザを停止します。よろしいですか？')">このユーザを停止する</button>&nbsp;&nbsp;
				</c:if>
				<c:if test = "${user.status == false }">
					<button type = 'submit' name = 'changeStatusUserId' value = "${user.id }" onClick = "return confirm('このユーザを復活します。よろしいですか？')">このユーザを復活させる</button>
				</c:if>
				</form>
				<form action = "deleteUser" method = "post" style = "display:inline">
					<button type = 'submit' name = 'deleteUserId' value = "${user.id }" onClick = "return confirm('このユーザを削除します。よろしいですか？')">このユーザを削除する</button>&nbsp;&nbsp;
				</form>

				<br />
			</span>
		</c:forEach>
	</div>
</body>
</html>