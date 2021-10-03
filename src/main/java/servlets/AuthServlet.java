package servlets;

import models.UserProfile;
import services.AccountService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null)
            resp.sendRedirect("/view/login.jsp");

        UserProfile profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.getPassword().equals(password))
            resp.sendRedirect("/view/login.jsp");

        AccountService.addSession(req.getSession().getId(), profile);
        req.getSession().setAttribute("login", login);
        resp.sendRedirect("/files");
    }
}
