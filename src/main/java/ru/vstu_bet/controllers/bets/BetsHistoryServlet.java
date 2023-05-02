package ru.vstu_bet.controllers.bets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.bets.BGBeand;
import ru.vstu_bet.models.beans.other.games.GameBean;
import ru.vstu_bet.models.handlers.BetsHandler;
import ru.vstu_bet.models.handlers.GameHandler;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bets/history")
public class BetsHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            req.getSession().setAttribute("user", new UserHandler().get(user.getId()));
            BetsHandler betsHandler = new BetsHandler();
            betsHandler.getMyBets(user.getId());

            List<BGBeand> bgBeands = new ArrayList<>();

            for (int i = 0; i < betsHandler.getBbets().size(); i++) {
                bgBeands.add(
                        new BGBeand(
                                betsHandler.getGgames().get(i),
                                betsHandler.getBbets().get(i),
                                betsHandler.getTteams().get(i)
                        )
                );
            }
            req.setAttribute("bgs", bgBeands);

            req.getRequestDispatcher("/views/bets/bets-history.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
