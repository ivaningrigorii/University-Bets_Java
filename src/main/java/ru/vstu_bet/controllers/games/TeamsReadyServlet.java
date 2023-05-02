package ru.vstu_bet.controllers.games;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.TeamHandler;

import java.io.IOException;

@WebServlet("/games/teams-ready")
public class TeamsReadyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            TeamHandler th = new TeamHandler();

            req.setAttribute("etb", th.getExpecTeams());
            req.setAttribute("myEt", th.getMyExpecTeams(user.getId()));
            req.setAttribute("myNotEt", th.getMyNotExpecTeams(user.getId()));

            req.getRequestDispatcher("/views/games/teams-ready.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
