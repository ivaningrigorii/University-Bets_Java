package ru.vstu_bet.controllers.teams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.FullPlayerBean;
import ru.vstu_bet.models.beans.other.TeamBean;
import ru.vstu_bet.models.handlers.PlayerHandler;
import ru.vstu_bet.models.handlers.TeamHandler;
import ru.vstu_bet.models.handlers.TeamPlayerHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@WebServlet("/team/editor")
public class TeamEditorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {

            int id_team = Integer.parseInt(req.getParameter("id_team"));
            TeamBean teamBean = new TeamBean();

            try {
                teamBean = new TeamHandler().find(id_team, user.getId());
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                resp.sendRedirect("/teams/list?page_number=1");
            }

            PlayerHandler ph = new PlayerHandler();
            List<FullPlayerBean> playersTeam = new LinkedList<>();
            if ((teamBean.getPlayers() != null)&&(!teamBean.getPlayers().isEmpty())) {
                playersTeam = ph.getPlayersForIds(teamBean.getPlayers());
            }
            List<FullPlayerBean> players = ph.getsFull(user.getId());

            for (int i = 0; i < players.size(); i++) {
                sec: for (int j = 0; j < playersTeam.size(); j++) {
                    if (players.get(i).getId() == playersTeam.get(j).getId()) {
                        players.remove(i);
                        --i;
                        break sec;
                    }
                }
            }

            req.setAttribute("team", teamBean);
            req.setAttribute("listPlayersTeam", playersTeam);
            req.setAttribute("listPlayers", players);

            req.getRequestDispatcher("/views/teams/team-editor.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int id_team = Integer.parseInt(req.getParameter("id_team"));
            int id_player = Integer.parseInt(req.getParameter("id_player"));

            if (req.getParameter("typeAction").equals("add")) {
                new TeamPlayerHandler().add(id_team, id_player);
            } else {
                new TeamPlayerHandler().del(id_team, id_player);
            }
            resp.sendRedirect("/team/editor?id_team="+id_team);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
