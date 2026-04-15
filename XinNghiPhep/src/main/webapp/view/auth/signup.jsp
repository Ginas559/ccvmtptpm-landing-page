<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard-theme.css">
<meta charset="UTF-8">
<title>Sign Up</title>
</head>
<body class="auth-page">
<main class="auth-card">
	<h2 class="auth-title">Create Account</h2>
	<form class="auth-form" action="${pageContext.request.contextPath}/signup" method="post">
		<div class="form-field">
			<label for="username">Username</label>
			<input id="username" type="text" name="username" required />
		</div>
		<div class="form-field">
			<label for="email">Email</label>
			<input id="email" type="email" name="email" required />
		</div>
		<div class="form-field">
			<label for="password">Password</label>
			<input id="password" type="password" name="password" required />
		</div>
		<div class="form-field">
			<label for="role">Role</label>
			<select id="role" name="role">
				<option value="EMPLOYEE">Employee</option>
				<option value="MANAGER">Manager</option>
				<option value="SUPER_ADMIN">Super Admin</option>
			</select>
		</div>
		<div class="form-field">
			<label for="secretCode">Secret Code</label>
			<input id="secretCode" type="text" name="secretCode" />
			<p class="form-help">Chi can nhap cho Manager/Super Admin.</p>
		</div>
		<button type="submit">Register</button>
	</form>

	<c:if test="${errorMsg != null}">
		<p class="alert-error">${errorMsg}</p>
	</c:if>

	<p class="auth-links">
		<a href="${pageContext.request.contextPath}/login">Back to login</a>
	</p>
</main>
</body>
</html>
