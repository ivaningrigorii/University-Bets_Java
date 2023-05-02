package ru.vstu_bet.controllers.games;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.GameHandler;

import java.io.IOException;

@WebServlet("/games/ready-to-start")
public class ReadyToStartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int id_team = Integer.parseInt(req.getParameter("id_team"));
            int id_game = Integer.parseInt(req.getParameter("id_game"));
            new GameHandler().toReadyToStart(id_game, id_team, true);
            resp.sendRedirect("/games");
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
