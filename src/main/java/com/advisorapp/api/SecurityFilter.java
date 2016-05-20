package com.advisorapp.api;

import com.advisorapp.api.domain.User;
import com.advisorapp.api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by damien on 20/05/2016.
 */
public class SecurityFilter implements Filter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            if(isGetTokenRequest(request)) {
                filterChain.doFilter(
                        servletRequest,
                        servletResponse
                );
            }else{
                processTokenCheck(servletResponse, filterChain, request);
            }
        } catch (Exception e) {
            System.err.println("SecurityFilter error : " + e.getMessage());
        } finally {
            sendUnauthorizedResponse((HttpServletResponse) servletResponse);
        }
    }

    private void processTokenCheck(ServletResponse servletResponse, FilterChain filterChain, HttpServletRequest request) throws IOException, ServletException {
        String token = request.getHeader("X-Authorization");
        if (token != null) {
            Optional<User> userOpt = authenticationService.verifyToken(token);
            if(userOpt.isPresent()){
                filterChain.doFilter(
                        new SecuredRequest(request, userOpt.get()),
                        servletResponse
                );
            }
        }
    }

    private boolean isGetTokenRequest(HttpServletRequest request) {
        return "/api/auths/token".equals(request.getServletPath());
    }


    private void sendUnauthorizedResponse(HttpServletResponse servletResponse) {
        HttpServletResponse response = servletResponse;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        // TODO write json error in body
//        response.getOutputStream().write();
    }



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
