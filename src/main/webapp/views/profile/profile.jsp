<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.vstu_bet.models.PhotoPathGenerator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Профиль</title>
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
<div>
    <div>
        <h1>Профиль</h1>
    </div>
    <div>
        <h2><jsp:getProperty name="user" property="fio"/></h2>
        <img src="${prc}/profile/getPhoto?add_to_path=users&photo_name=${user.photo}"
             height="100" width="100" style="border-radius: 100px;">
        <p style="color: red">Ваши денюжки: <jsp:getProperty name="user" property="score"/></p>
        <p>Ваш логин: <jsp:getProperty name="user" property="login"/></p>
        <p>Ваш телефон: <jsp:getProperty name="user" property="telephone"/></p>
    </div>
    <div>
        <form action="${prc}/views/profile/profile-editor.jsp">
            <button style="cursor: pointer;">Редактировать</button>
        </form>
        <form action="${prc}/exit" method="get">
            <button style="cursor: pointer;">Выйти</button>
        </form>
    </div>
</div>

</body>
</html>
