<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
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
    <h1>Регистрация</h1>
    <c:if test="${requestScope.get('exp') != null}">
        <p style="color: red">Стоп, стоп, стоп. Вы что-то, наверно, не ввели :( </p>
    </c:if>
    <form action="${prc}/initialization/create-users" method="post">
        <p>Введите Ваше ФИО (Иванов И.И.):</p>
        <input type="text" name="fio">
        <p>Введите телефон:</p>
        <input type="text" name="telephone">
        <p>Придумайте логин (хорошо подумайте):</p>
        <input type="text" name="login">
        <p>Придумайте пароль (чтобы было что воровать):</p>
        <input type="text" name="password"><br><br>
        <input type="submit" value="Зарегистрироваться!">
    </form>
</div>
<div>
    <a href="${prc}/views/initialization/authorization.jsp">Авторизация</a>
</div>
</body>
</html>
