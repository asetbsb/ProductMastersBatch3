package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebFilter("/attendance")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String role = session != null ? (String) session.getAttribute("role") : null;

        // пускаем и teacher, и student
        if (!Objects.isNull(role)
                && ("teacher".equalsIgnoreCase(role) || "student".equalsIgnoreCase(role))) {
            chain.doFilter(request, response);
        } else {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("У вас нет доступа к этой странице");
        }
    }
}
