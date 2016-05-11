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
<title>NGワード管理</title>
<link rel = "stylesheet" type = "text/css" href = "stylesheet/style.css">

<script type="text/javascript">
<!--
function openwin() {
	window.open("./editngword", "", "width=500,height=400");
}
// -->
</script>
</head>
<body>
	<div class ="main-contents">
		<div class = "header">
			<div class = "menu">
			<a href = "top">ホーム</a>


		</div></div><p>

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
		<hr>


		<div class = "ngwordlist">
		NGワード一覧
		<table class = "ngwordlist">
			<tbody>
				<th>NGワード</th>

				<th>操作</th>


					<c:forEach items = "${ngWordList }" var = "ngWord">
						<tr>
							<td><c:out value = "${ngWord.word }" /></td>


							<form action = "deletengword" method = "post" style = "display:inline">
								<td><button type = 'submit' name = 'id' value = "${ngWord.id }" onClick = "return confirm('このNGワードします。よろしいですか？')">削除</button></td>
							</form>

						</tr>
					</c:forEach>
					<tr>
						<form action = "addngword" method = "post">
							<td><input name = "word" id = "word"   /></td>
							<td colspan = "2"><button type = 'submit' name = 'word' >登録</button></td>
						</form>
					</tr>
			</tbody>
		</table>
		</div>
	</div>
</body>
</html>