<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Leave Request Detail</title>
</head>
<body>
	<h2>Leave Request Detail</h2>

	<p>
		<a href="${pageContext.request.contextPath}/leave/list">Back to all</a> |
		<a href="${pageContext.request.contextPath}/leave/pending">Back to pending</a>
	</p>

	<c:if test="${not empty leaveRequest}">
		<table border="1">
			<tr>
				<th>ID</th>
				<td>${leaveRequest.id}</td>
			</tr>
			<tr>
				<th>User</th>
				<td>${leaveRequest.user.fullName}</td>
			</tr>
			<tr>
				<th>Start date</th>
				<td>${leaveRequest.startDate}</td>
			</tr>
			<tr>
				<th>End date</th>
				<td>${leaveRequest.endDate}</td>
			</tr>
			<tr>
				<th>Status</th>
				<td>${leaveRequest.status}</td>
			</tr>
			<tr>
				<th>Reason</th>
				<td>${leaveRequest.reason}</td>
			</tr>
		</table>

		<c:if test="${(sessionScope.account.role == 'MANAGER' || sessionScope.account.role == 'SUPER_ADMIN') && leaveRequest.status == 'PENDING'}">
			<form action="${pageContext.request.contextPath}/leave/approve" method="post" style="margin-top:10px;">
				<input type="hidden" name="id" value="${leaveRequest.id}" />
				<button type="submit">Approve</button>
			</form>
		</c:if>
	</c:if>
</body>
</html>

