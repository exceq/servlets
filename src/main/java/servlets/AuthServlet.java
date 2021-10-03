package servlets;

import models.UserProfile;
import services.DBService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        UserProfile profile = null;
        try {
            profile = DBService.getUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO redirect to login.jsp
        }
        if (profile == null || !profile.getPassword().equals(password)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("/view/login.jsp");
            return;
        }

        try {
            DBService.addSession(req.getSession().getId(),profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("login", login);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect("/files");
    }
}
