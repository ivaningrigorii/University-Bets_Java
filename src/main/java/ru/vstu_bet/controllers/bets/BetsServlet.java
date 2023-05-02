package ru.vstu_bet.controllers.bets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.games.GameBean;
import ru.vstu_bet.models.handlers.GameHandler;
import ru.vstu_bet.models.handlers.Odds;

import java.io.IOException;
import java.util.List;

@WebServlet("/bets")
public class BetsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            String findWord = req.getParameter("stringWord");
            List<GameBean> games = new GameHandler().betsActive(findWord);

            req.setAttribute("games", games);
            req.setAttribute("stringWord", req.getParameter("stringWord"));
            req.getRequestDispatcher("/views/bets/bets.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
