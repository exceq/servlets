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
        String pass = req.getParameter("password");

        if (login == null || pass == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        UserProfile profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        AccountService.addSession(req.getSession().getId(), profile);


        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect("/files");
    }

    @Override
    public void init() throws ServletException {

    }
}
