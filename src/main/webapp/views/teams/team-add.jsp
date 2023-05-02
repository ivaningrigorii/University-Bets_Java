<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание команды</title>
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
    <h1>Мастер создание команы</h1>
</div>
<div>
    <form action="${prc}/teams/add" method="post" enctype="multipart/form-data">
        <p>Добавить фото-логотип команды</p>
        <input type="file" name="photo" multiple accept="image/*,image/jpeg"/>

        <p>Придумайте название команды</p>
        <input type="text" name="name_team"><br>

        <p>Вид спорта</p>
        <select required name="fk_sport[]">
            <option value="none" hidden="">Выберите спорт</option>
            <c:forEach var="sport" items="${requestScope.get('sportList')}">
                <jsp:useBean id="sport" class="ru.vstu_bet.models.beans.db.Sport"/>
                <option value="${sport.id}">
                    <jsp:getProperty name="sport" property="name_sport"/>
                </option>
            </c:forEach>
        </select>
        <br><br>
        <button type="submit" style="cursor: pointer;">Создать команду</button>
    </form>
    <a href="${prc}/teams/list?page_number=1">Вернуться</a>
</div>
</body>
</html>
