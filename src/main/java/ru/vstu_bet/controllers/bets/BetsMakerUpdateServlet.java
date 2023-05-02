package ru.vstu_bet.controllers.bets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.Bets;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.BetsHandler;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;

@WebServlet("/bets/bets-maker-update")
public class BetsMakerUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() != null)) {
            int id_game = Integer.parseInt(req.getParameter("id_game"));
            double money = Double.parseDouble(req.getParameter("money"));
            int exp_val = Integer.parseInt(req.getParameter("slct"));
            int id_bet = Integer.parseInt(req.getParameter("id_bet"));
            Bets test = new BetsHandler().find(id_bet);
            if ((money<0)||((test!=null)&&((money-test.getMoney())>user.getScore()))
                    ||((money>user.getScore())&&test==null)) {
                resp.sendRedirect("/views/bets/bets");
            } else {
                if (test==null) {
                    user.setScore(user.getScore()-money);
                } else {
                    user.setScore(user.getScore()-(money-test.getMoney()));
                }
                Bets bets = new Bets();
                bets.setMoney(money);
                bets.setExpected_result(exp_val);
                bets.setFk_game(id_game);
                bets.setFk_user(user.getId());
                bets.setId(id_bet);

                new UserHandler().update(user);
                new BetsHandler().update(bets);
                resp.sendRedirect("/bets/bets-maker?id_game="+bets.getFk_game());
            }
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
