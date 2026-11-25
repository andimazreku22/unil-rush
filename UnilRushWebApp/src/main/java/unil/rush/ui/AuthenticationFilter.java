package unil.rush.ui;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"*.xhtml"})

public class AuthenticationFilter implements Filter {
    @Inject
    private LoginBean loginBean;
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        HttpServletResponse res = (HttpServletResponse) response;
        if (requestURI.contains("resource")) {
            chain.doFilter(request, response);
            return;
        }
        String indexURI    = req.getContextPath() + "/index.xhtml";
        String loginURI    = req.getContextPath() + "/Login.xhtml";
        String registerURI = req.getContextPath() + "/Register.xhtml";
        boolean publicRequest = requestURI.equals(indexURI) || requestURI.equals(loginURI) || requestURI.equals(registerURI);

        if (publicRequest || loginBean.getCurrentPlayer() != null)
            chain.doFilter(request,response);
        else
            res.sendRedirect(loginURI);

    }
}
