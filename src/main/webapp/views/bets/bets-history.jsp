<%@ page import="ru.vstu_bet.models.beans.db.Game" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>История ставок</title>
</head>
<body>
<jsp:useBean id="user" class="ru.vstu_bet.models.beans.db.User" scope="session">
    <%response.sendRedirect("/views/initialization/authorization.jsp");%>
</jsp:useBean>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<c:set var="bgs" value="${requestScope.get('bgs')}"/>

<div>
    <a href="${prc}/views/main-page.jsp">Главная</a>
    <a href="${prc}/views/profile/profile.jsp">Профиль</a>
    <a href="${prc}/bets">Ставки</a>
    <a href="${prc}/games/teams-ready">Игры</a>
    <a href="${prc}/teams/list?page_number=1">Команды</a>
    <a href="${prc}/players/my-players?page_number=1">Игроки</a>
</div>
<h1>История ваших ставок</h1>

<c:forEach var="bg" items="${bgs}">

    <jsp:useBean id="bg" class="ru.vstu_bet.models.beans.other.bets.BGBeand"/>

    <label>Игра № <b><jsp:getProperty name="bg" property="id_game"/></b>.</label>
    <label> от <jsp:getProperty name="bg" property="date"/></label><br>
    <label>Играли <jsp:getProperty name="bg" property="t1"/> vs <jsp:getProperty name="bg" property="t2"/></label><br>
    <label>Статус: </label>
    <c:if test="${bg.real_res==0}">
        <label style="color: gray">результатов ещё нет</label>
    </c:if>
    <c:if test="${bg.real_res==1}">
        <label style="color: rebeccapurple">победила команда <jsp:getProperty name="bg" property="t1"/></label>
    </c:if>
    <c:if test="${bg.real_res==2}">
        <label style="color: rebeccapurple">победила команда <jsp:getProperty name="bg" property="t2"/></label>
    </c:if>
    <c:if test="${bg.real_res==3}">
        <label style="color: rebeccapurple">ничья!</label>
    </c:if>
    <c:if test="${((bg.real_res!=0)&&(bg.real_res==bg.exp_res))}">
        <p style="color: green">Вы угадали!</p>
    </c:if>
    <c:if test="${((bg.real_res!=0)&&(bg.real_res!=bg.exp_res))}">
        <p style="color: red">Вы не угадали!</p>
    </c:if>
    <p>Ваша ставка: <jsp:getProperty name="bg" property="money"/></p>
    <br><br>
</c:forEach>

<a href="/views/bets/bets.jsp">Вернуться</a>
</body>
</html>
