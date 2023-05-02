package ru.vstu_bet.controllers.teams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.vstu_bet.models.beans.db.Sport;
import ru.vstu_bet.models.beans.db.Team;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.SportHandler;
import ru.vstu_bet.models.handlers.TeamHandler;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@MultipartConfig(location = "D:\\Пользователи\\Документы\\" +
        "EDUCATION\\VSTU\\5 семестр\\EE\\course work program" +
        "\\vstu_bet\\src_photo\\team")
@WebServlet("/teams/add")
public class TeamAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            List<Sport> sportList = new SportHandler().getAll();
            req.setAttribute("sportList", sportList);
            req.getRequestDispatcher("/views/teams/team-add.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            Part part = req.getPart("photo");
            String namePhoto = UUID.randomUUID().toString()
                    + user.getId() + part.getSubmittedFileName();
            part.write(namePhoto);

            Team team = new Team();
            team.setName_team(req.getParameter("name_team"));
            team.setPhoto_team(namePhoto);
            team.setFk_user(user.getId());
            team.setFk_sport(Integer.parseInt(req.getParameter("fk_sport[]")));

            new TeamHandler().add(team);
            String url = "/team/editor?id_team=" + String.valueOf(team.getId());
            resp.sendRedirect(url);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
