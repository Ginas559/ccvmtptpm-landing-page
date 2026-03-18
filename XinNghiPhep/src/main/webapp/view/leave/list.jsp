<-- /view/leave/list.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Leave Requests List</h2>

	<table border="1">
		<tr>
			<th>ID</th>
			<th>User</th>
			<th>Start</th>
			<th>End</th>
			<th>Reason</th>
			<th>Status</th>
		</tr>

		<c:forEach var="lr" items="${leaveList}">
			<tr>
				<td>${lr.id}</td>
				<td>${lr.user.fullName}</td>
				<td>${lr.startDate}</td>
				<td>${lr.endDate}</td>
				<td>${lr.reason}</td>
				<td>${lr.status}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>