package ru.vstu_bet.controllers.profile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.handlers.UserHandler;

import java.awt.*;
import java.io.IOException;
@MultipartConfig(location = "D:\\Пользователи\\Документы\\" +
        "EDUCATION\\VSTU\\5 семестр\\EE\\course work program" +
        "\\vstu_bet\\src_photo\\users")
@WebServlet("/profile/photo-edit")
public class ProfilePhotoEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getLogin() !=null)) {
            Part part = req.getPart("photo");
            part.write(user.getId()+part.getSubmittedFileName());

            UserHandler userHandler = new UserHandler();
            req.getSession().setAttribute("user",
                    userHandler.updatePhoto((user.getId()+part.getSubmittedFileName()), user));

            resp.sendRedirect("/views/profile/profile-editor.jsp");
        }
    }
}