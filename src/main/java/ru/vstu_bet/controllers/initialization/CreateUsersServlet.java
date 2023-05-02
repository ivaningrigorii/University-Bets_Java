package ru.vstu_bet.controllers.initialization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;

@WebServlet("/initialization/create-users")
public class CreateUsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();

        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setFio(req.getParameter("fio"));
        user.setTelephone(req.getParameter("telephone"));
        user.setScore(2000d);
        try {
            new UserHandler().create(user);
            resp.sendRedirect("/views/initialization/authorization.jsp");
        } catch (Exception exp) {
            req.setAttribute("exp", "1");
            req.getRequestDispatcher("/views/initialization/registration.jsp")
                    .forward(req, resp);
        }
    }
}
