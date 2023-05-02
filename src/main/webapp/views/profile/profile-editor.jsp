<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редакция профиля</title>
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
    <h1>Редакция профиля</h1>
</div>
<div>
    <form action="${prc}/profile/photo-edit" method="post" enctype="multipart/form-data">
        <h3>Обновление фото (ФОТО В ФОРМАТЕ 1Х1)</h3>
        <button type="submit" style="cursor: pointer;">Обновить фото</button>
        <input type="file" name="photo" multiple accept="image/*,image/jpeg"
         style="cursor: pointer;">
    </form>
    <form action="${prc}/profile/edit" method="post">
        <h3>Обновление других данных</h3>
        <c:if test="${requestScope.get('errorEditProfile') != null}">
            <p>Данные успешно обновлены!</p>
            <%request.removeAttribute("errorEditProfile");%>
        </c:if>
        <p>Ваш логин:</p>
        <p>Ваш пароль:</p>
        <input type="text" value="${user.password}" name="password">
        <p>Ваш телефон: </p>
        <input type="text" value="${user.telephone}" name="telephone">
        <p>Ваше ФИО:</p>
        <input type="text" value="${user.fio}" name="fio"><br><br>
        <button type="submit" style="cursor: pointer;">Сохранить</button>
    </form>
    <div>
        <form action="${prc}/views/profile/profile-editor.jsp">
            <button type="submit" style="cursor: pointer;">Отменить</button>
        </form>
    </div>
</div>
<div>
    <form action="${prc}/views/profile/profile.jsp">
        <button type="submit" style="cursor: pointer;">Вернуться к профилю</button>
    </form>
</div>
</body>
</html>
