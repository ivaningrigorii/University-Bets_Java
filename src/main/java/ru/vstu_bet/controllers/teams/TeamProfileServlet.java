package ru.vstu_bet.controllers.teams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.controllers.PageWorker;
import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.db.Team;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.FullPlayerBean;
import ru.vstu_bet.models.beans.other.NumberPageBean;
import ru.vstu_bet.models.beans.other.TeamBean;
import ru.vstu_bet.models.handlers.PlayerHandler;
import ru.vstu_bet.models.handlers.TeamHandler;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/teams/team-profile")
public class TeamProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int lastUrl = Integer.parseInt(req.getParameter("lastUrl"));
            int id_team = Integer.parseInt(req.getParameter("id_team"));
            int id_user = Integer.parseInt(req.getParameter("id_user"));
            TeamBean teamBean = new TeamBean();

            User us = new UserHandler().get(id_user);

            try {
                teamBean = new TeamHandler().find(id_team, us.getId());
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                resp.sendRedirect("/teams/list?page_number=1");
            }
            PlayerHandler ph = new PlayerHandler();
            List<FullPlayerBean> playersTeam = new LinkedList<>();
            if ((teamBean.getPlayers() != null)&&(!teamBean.getPlayers().isEmpty())) {
                playersTeam = ph.getPlayersForIds(teamBean.getPlayers());
            }

            req.setAttribute("us", us);
            req.setAttribute("players", playersTeam);
            req.setAttribute("team", teamBean);
            req.getRequestDispatcher("/views/teams/team-profile.jsp").forward(req, resp);
            req.setAttribute("lastUrl", lastUrl);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
