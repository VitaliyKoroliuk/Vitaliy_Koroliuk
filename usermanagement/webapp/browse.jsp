<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User management</title>
</head>
<body>
<table id="userTable" border="1">
    <tr>
        <th></th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Date of Birth</th>
    </tr>
    <c:forEach var="user" items="${sessionScope.users}">
    <tr>
        <td><input type="radio" name="id" id="id" value="${user.id}"></td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.dateOfBirth}</td>

    </tr>
    </c:forEach>
</table>
</body>
</html>