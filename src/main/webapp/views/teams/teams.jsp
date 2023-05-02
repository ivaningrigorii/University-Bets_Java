<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Ваши команды</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="pg" class="ru.vstu_bet.models.beans.other.NumberPageBean" scope="request"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<div>
    <h1>Команды</h1>
    <h4>Ваши команды в состязаниях скрываются</h4>
</div>
<div>
    <form action="${prc}/teams/add">
        <button type="submit" style="cursor: pointer">Создать новую команду</button>
    </form>
</div>
<div>
    <c:forEach var="teams" items="${requestScope.get('list')}">
        <jsp:useBean id="teams" class="ru.vstu_bet.models.beans.other.TeamBean"/>
        <div style="
            background: #fc0;
            border-radius: 10px;
            width: 600px;">
            <img src="${prc}/profile/getPhoto?add_to_path=team&photo_name=${teams.photo_team}"
                 height="30" width="30" style="border-radius: 100px;">
            <p>
                Команда <b><jsp:getProperty name="teams" property="name_team"/></b>
                № <jsp:getProperty name="teams" property="id"/><br>
                Сосредоточена на спорте: <jsp:getProperty name="teams" property="nameSport"/>;<br>
                должно быть человек - <jsp:getProperty name="teams" property="peopleInSport"/><br>
                в команде - <jsp:getProperty name="teams" property="peopleInTeam"/>
            <c:if test="${teams.peopleInTeam != teams.peopleInSport}">
            <p style="color: red">Неверное количество людей в команде!</p>
            </c:if>
            <c:if test="${teams.peopleInTeam == teams.peopleInSport}">
                <p style="color: green">Все в сборе</p>
            </c:if>
            </p>
            <form action="${prc}/team/editor" method="get">
                <input type="hidden" name="page" value="${pg.numberPage}">
                <input type="hidden" name="id_team" value="${teams.id}">
                <button type="submit" style="cursor: pointer;">Редактировать состав</button>
            </form>
            <form action="${pageContext.request.contextPath}/team/delete" method="get">
                <input type="hidden" name="page" value="${pg.numberPage}">
                <input type="hidden" name="id_team" value="${teams.id}">
                <button type="submit" style="cursor: pointer;">Расформировать</button>
            </form>
        </div>
    </c:forEach>
    <div><p>
        <c:forEach items="${pg.dataPages}" var="new_page">
            <c:if test="${new_page!=pg.numberPage}">
                <a href="${prc}/teams/list?page_number=${new_page}">${new_page}</a>
            </c:if>
            <c:if test="${new_page==pg.numberPage}">
                <b>${new_page}</b>
            </c:if>
        </c:forEach>
    </p></div>
</div>
</body>
</html>
