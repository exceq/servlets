package servlets;

import models.User;
import services.DBService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (login == null || password == null || email == null)
            resp.sendRedirect("/view/registration.jsp");

        User user = new User(login, password, email);
        try {
            DBService.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/files");
    }
}
