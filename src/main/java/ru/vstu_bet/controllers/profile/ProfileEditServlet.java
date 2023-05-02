package ru.vstu_bet.controllers.profile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;

@WebServlet("/profile/edit")
public class ProfileEditServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String names[] = {"password", "telephone", "fio"};
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() !=null)) {
            user.setFio(req.getParameter("fio"));
            user.setTelephone(req.getParameter("telephone"));
            user.setPassword(req.getParameter("password"));
            user = new UserHandler().update(user);

            req.getSession().setAttribute("user", user);

            req.setAttribute("errorEditProfile", "noError");
            req.getRequestDispatcher("/views/profile/profile-editor.jsp").forward(req, resp);
        }
    }
}
