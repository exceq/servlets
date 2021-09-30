package filters;

import models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import services.AccountService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = {"/files", "/download", "/"})
public class AuthFilter implements Filter {
    AccountService accountService;
    FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accountService = new AccountService();
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        String login = req.getParameter("login");
        String pass = req.getParameter("password");


        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile != null) {
            config.getServletContext().getRequestDispatcher("/files").forward(req, resp);
        } else if (profile == null && (login == null || pass == null)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            config.getServletContext().getRequestDispatcher("/view/login.jsp").forward(req, resp);
        } else if (login != null) {
            UserProfile user = accountService.getUserByLogin(login);
            if (user != null && user.getPass().equals(pass)){
                accountService.addSession(sessionId, user);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
