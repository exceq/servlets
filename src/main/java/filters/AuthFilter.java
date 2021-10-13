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

@WebFilter(urlPatterns = {"/", "/files", "/download"})
public class AuthFilter extends HttpFilter {

    @Override
    public void destroy() {
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        User user = DBService.getUserBySessionId(req.getSession().getId());
        if (user == null) {
            resp.sendRedirect("/view/login.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }
}
