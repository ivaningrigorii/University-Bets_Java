<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Профиль команды</title>
</head>
<body>
<h1>Профиль команды</h1>
</body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="team" class="ru.vstu_bet.models.beans.other.TeamBean" scope="request"/>
<jsp:useBean id="us" class="ru.vstu_bet.models.beans.db.User" scope="request"/>
<c:set var="players" value="${requestScope.get('players')}"/>
<c:set var="lastUrl" value="${requestScope.get('lastUrl')}"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<h1>Профиль команды <jsp:getProperty name="team" property="name_team"/></h1>
<p>Создатель команды</b>: </p>
<p><b><jsp:getProperty name="us" property="fio"/></b></p>
<p>Логин: <jsp:getProperty name="us" property="login"/></p>
<img src="${prc}/profile/getPhoto?add_to_path=users&photo_name=${us.photo}"
     height="100" width="100" style="border-radius: 100px;">
<p></p>
<p>Состав команды: </p>
<c:forEach var="player" items="${players}">
    <jsp:useBean id="player" class="ru.vstu_bet.models.beans.other.FullPlayerBean"/>
    <div>
        <p>Игрок <jsp:getProperty name="player" property="name"/></p>
        <img src="${prc}/profile/getPhoto?add_to_path=rndForPlayers&photo_name=${player.photo}"
             height="100" width="100" style="border-radius: 100px;">
        <p>Показатели: сила <jsp:getProperty name="player" property="stren"/>;
        сила духа: <jsp:getProperty name="player" property="stren_mind"/>;
        выносливость: <jsp:getProperty name="player" property="endurance"/>.</p>
    </div>
</c:forEach>
<%!String paths[] = new String[]{"/games", "/bets"};%>
<a href=<%=paths[Integer.parseInt(request.getParameter("lastUrl"))]%>>Вернуться</a>
</html>
