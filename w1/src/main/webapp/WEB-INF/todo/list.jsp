<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lemi1
  Date: 2024-04-08
  Time: 오후 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <ul>
        <%--  el태그 안에 있는 것이 TodoListController에서 req.setAttribute()해서
        넘겨준 것과 이름이 동일해야 함  --%>
        <c:forEach var="dto" items="${list}">
            <li>${dto}</li>
        </c:forEach>
        ${list}
    </ul>
</body>
</html>
