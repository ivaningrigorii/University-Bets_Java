<%@ page import="ru.vstu_bet.models.handlers.BetsHandler" %>
<%@ page import="ru.vstu_bet.models.beans.db.Bets" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редакция ставок</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="bet" scope="request" class="ru.vstu_bet.models.beans.db.Bets"/>
<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<h1>Редакция ставки</h1>
<h2>Ставка на игру №<%=request.getParameter("id_game")%></h2>
<label>Ваша сумма ставки: <jsp:getProperty name="bet" property="money"/></label>
    <p>Введите желаюмую сумму</p>
    <p style="color: rebeccapurple">Ваш счёт: <jsp:getProperty name="user" property="score"/></p>
    <c:if test="${bet.expected_result==3}">
        <p>Вы ставили на ничью</p>
    </c:if>
    <c:if test="${bet.expected_result==2}">
        <p>Вы ставили на 2 команду</p>
    </c:if>
    <c:if test="${bet.expected_result==1}">
        <p>Вы ставили на 1 команду</p>
    </c:if>
    <c:if test="${bet.expected_result==0}">
        <p>Вы пока не делали ставки</p>
    </c:if>
    <form action="${prc}/bets/bets-maker-update" method="get">
        <input type="text" name="money" value="${bet.money}">
        <input type="hidden" name="id_bet" value="${bet.id}">
        <input type="hidden" name="id_game" value="${bet.fk_game}">
        <p>За кого ставка:</p>
        <select required name="slct">
            <option value="1">Команда №1</option>
            <option value="2">Команда №2</option>
            <option value="3">Ничья</option>
        </select><br>
        <button type="submit" style="cursor: pointer">Изменить ставку</button>
    </form>
<a href="/bets">Вернуться</a>
</body>
</html>
