package com.advisorapp.api;


import com.advisorapp.api.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by damien on 20/05/2016.
 */
public class SecuredRequest extends HttpServletRequestWrapper {

    private User user;

    public SecuredRequest(HttpServletRequest request, User user) {
        super(request);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
