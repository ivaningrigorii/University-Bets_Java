<%@ page import="static java.lang.Double.NaN" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ставки</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<c:set var="games" value="${requestScope.get('games')}"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<a href="/bets/history">Результаты прошлых ставок</a>
<h1>Актуальные игры для ставок</h1>
<div>
    <c:set var="find" value="${requestScope.get('stringWord')}"/>
    <form action="/bets" method="get">
        <input type="text" name="stringWord" value="${find}">
        <button type="submit" style="cursor: pointer">Найти по команде</button>
    </form>
</div>
<c:forEach var="gm" items="${games}">
    <jsp:useBean id="gm" class="ru.vstu_bet.models.beans.other.games.GameBean"/>
    <div style="
    background-color: linen;
    width: 600px;
    border-radius: 25px;">
        <div style="
        width: fit-content;
        height: fit-content;
        margin: auto;
        ">
            <b><label>Игра №<jsp:getProperty name="gm" property="id_game"/></label>
                <label> Спорт: <jsp:getProperty name="gm" property="name_sport"/></label></b>
        </div><br>
        <div style="
        width: fit-content;
        height: fit-content;
        margin: auto;
        ">
            <form action="/bets/bets-maker" method="get" style="display: inline-block">
                <%!String o1 = "нет %";%>
                <%if ((!Double.isNaN(gm.getOdds()[0])&&(!Double.isInfinite(gm.getOdds()[0])))) {
                    o1 = String.valueOf(Math.round(gm.getOdds()[0]*100)/100d);
                }%>
                <input type="hidden" name="id_game" value="${gm.id_game}">
                <label>(<%=o1%>)<a href="${prc}/teams/team-profile?id_team=${gm.teams.get(0).id_team}&id_user=${gm.user_ids.get(0)}&lastUrl=1">
                    <b>${gm.teams.get(0).name_team}</b></a>
                </label>
                <button type="submit" style="cursor: pointer">Ставка</button>
            </form>
            <label>___<b>VS</b>___</label>
            <%!String o2 = "нет %";%>
            <%if ((!Double.isNaN(gm.getOdds()[1])&&(!Double.isInfinite(gm.getOdds()[1])))) {
                o2 = String.valueOf(Math.round(gm.getOdds()[1]*100)/100d);
            }%>
            <form action="/bets/bets-maker" method="get" style="display: inline-block">
                <input type="hidden" name="id_game" value="${gm.id_game}">
                <button type="submit" style="cursor: pointer">Ставка</button>
                <label><a href="${prc}/teams/team-profile?id_team=${gm.teams.get(1).id_team}&id_user=${gm.user_ids.get(1)}&lastUrl=1">
                    <b>${gm.teams.get(1).name_team}</b></a>
                    (<%=o2%>)</label>
            </form>
        </div>
    </div>
</c:forEach>
</body>
</html>
