<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: PAKOT
  Date: 06.11.2017
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .red{
            background-color: red;
        }

        .green{
            background-color: green;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <td>Дата</td>
        <td>Описание</td>
        <td>Калории</td>
    </tr>

    <c:forEach var="elem" items="${meals}">
        <c:if test="${elem.exceed == true}">
            <tr class="red">
        </c:if>
        <c:if test="${elem.exceed == false}">
            <tr class="green">
        </c:if>
            <td>
                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${elem.dateTime}" var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" pattern="dd.MM.yyyy HH:mm" />
            </td>
            <td>${elem.description}</td>
            <td>${elem.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
