package servlets;

import javassist.NotFoundException;
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

        if (DBService.getUserByLogin(login) != null){
            throw new RuntimeException("This login already used: " + login);
        }
        DBService.addSession(req.getSession().getId(), user);
        resp.sendRedirect("/files");
    }
}
