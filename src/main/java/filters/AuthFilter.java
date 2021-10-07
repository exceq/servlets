package filters;

import models.User;
import services.DBService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/","/files"})
public class AuthFilter extends HttpFilter {


    @Override
    public void destroy() {
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String sessionId = req.getSession().getId();
        User user = null;
        try {
            user = DBService.getUserBySessionId(sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("/view/login.jsp");
        }
        if (user == null && (login == null || password == null)) {
            resp.sendRedirect("/view/login.jsp");
        }
        chain.doFilter(req, resp);
    }
}
