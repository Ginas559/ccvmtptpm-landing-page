<-- view/mânger/employees.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Employee List</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Action</th>
    </tr>

    <c:forEach var="u" items="${list}">
        <tr>
            <td>${u.id}</td>
            <td>${u.fullName}</td>
            <td>${u.email}</td>
            <td>
                <a href="${pageContext.request.contextPath}/my-profile?id=${u.id}">
                    View
                </a>
            </td>
        </tr>
    </c:forEach>
</table>