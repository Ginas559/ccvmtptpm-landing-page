<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/signup" method="post">

		Username: <input type="text" name="username" /> Email: <input
			type="email" name="email" /> Password: <input type="password"
			name="password" />

		<button type="submit">Register</button>

	</form>

	<c:if test="${errorMsg != null}">
		<p style="color: red">${errorMsg}</p>
	</c:if>
</body>
</html>