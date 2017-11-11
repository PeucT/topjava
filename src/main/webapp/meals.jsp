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
<form method="post" >
    <c:if test="${edit == null}" >
        <input type="hidden" name="action" value="add"/>
    </c:if>
    <c:if test="${edit != null}" >
        <input type="hidden" name="action" value="editSave"/>
    </c:if>
    <input type="datetime-local" name="date" value="${edit.dateTime}">
    <input type="text" name="description" value="${edit.description}">
    <input type="number" id="calories" name="calories" value="${edit.calories}">
    <button type="submit">Save</button>
</form>

<table>
    <tr>
        <td>Дата</td>
        <td>Описание</td>
        <td>Калории</td>
    </tr>

    <c:forEach var="elem" items="${meals}">
        <c:choose>
            <c:when test="${elem.exceed == true}">
                <tr class="red">
            </c:when>
            <c:otherwise>
                <tr class="green">
            </c:otherwise>
        </c:choose>
            <td>
                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${elem.dateTime}" var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" pattern="dd.MM.yyyy HH:mm" />
            </td>
            <td>${elem.description}</td>
            <td>${elem.calories}</td>
        <td>
            <form method="post">
                <input type="hidden" name="action" value="edit"/>
                <input type="hidden" name="id" value="${elem.id}"/>
                <input type="submit" value="EDIT"/>
            </form>
        </td>
        <td>
            <form method="post">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="${elem.id}"/>
                <input type="submit" value="DELETE"/>
            </form>
        </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
