package servlets;

import models.User;
import services.DBService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        User user = DBService.getUserByLogin(login);
        if (user == null || !user.getPassword().equals(password)){
            resp.sendRedirect("/view/login.jsp");
            return;
        }
        DBService.addSession(req.getSession().getId(), user);
        resp.sendRedirect("/files");
    }
}
