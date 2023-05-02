<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редакция команды</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<jsp:useBean id="team" class="ru.vstu_bet.models.beans.other.TeamBean" scope="request"/>
<c:set var="list_players" value="${requestScope.get('listPlayers')}" scope="page"/>
<c:set var="list_players_in_team" value="${requestScope.get('listPlayersTeam')}" scope="page"/>
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
    <div>
        <h1>Редактор команды <jsp:getProperty name="team" property="name_team"/></h1>
        <p>В команде человек: <jsp:getProperty name="team" property="peopleInTeam"/><br>
            Для спорта <b><jsp:getProperty name="team" property="nameSport"/></b>
            необходимо: <b><jsp:getProperty name="team" property="peopleInSport"/></b></p>
        <c:if test="${team.peopleInSport==team.peopleInTeam}">
            <p style="color: green;">Игроков в команде достаточно</p>
        </c:if>
        <c:if test="${team.peopleInSport > team.peopleInTeam}">
            <p style="color: red;">Слишком мало игроков</p>
        </c:if>
        <c:if test="${team.peopleInSport < team.peopleInTeam}">
            <p style="color: red;">Слишком много игроков</p>
        </c:if>
    </div>
    <div>
        <form action="${prc}/team/editor" method="post">
            <p>Добавить нового игрока:<br>
                <input type="hidden" name="typeAction" value="add">
                <input type="hidden" name="id_team" value="${team.id}">
                <select required name="id_player">
                    <option value="none" hidden="">Выберите игрока</option>
                    <c:forEach var="player" items="${list_players}">
                        <jsp:useBean id="player" class="ru.vstu_bet.models.beans.other.FullPlayerBean"/>
                        <option value="${player.id}">
                            <jsp:getProperty name="player" property="name"/>  |  показатели:
                            <jsp:getProperty name="player" property="endurance"/>;
                            <jsp:getProperty name="player" property="stren"/>;
                            <jsp:getProperty name="player" property="stren_mind"/>.
                        </option>
                    </c:forEach>
                </select>
            </p>
            <button type="submit" style="cursor: pointer">Добавить в команду</button>
        </form>
    </div>
    <div>
        Уже в команде:
        <c:forEach var="player_in_team" items="${list_players_in_team}">
            <div>
                <jsp:useBean id="player_in_team" class="ru.vstu_bet.models.beans.other.FullPlayerBean"/>
                <img src="${prc}/profile/getPhoto?add_to_path=rndForPlayers&photo_name=${player_in_team.photo}"
                     height="30" width="30" style="border-radius: 100px;">
                <p>
                    Игрок <b><jsp:getProperty name="player_in_team" property="name"/></b><br>
                    Показатели:
                    cила: <b><jsp:getProperty name="player_in_team" property="stren"/></b>;
                    сила духа: <b><jsp:getProperty name="player_in_team" property="stren_mind"/></b>;
                    выносливость: <b><jsp:getProperty name="player_in_team" property="endurance"/></b>.
                    id: <b><jsp:getProperty name="player_in_team" property="id"/></b>
                </p>
                <form action="${prc}/team/editor" method="post">
                    <input type="hidden" name="id_player" value="${player_in_team.id}">
                    <input type="hidden" name="id_team" value="${team.id}">
                    <input type="hidden" name="typeAction" value="del">
                    <button type="submit" style="cursor: pointer;">Выгнать из команды</button>
                </form>
            </div>
        </c:forEach>
    </div>
    <a href="${prc}/teams/list?page_number=1">Вернуться в меню команд</a>

</div>
</body>
</html>
