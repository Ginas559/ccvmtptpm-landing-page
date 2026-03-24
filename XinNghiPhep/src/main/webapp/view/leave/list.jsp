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
	<c:if test="${param.msg == 'rejected'}">
		<p style="color: green;">Leave request rejected successfully.</p>
	</c:if>
	<c:if test="${param.msg == 'notfound'}">
		<p style="color: red;">Unable to process: request not found or already processed.</p>
	</c:if>
	<p>
		<a href="${pageContext.request.contextPath}/leave/list?status=ALL">All</a> |
		<a href="${pageContext.request.contextPath}/leave/list?status=PENDING">Pending</a>
	</p>

	<form method="get" action="${pageContext.request.contextPath}/leave/list" style="margin-bottom: 10px;">
		<label for="status">Status:</label>
		<select id="status" name="status">
			<option value="ALL" ${selectedStatus == 'ALL' ? 'selected' : ''}>ALL</option>
			<option value="PENDING" ${selectedStatus == 'PENDING' ? 'selected' : ''}>PENDING</option>
			<option value="APPROVED" ${selectedStatus == 'APPROVED' ? 'selected' : ''}>APPROVED</option>
			<option value="REJECTED" ${selectedStatus == 'REJECTED' ? 'selected' : ''}>REJECTED</option>
		</select>
		<button type="submit">Filter</button>
	</form>

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
						|
						<form action="${pageContext.request.contextPath}/leave/reject" method="post" style="display:inline;">
							<input type="hidden" name="id" value="${lr.id}" />
							<button type="submit">Reject</button>
						</form>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty leaveList}">
			<tr>
				<td colspan="7">No leave requests found.</td>
			</tr>
		</c:if>
	</table>
</body>
</html>