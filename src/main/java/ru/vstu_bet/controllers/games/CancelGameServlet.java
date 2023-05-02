package ru.vstu_bet.controllers.games;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.games.GameBean;
import ru.vstu_bet.models.handlers.GameHandler;

import java.io.IOException;
import java.util.List;

@WebServlet("/games/cancel-game")
public class CancelGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int id_game = Integer.parseInt(req.getParameter("id_game"));
            new GameHandler().del(id_game);
            resp.sendRedirect("/games");
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
