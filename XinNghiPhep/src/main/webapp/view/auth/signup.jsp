<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard-theme.css">
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>
<style>
    .auth-card {
        max-width: 450px;
        margin: 50px auto;
        padding: 30px;
        background: white;
        border-radius: 15px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        font-family: Arial, sans-serif;
    }

    .form-group {
        display: flex;
        flex-direction: column;
        margin-bottom: 15px;
    }

    .form-group label {
        margin-bottom: 5px;
        font-weight: 500;
    }

    .form-group input, .form-group select {
        padding: 10px;
        border: 1px solid #e0e0e0;
        border-radius: 8px;
        font-size: 14px;
    }

    button {
        background-color: #7c4dff;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        font-size: 16px;
        margin-top: 10px;
    }

    button:hover {
        background-color: #6200ea;
    }

    .error-msg {
        color: red;
        margin-top: 10px;
    }
</style>
</head>
<body class="auth-page">
<main class="auth-card">
    <form action="${pageContext.request.contextPath}/signup" method="post">
        
        <div class="form-group">
            <label>Username:</label>
            <input type="text" name="username" required />
        </div>

        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" required />
        </div>

        <div class="form-group">
            <label>Password:</label>
            <input type="password" name="password" required />
        </div>

        <div class="form-group">
            <label>Role:</label>
            <select name="role">
                <option value="EMPLOYEE">Employee</option>
                <option value="MANAGER">Manager</option>
                <option value="SUPER_ADMIN">Super Admin</option>
            </select>
        </div>

        <div class="form-group">
            <label>Secret Code (chỉ cần cho Manager / Admin):</label>
            <input type="text" name="secretCode" />
        </div>

        <button type="submit">Register</button>

    </form>

    <c:if test="${errorMsg != null}">
        <p class="error-msg">${errorMsg}</p>
    </c:if>
</main>
</body>
</html>
