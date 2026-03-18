<-- view/employee/dashboard.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Employee Dashboard</h2>

	<hr>
	<li><a href="${pageContext.request.contextPath}/my-profile">
			My Profile </a></li>
	</hr>

	<ul>
		<li><a href="${pageContext.request.contextPath}/employee/request">
				Request Leave </a></li>

		<li><a href="${pageContext.request.contextPath}/employee/status">
				Check Leave Status </a></li>

		<li><a href="${pageContext.request.contextPath}/employee/balance">
				Check Leave Balance </a></li>
	</ul>
</body>
</html>