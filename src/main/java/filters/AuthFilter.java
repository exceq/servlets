package filters;

import models.UserProfile;
import services.AccountService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
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
        UserProfile profile = AccountService.getUserBySessionId(sessionId);
        if (profile == null && (login == null || password == null)) {
            req.getServletContext().getRequestDispatcher("/view/login.jsp").forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }
}
