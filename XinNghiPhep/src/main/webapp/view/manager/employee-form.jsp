<%-- view/manager/employee-form.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard-theme.css">
    <meta charset="UTF-8">
    <title>Employee Form</title>
</head>
<body class="app-page">
<div class="session-actions">
    <a class="chip-link logout-chip" href="${pageContext.request.contextPath}/logout">Dang xuat</a>
</div>
<main class="page-card">

<h2>Employee Form</h2>

<form method="post"
      action="${pageContext.request.contextPath}${user == null ? '/manager/employees/insert' : '/manager/employees/update'}">

    <c:if test="${user != null}">
        <input type="hidden" name="id" value="${user.id}" />
    </c:if>

    Username:
    <input type="text" name="username" value="${user.username}"
           ${user != null ? 'readonly' : ''} />
    <br/><br/>

    <c:if test="${user == null}">
        Password:
        <input type="password" name="password" />
        <br/><br/>
    </c:if>

    Full Name:
    <input type="text" name="fullName" value="${user.fullName}" />
    <br/><br/>

    Email:
    <input type="email" name="email" value="${user.email}" />
    <br/><br/>

    <button type="submit">Save</button>
</form>

</main>
</body>
</html>
