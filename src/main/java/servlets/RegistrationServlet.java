package servlets;

import models.User;
import services.DBService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (login == null || password == null || email == null) {
            resp.sendRedirect("/view/registration.jsp");
            return;
        }

        User user = new User(login, password, email);

        try {
            if (DBService.getUserByLogin(login) != null){
                throw new SQLException();
            }
            DBService.addUser(user);
            DBService.addSession(req.getSession().getId(), user);
            resp.sendRedirect("/files");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("/view/login.jsp");
        }
    }
}
