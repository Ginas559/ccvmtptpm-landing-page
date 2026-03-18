<-- view/admin/users.jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<h2>User List</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Action</th>
    </tr>

    <c:forEach var="u" items="${list}">
        <tr>
            <td>${u.id}</td>
            <td>${u.fullName}</td>
            <td>${u.email}</td>
            <td>${u.role}</td>
            <td>
                <a href="${pageContext.request.contextPath}/my-profile?id=${u.id}">
                    View
                </a>
            </td>
        </tr>
    </c:forEach>
</table>