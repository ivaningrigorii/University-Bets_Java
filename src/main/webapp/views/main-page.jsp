<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ставки на спорт</title>
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
    <h1 style="color: red">Ставки на спорт</h1><br>
    <h4><i>Ставки на разные виды спорта,
        <br>вариант 5, курсовой, Григорий Михайлович Иванин</i></h4>
    <form action="${prc}/views/initialization/authorization.jsp">
        <button class="b_cursor" style="cursor: pointer;">Начать проигрывать все деньги</button>
    </form>
</div>
</body>
</html>