<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<jsp:useBean id="user" scope="session" class="ru.vstu_bet.models.beans.db.User"/>
<c:if test="${user.login == null}">
    <%session.removeAttribute("user");%>
</c:if>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<div>
    <h1>Вход</h1>
    <form action="${prc}/authorization" method="post">
        <p>Введите ваш логин:</p>
        <input type="text" name="login">
        <p>Введите ваш пароль:</p>
        <input type="password" name="password"><br><br>
        <c:if test="${requestScope.get('error') != null}">
            <p>При вводе данных допущена ошибка!</p>
        </c:if>
        <input type="submit" name="Войти" value="Войти" style="cursor: pointer;">
    </form>
</div>
<div>
    <a href="${prc}/views/initialization/registration.jsp">Да что это такое, ставки ети? (регистрация)</a>
</div>
</body>
</html>
