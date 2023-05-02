package ru.vstu_bet.controllers.players;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.controllers.PageWorker;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.FullPlayerBean;
import ru.vstu_bet.models.beans.other.NumberPageBean;
import ru.vstu_bet.models.beans.other.TeamBean;
import ru.vstu_bet.models.handlers.PlayerHandler;
import ru.vstu_bet.models.handlers.TeamHandler;

import java.io.IOException;
import java.util.List;

@WebServlet("/players/my-players")
public class PlayersServlet extends HttpServlet {
    private final  int COUNT_ELEM = 3;

    private int getPageNumber(String strNumberPage) {
        return (strNumberPage==null)||(strNumberPage.equals(""))?
                1:Integer.parseInt(strNumberPage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int number_page = getPageNumber(req.getParameter("page_number"));

        if ((user != null) && (user.getLogin() != null)) {
            List<FullPlayerBean> list = new PlayerHandler().getsFull(user.getId());
            PageWorker pw = new PageWorker(COUNT_ELEM, list.size(), number_page);
            pw.correct_number_page();

            req.setAttribute("list", list.subList(pw.getLeft(), pw.getRight()));
            req.setAttribute("pg", new NumberPageBean(pw.getNumber_page(), COUNT_ELEM, pw.getMaxPages()));
            req.getRequestDispatcher("/views/players/players.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/views/initialization/authorization.jsp");
        }
    }
}
