<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Готовые к игре команды</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<c:set var="etbList" value="${requestScope.get('etb')}"/>
<c:set var="myEtList" value="${requestScope.get('myEt')}"/>
<c:set var="myNotEtList" value="${requestScope.get('myNotEt')}"/>

<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<h1>Команды для игры</h1>
<a href="${prc}/games">Ваши активные игры</a>
<h4>Выставление и удаление своих команд
    на игру</h4>
<form action="${prc}/games/team/add-ex" method="get">
    <select required name="addSelect">
        <c:forEach var="minAddTeam" items="${myNotEtList}">
            <jsp:useBean id="minAddTeam" class="ru.vstu_bet.models.beans.other.teams.MiniTeamBean"/>
            <option value="${minAddTeam.id_team}">
                <jsp:getProperty name="minAddTeam" property="name_team"/>
            </option>
        </c:forEach>
    </select>
    <button type="submit" style="cursor: pointer">Ожидать игры</button>
</form>
<form action="${prc}/games/team/del-ex" method="get">
    <select required name="delSelect">
        <c:forEach var="minDelTeam" items="${myEtList}">
            <jsp:useBean id="minDelTeam" class="ru.vstu_bet.models.beans.other.teams.MiniTeamBean"/>
            <option value="${minDelTeam.id_team}">
                <jsp:getProperty name="minDelTeam" property="name_team"/>
            </option>
        </c:forEach>
    </select>
    <button type="submit" style="cursor: pointer">Отдыхать</button>
</form>

<h4>Просмотр ожидающих игру</h4>
<c:forEach var="team" items="${etbList}">
    <jsp:useBean id="team" class="ru.vstu_bet.models.beans.other.teams.ExpecTeamsBean"/>
    <div style="
        background: lightblue;
        width: fit-content;
        border-radius: 25px;
        ">
        <p>Команда <b><jsp:getProperty name="team" property="name"/></b>;
            спорт <jsp:getProperty name="team" property="name_sport"/>; создатель-пользователь
            <jsp:getProperty name="team" property="user_name"/></p>
    </div>
</c:forEach>
</body>
</html>
