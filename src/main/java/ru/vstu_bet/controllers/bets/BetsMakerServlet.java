package ru.vstu_bet.controllers.bets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.Bets;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.BetsHandler;

import java.io.IOException;

@WebServlet("/bets/bets-maker")
public class BetsMakerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int id_game = Integer.parseInt(req.getParameter("id_game"));
            Bets bet = new BetsHandler().getOdds(id_game, user.getId());
            if ((bet==null)||(bet.getId()==0)) {
                bet = new Bets();
                bet.setMoney(0); bet.setExpected_result(0); bet.setExpected_result(0);
                bet.setFk_game(id_game);
                bet.setFk_user(user.getId());
                new BetsHandler().create(bet);
                bet = new BetsHandler().getOdds(id_game, user.getId());
            }
            req.setAttribute("bet", bet);
            req.getRequestDispatcher("/views/bets/bets-maker.jsp").forward(req, resp);

        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
