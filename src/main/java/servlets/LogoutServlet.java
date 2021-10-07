package servlets;

import services.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        try {
            DBService.deleteSession(sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getSession().removeAttribute("login");
        resp.sendRedirect("/view/login.jsp");
    }
}