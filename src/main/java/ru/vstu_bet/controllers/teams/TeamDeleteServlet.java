package ru.vstu_bet.controllers.teams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.TeamHandler;
import ru.vstu_bet.models.handlers.TeamPlayerHandler;

import java.io.IOException;

@WebServlet("/team/delete")
public class TeamDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int id_team = Integer.parseInt(req.getParameter("id_team"));
            int page = Integer.parseInt(req.getParameter("page"));

            new TeamHandler().del(id_team, user.getId());

            resp.sendRedirect("/teams/list?page_number="+page);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
