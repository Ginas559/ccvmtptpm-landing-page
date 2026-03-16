<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
	    <c:when test="${sessionScope.account != null}">
	        <!-- Hiển thị tên người dùng và nút Đăng xuất khi đã đăng nhập -->
	        Chào bạn, ${sessionScope.account.fullName} | 
	        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a> [6, 7]
	    </c:when>
	    <c:otherwise>
	        <!-- Hiển thị nút Đăng nhập nếu chưa có session -->
	        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
	    </c:otherwise>
	</c:choose>
</body>
</html>