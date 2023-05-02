package ru.vstu_bet.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.PhotoPathGenerator;
import ru.vstu_bet.models.beans.db.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/profile/getPhoto")
public class GetUrPhotoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String path = PhotoPathGenerator.getPath() + "\\" +
                req.getParameter("add_to_path") +"\\"
                + req.getParameter("photo_name");

        File file = new File(path);

        resp.setContentType(Files.probeContentType(file.toPath()));
        resp.getOutputStream().write(Files.readAllBytes(file.toPath()));
    }
}
