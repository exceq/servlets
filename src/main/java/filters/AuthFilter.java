package filters;

import models.UserProfile;
import services.AccountService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = {"/files", "/download", "/", "/*"})
public class AuthFilter implements Filter {
    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        String sessionId = req.getSession().getId();
        UserProfile profile = AccountService.getUserBySessionId(sessionId);
        if (profile != null || (login != null && pass != null)) {
            filterChain.doFilter(request, response);
        } else {
            filterConfig.getServletContext().getRequestDispatcher("/view/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
