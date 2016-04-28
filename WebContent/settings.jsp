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
<title>ユーザ管理</title>
</head>
<body>
	<div class = "header">
		<a href = "top">ホーム</a>
		<c:if test = "${loginUser.departmentId == 1 }">
			<a href = "signup">ユーザ新規登録</a>
		</c:if>
		<a href = "logout">ログアウト</a>
	</div><p>
</body>
</html>