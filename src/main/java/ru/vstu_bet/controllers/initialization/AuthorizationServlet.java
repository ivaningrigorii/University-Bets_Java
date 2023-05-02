package ru.vstu_bet.controllers.initialization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String log = req.getParameter("login");
        String pas = req.getParameter("password");
        User ur = new UserHandler().init(log, pas);

        if (ur != null) {
            req.getSession().setAttribute("user", ur);
            resp.sendRedirect("/views/profile/profile.jsp");
        } else {
            req.setAttribute("error", "Не верный логин или пароль");
            req.getRequestDispatcher("/views/initialization/authorization.jsp").forward(req, resp);
        }
    }
}
