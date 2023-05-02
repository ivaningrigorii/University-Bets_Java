<%@ page import="ru.vstu_bet.models.beans.other.teams.MiniTeamBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ваши активные игры</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="games" value="${requestScope.get('games')}"/>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<h1>Ваши активные игры</h1>
<c:forEach var="game" items="${games}">
    <div>
        <jsp:useBean id="game" class="ru.vstu_bet.models.beans.other.games.GameBean"/>
        <c:set var="g" value="${game}"/>

        <c:set var="t" value="${g.teams}"/>
        <c:set var="t1" value="${t.get(0)}"/>
        <c:set var="t2" value="${t.get(1)}"/>

        <jsp:useBean id="t1" class="ru.vstu_bet.models.beans.other.teams.MiniTeamBean"/>
        <jsp:useBean id="t2" class="ru.vstu_bet.models.beans.other.teams.MiniTeamBean"/>

        <label style="color: blue">Игра №<b><jsp:getProperty name="game" property="id_game"/></b>;<br>
            вид спорта <b><jsp:getProperty name="game" property="name_sport"/></b></label><br>
        <label>Ваша команда
            <c:set var="id_my_team"/>
            <c:if test="${g.user_ids.get(0)==user.id}">
                ${t1.name_team}
                <c:set var="id_my_team" value="${t1.id_team}"/>
            </c:if>
            <c:if test="${g.user_ids.get(1)==user.id}">
                ${t2.name_team}
                <c:set var="id_my_team" value="${t2.id_team}"/>
            </c:if>
        </label><br><br>

        <label>Команда
            <a href="/teams/team-profile?id_team=${t1.id_team}&id_user=${g.user_ids.get(0)}&lastUrl=0"><b>${t1.name_team}</b></a></label>
        <c:if test="${g.play_starts.get(0)==true}">
            <label style="color: green">Готова к игре</label>
        </c:if>
        <c:if test="${g.play_starts.get(0)==false}">
            <label style="color: gray">Сбор ставок</label>
        </c:if>
        <br>
        <label style="color: red">VS</label><br>
        <label>Команда  <a href="/teams/team-profile?id_team=${t2.id_team}&id_user=${g.user_ids.get(1)}&lastUrl=0"><b>${t2.name_team}</b></a></label>
        <c:if test="${g.play_starts.get(1)==true}">
            <label style="color: green">Готова к игре</label>
        </c:if>
        <c:if test="${g.play_starts.get(1)==false}">
            <label style="color: gray">Сбор ставок</label>
        </c:if>
        <br>
        <form action="${prc}/games/ready-to-start" method="get">
            <input type="hidden" name="id_team" value="${id_my_team}"/>
            <input type="hidden" name="id_game" value="${g.id_game}"/>
            <button type="submit" style="cursor: pointer">Готов к состязанию</button>
        </form>
        <form action="${prc}/games/not-ready-to-start" method="get">
            <input type="hidden" name="id_team" value="${id_my_team}"/>
            <input type="hidden" name="id_game" value="${g.id_game}"/>
            <button type="submit" style="cursor: pointer">Продолжить сбор ставок</button>
        </form>
        <form action="${prc}/games/cancel-game" method="get">
            <input type="hidden" name="id_team" value="${id_my_team}"/>
            <input type="hidden" name="id_game" value="${g.id_game}"/>
            <button type="submit" style="cursor: pointer">Отменить игру</button>
        </form>
    </div>
</c:forEach>
<a href="${prc}/games/teams-ready">Вернуться</a>

</body>
</html>
