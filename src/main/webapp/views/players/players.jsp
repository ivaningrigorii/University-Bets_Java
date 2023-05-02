<%@ page import="ru.vstu_bet.models.handlers.PlayerHandler" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Игроки</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<%--${prc}--%>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<div>
    <h1>Меню ваших игроков </h1>
</div>
<div>
    <form action="${prc}/views/players/add-players.jsp">
        <button type="submit" style="cursor: pointer">Добавить нового игрока</button>
    </form>
    <jsp:useBean id="pg" class="ru.vstu_bet.models.beans.other.NumberPageBean" scope="request"/>
    <c:forEach var="player" items="${requestScope.get('list')}">
        <jsp:useBean id="player" class="ru.vstu_bet.models.beans.other.FullPlayerBean"/>
        <div>
                <img src="${prc}/profile/getPhoto?add_to_path=rndForPlayers&photo_name=${player.photo}"
                     height="30" width="30" style="border-radius: 100px;">
                <p>Игрок №<jsp:getProperty name="player" property="id"/>
                    ; <b><jsp:getProperty name="player" property="name"/></b></p>
                <p>Характеристики: сила духа - <jsp:getProperty name="player" property="stren_mind"/>;
                 сила - <jsp:getProperty name="player" property="stren"/>;
                выносливость - <jsp:getProperty name="player" property="endurance"/>.</p>
            <form action="${prc}/players/delete" method="get">
                <input type="hidden" value="${player.id}" name="id_players">
                <input type="hidden" value="${pg.numberPage}" name="pages">
                <button type="submit" style="cursor: pointer">Продать (+100 монет)</button>
            </form>
        </div>
    </c:forEach>
    <div><p>
        <c:forEach items="${pg.dataPages}" var="new_page">
            <c:if test="${new_page!=pg.numberPage}">
                <a href="${prc}/players/my-players?page_number=${new_page}">${new_page}</a>
            </c:if>
            <c:if test="${new_page==pg.numberPage}">
                <b>${new_page}</b>
            </c:if>
        </c:forEach>
    </p></div>
</div>
</body>
</html>
