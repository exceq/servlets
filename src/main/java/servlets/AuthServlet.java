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

        User user = null;
        try {
            user = DBService.getUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        if (user == null || !user.getPassword().equals(password)){
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        try {
            DBService.addSession(req.getSession().getId(),user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("login", login);
        resp.sendRedirect("/files");
    }
}
