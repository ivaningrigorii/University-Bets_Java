<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Добавление игроков</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
    <h1>Поиск новых игроков</h1>
    <div>
        <h4>Вы можете найти игрока в свои команды<br>
            случайным образом.</h4>
        <p>Ваш баланс: <b style="color: blue"><jsp:getProperty name="user" property="score"/></b></p>
        <form action="${prc}/players/add-players" method="post">
            <p>Дешёвый поиск (200 монет), слабые игроки</p>
            <input type="hidden" name="price" value="200">
            <input type="hidden" value="0" name="finder">
            <button type="submit" style="cursor: pointer">Найти игрока</button>
        </form>
        <form action="${prc}/players/add-players" method="post">
            <p>Обычный поиск (500 монет), средние игроки</p>
            <input type="hidden" name="price" value="500">
            <input type="hidden" value="1" name="finder">
            <button type="submit" style="cursor: pointer">Найти игрока</button>
        </form>
        <form action="${prc}/players/add-players" method="post">
            <input type="hidden" name="price" value="800">
            <p>Дорогой поиск (800 монет), сильные игроки</p>
            <input type="hidden" value="2" name="finder">
            <button type="submit" style="cursor: pointer">Найти игрока</button>
        </form>
    </div>
    <div>
        <c:if test="${requestScope.get('exp') != null}">
            <%request.removeAttribute("exp");%>
            <p style="color: red">У вас недостаточно средств!</p>
        </c:if>
    </div>
    <div>
        <c:if test="${requestScope.get('player') != null}">
            <p>Урааааа!!!! Вы нашли игрока!!!</p>
            <jsp:useBean id="player" class="ru.vstu_bet.models.beans.other.FullPlayerBean"
                         scope="request"/>
            <p><jsp:getProperty name="player" property="name"/></p>
            <img src="${prc}/profile/getPhoto?add_to_path=rndForPlayers&photo_name=${player.photo}"
                 height="100" width="100" style="border-radius: 100px;">
            <p>Параметры:</p>
            <p>Физическая сила <jsp:getProperty name="player" property="stren"/> из 100</p>
            <p>Физическая выносливость <jsp:getProperty name="player" property="endurance"/> из 100</p>
            <p>Сила духа <jsp:getProperty name="player" property="stren_mind"/> из 100</p>
        </c:if>
    </div>
    <div>
        <a href="${prc}/players/my-players?page_number=1">Вернуться</a>
    </div>
</body>
</html>
