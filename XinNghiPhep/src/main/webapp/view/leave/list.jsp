<!-- /view/leave/list.jsp -->
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
	<h2>${empty pageTitle ? 'Leave Requests List' : pageTitle}</h2>
	<c:if test="${param.msg == 'approved'}">
		<p style="color: green;">Leave request approved successfully.</p>
	</c:if>
	<c:if test="${param.msg == 'notfound'}">
		<p style="color: red;">Unable to approve: request not found or already processed.</p>
	</c:if>
	<p>
		<a href="${pageContext.request.contextPath}/leave/list">All</a> |
		<a href="${pageContext.request.contextPath}/leave/pending">Pending</a>
	</p>

	<table border="1">
		<tr>
			<th>ID</th>
			<th>User</th>
			<th>Start</th>
			<th>End</th>
			<th>Reason</th>
			<th>Status</th>
			<th>Action</th>
		</tr>

		<c:forEach var="lr" items="${leaveList}">
			<tr>
				<td>${lr.id}</td>
				<td>${lr.user.fullName}</td>
				<td>${lr.startDate}</td>
				<td>${lr.endDate}</td>
				<td>${lr.reason}</td>
				<td>${lr.status}</td>
				<td>
					<a href="${pageContext.request.contextPath}/leave/detail?id=${lr.id}">View</a>
					<c:if test="${(sessionScope.account.role == 'MANAGER' || sessionScope.account.role == 'SUPER_ADMIN') && lr.status == 'PENDING'}">
						|
						<form action="${pageContext.request.contextPath}/leave/approve" method="post" style="display:inline;">
							<input type="hidden" name="id" value="${lr.id}" />
							<button type="submit">Approve</button>
						</form>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>