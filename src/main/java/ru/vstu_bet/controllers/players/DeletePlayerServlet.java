package ru.vstu_bet.controllers.players;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.PlayerHandler;

import java.io.IOException;

@WebServlet("/players/delete")
public class DeletePlayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {

            int id_player = Integer.parseInt(req.getParameter("id_players"));
            int pages = Integer.parseInt(req.getParameter("pages"));

            user.setScore(user.getScore()+100);
            new PlayerHandler().delete(id_player, user);

            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/players/my-players?page_number="+pages);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
