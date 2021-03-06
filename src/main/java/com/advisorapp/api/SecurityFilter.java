package com.advisorapp.api;

import com.advisorapp.api.model.User;
import com.advisorapp.api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Optional;

/**
 * Created by damien on 20/05/2016.
 */
public class SecurityFilter implements Filter {

    @Autowired
    private AuthenticationService authenticationService;

    private final String HEADER_NAME = "X-Authorization";

    private Set<String> authorizedRoutes = new HashSet<>();

    public SecurityFilter() {
        authorizedRoutes.add("/api/auths/token");
        authorizedRoutes.add("/api/auths/signup");
        authorizedRoutes.add("/api/reset");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            if(shouldIFilter(request)) {
                System.out.println("No filter this request");
                filterChain.doFilter(servletRequest, servletResponse);

                return;
            }

            if(!processTokenCheck(servletResponse, filterChain, request)) {
                sendUnauthorizedResponse((HttpServletResponse) servletResponse);
            }
        } catch (Exception e) {
            System.err.println("SecurityFilter error : " + e.getMessage());
            throw e;
        }
    }

    private boolean processTokenCheck(ServletResponse servletResponse, FilterChain filterChain, HttpServletRequest request) throws IOException, ServletException {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            Optional<User> userOpt = authenticationService.verifyToken(token);
            if(userOpt.isPresent()){
                filterChain.doFilter(
                        new SecuredRequest(request, userOpt.get()),
                        servletResponse
                );
                return true;
            }
        }
        return false;
    }

    private boolean shouldIFilter(HttpServletRequest request) {
        return authorizedRoutes.contains(request.getServletPath());
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
