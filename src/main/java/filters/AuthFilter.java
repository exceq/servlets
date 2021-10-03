package filters;

import models.User;
import services.DBService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;

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
