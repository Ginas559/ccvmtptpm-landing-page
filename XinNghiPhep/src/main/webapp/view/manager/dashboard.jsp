<-- view/mânger/dashboard.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Manager Dashboard</h2>

	<hr>
        <li><a href="${pageContext.request.contextPath}/my-profile">
                My Profile </a></li>
	</hr>


	<hr>
    	<li>
            <a href="${pageContext.request.contextPath}/manager/zemployeez">
                View Employees
            </a>
        </li>
    </hr>


	<ul>
		<li><a href="${pageContext.request.contextPath}/manager/requests">
				Check Leave Requests </a></li>

		<li><a href="${pageContext.request.contextPath}/manager/approve">
				Approval Leave </a></li>

		<li><a href="${pageContext.request.contextPath}/manager/reports">
				Create Leave Reports </a></li>
	</ul>
</body>
</html>